package com.periut.chiseler;

import com.periut.chisel.block.ChiselGroupLookup;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class ChiselerBlockEntity extends LockableContainerBlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    public static final int ENERGY_CAPCITY_PROPERTY_INDEX = 0;
    public static final int ENERGY_AMOUNT_PROPERTY_INDEX = 1;
    public static final int MAKE_TIME_PROPERTY_INDEX = 2;
    public static final int MAKE_TIME_TOTAL_PROPERTY_INDEX = 3;
    public static final int PROPERTY_COUNT = 4;
    protected final PropertyDelegate propertyDelegate;
    int makeTimeSpent = 0;
    int makeTotalTime = Chiseler.energyPerBlock;

    public final EnergyStorage energyStorage;

    protected abstract EnergyStorage createEnergyStorage();

    protected DefaultedList<ItemStack> inventory;

    public ChiselerBlockEntity(BlockPos pos, BlockState state) {
        super(Registries.BLOCK_ENTITY_TYPE.get(Chiseler.CHISELER_ID), pos, state);
        this.energyStorage = createEnergyStorage();
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case ENERGY_CAPCITY_PROPERTY_INDEX:
                        return (int) ChiselerBlockEntity.this.energyStorage.getMaxEnergy();
                    case ENERGY_AMOUNT_PROPERTY_INDEX:
                        return (int) ChiselerBlockEntity.this.energyStorage.getEnergy();
                    case MAKE_TIME_PROPERTY_INDEX:
                        return ChiselerBlockEntity.this.makeTimeSpent;
                    case MAKE_TIME_TOTAL_PROPERTY_INDEX:
                        return ChiselerBlockEntity.this.makeTotalTime;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case ENERGY_AMOUNT_PROPERTY_INDEX -> ChiselerBlockEntity.this.energyStorage.setEnergy(value);
                    case MAKE_TIME_PROPERTY_INDEX -> ChiselerBlockEntity.this.makeTimeSpent = value;
                    case MAKE_TIME_TOTAL_PROPERTY_INDEX -> ChiselerBlockEntity.this.makeTotalTime = value;
                }

            }

            public int size() {
                return PROPERTY_COUNT;
            }
        };
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0,1,2};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 2;
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Chiseler");
    }

    @Override
    protected Text getContainerName() {
        return getDisplayName();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ChiselerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readData(view, this.inventory);
        this.makeTimeSpent = view.getInt("make_time_spent", 0);
        this.makeTotalTime = view.getInt("make_total_time", Chiseler.energyPerBlock);
        this.energyStorage.setEnergy(view.getLong("energy_amount", 0L));
        makeTotalTime = Chiseler.energyPerBlock;
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, this.inventory);
        view.putInt("make_time_spent", makeTimeSpent);
        view.putInt("make_total_time", makeTotalTime);
        view.putLong("energy_amount", energyStorage.getEnergy());
    }

    public static void tick(World world, BlockPos pos, BlockState state, ChiselerBlockEntity blockEntity) {
        if (world.isClient)
            return;

        ItemStack blockToChisel = (ItemStack)blockEntity.inventory.get(0);
        if (blockToChisel.isEmpty()) {
            blockEntity.makeTimeSpent = 0;
            return;
        }
        ItemStack templateBlock = (ItemStack)blockEntity.inventory.get(1);
        ItemStack output = (ItemStack)blockEntity.inventory.get(2);

        if ((output.getItem() == templateBlock.getItem() || output.isEmpty()) && output.getCount() < output.getMaxCount()) {
            List<Item> relatedBlocks = ChiselGroupLookup.getBlocksInGroup(templateBlock.getItem());
            if (relatedBlocks.contains(blockToChisel.getItem())) {
                if (blockEntity.energyStorage.getEnergy() >= 1) {
                    blockEntity.energyStorage.insertEnergy(-1);
                    blockEntity.makeTimeSpent += 1;
                    if (blockEntity.makeTimeSpent > blockEntity.makeTotalTime) {
                        blockToChisel.decrement(1);
                        if (output.getItem() == templateBlock.getItem()) {
                            output.increment(1);
                        } else if (output.isEmpty()) {
                            blockEntity.inventory.set(2, new ItemStack(templateBlock.getItem(), 1));
                        }
                        blockEntity.makeTimeSpent = 0;
                    }
                    blockEntity.markDirty();
                } else {
                    blockEntity.makeTimeSpent = 0;
                }
            } else {
                blockEntity.makeTimeSpent = 0;
            }
        }
        else {
            blockEntity.makeTimeSpent = 0;
        }
    }
}
