package com.periut.chiselmachine;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ChiselerBlockEntity extends LockableContainerBlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    protected DefaultedList<ItemStack> inventory;

    public ChiselerBlockEntity(BlockPos pos, BlockState state) {
        super(Registries.BLOCK_ENTITY_TYPE.get(ChiselMachine.CHISELER_ID), pos, state);
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    }

    private int clicks = 0;
    public int getClicks() {
        return clicks;
    }

    public void incrementClicks() {
        clicks++;
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("clicks", clicks);

        super.writeNbt(nbt, registryLookup);
    }


    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0,1};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
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
        return new ChiselerScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 3;
    }
}
