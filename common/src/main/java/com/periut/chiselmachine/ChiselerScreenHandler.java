package com.periut.chiselmachine;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ChiselerScreenHandler extends ScreenHandler {

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Registries.SCREEN_HANDLER.get(ChiselMachine.CHISELER_ID), syncId);
    }

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Registries.SCREEN_HANDLER.get(ChiselMachine.CHISELER_ID), syncId);
        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new Slot(inventory, 1, 56, 53));
        this.addSlot(new Slot(inventory, 2, 116, 35));
        this.addPlayerSlots(playerInventory, 8, 84);
    }

    public ChiselerScreenHandler(int syncId, PlayerInventory inventory, Object o) {
        super(Registries.SCREEN_HANDLER.get(ChiselMachine.CHISELER_ID), syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
