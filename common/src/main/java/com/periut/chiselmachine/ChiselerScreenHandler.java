package com.periut.chiselmachine;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ChiselerScreenHandler extends ScreenHandler {

    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3)); // Create an empty inventory with 3 slots
    }


    public ChiselerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Registries.SCREEN_HANDLER.get(ChiselMachine.CHISELER_ID), syncId);
        this.addSlot(new Slot(inventory, 0, 51, 35));
        this.addSlot(new Slot(inventory, 1, 80, 59));
        this.addSlot(new Slot(inventory, 2, 113, 35));
        this.addPlayerSlots(playerInventory, 8, 84);
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
