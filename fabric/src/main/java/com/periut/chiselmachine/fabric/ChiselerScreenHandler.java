package com.periut.chiselmachine.fabric;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;

public class ChiselerScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4)); // Create an empty inventory with 3 slots
    }

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(syncId, playerInventory, inventory, new ArrayPropertyDelegate(4));
    }

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Registries.SCREEN_HANDLER.get(ChiselMachine.CHISELER_ID), syncId);
        checkSize(inventory, 3);
        checkDataCount(propertyDelegate, 4);
        this.propertyDelegate = propertyDelegate;
        this.addSlot(new Slot(inventory, 0, 51, 35));
        this.addSlot(new Slot(inventory, 1, 80, 59));
        this.addSlot(new Slot(inventory, 2, 113, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addPlayerSlots(playerInventory, 8, 84);
        this.addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotId) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(slotId);
        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slotId == 2) {
                if (!this.insertItem(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemstack1, itemstack);
            } else if (slotId != 1 && slotId != 0) {
                if (!this.insertItem(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (slotId >= 3 && slotId < 30) {
                    if (!this.insertItem(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotId >= 30 && slotId < 39 && !this.insertItem(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }


    public int getEnergy() {
        return propertyDelegate.get(1);
    }

    public int getMaxEnergy() {
        return propertyDelegate.get(0);
    }

    public float getCookProgress() {
        int i = this.propertyDelegate.get(2);
        int j = this.propertyDelegate.get(3);
        return j != 0 && i != 0 ? MathHelper.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }
}
