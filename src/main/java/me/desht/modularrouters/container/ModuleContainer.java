package me.desht.modularrouters.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import static me.desht.modularrouters.container.Layout.SLOT_X_SPACING;
import static me.desht.modularrouters.container.Layout.SLOT_Y_SPACING;

public class ModuleContainer extends Container {

    private static final int N_FILTER_SLOTS = 9;
    private static final int INV_START = N_FILTER_SLOTS;
    private static final int INV_END = INV_START + 26;
    private static final int HOTBAR_START = INV_END + 1;
    private static final int HOTBAR_END = HOTBAR_START + 8;

    public final FilterHandler filterHandler;
    private final int currentSlot;

    public ModuleContainer(EntityPlayer player, ItemStack moduleStack) {
        this.filterHandler = new FilterHandler(moduleStack, N_FILTER_SLOTS);
        this.currentSlot = player.inventory.currentItem + HOTBAR_START;

        // slots for the (ghost) filter items
        for (int i = 0; i < N_FILTER_SLOTS; i++) {
            addSlotToContainer(new FilterSlot(filterHandler, i, 8 + SLOT_X_SPACING * (i % 3), 17 + SLOT_Y_SPACING * (i / 3)));
        }

        // player's main inventory - uses default locations for standard inventory texture file
        for (int i = 0; i < 3; i++)	{
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * SLOT_X_SPACING, 84 + i * SLOT_Y_SPACING));
            }
        }

        // player's hotbar - uses default locations for standard action bar texture file
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * SLOT_X_SPACING, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            stack.stackSize = 1;

            if (index < N_FILTER_SLOTS) {
                // shift-clicking in a filter slot: clear it from the filter
                slot.putStack(null);
            } else if (index >= INV_START) {
                // shift-clicking in player inventory: copy it into the filter (if not already present)
                // but don't remove it from player inventory
                int freeSlot;
                for (freeSlot = 0; freeSlot < N_FILTER_SLOTS; freeSlot++) {
                    ItemStack stack0 = filterHandler.getStackInSlot(freeSlot);
                    if (stack0 == null || stack0.stackSize == 0 || stack0.isItemEqual(stack)) {
                        break;
                    }
                }
                if (freeSlot < N_FILTER_SLOTS) {
                    Slot s = inventorySlots.get(freeSlot);
                    s.putStack(stack);
                    slot.putStack(stackInSlot);
                }
            }
        }
        return null;
    }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        System.out.println("slotClick: slot=" + slot + " dragtype=" + dragType + " clicktype=" + clickTypeIn);

        if (dragType > 1 && slot < N_FILTER_SLOTS && slot >= 0) {
            // no dragging items over the filter
            return null;
        }
        if (slot == currentSlot) {
            // no messing with the module that triggered this container's creation
            return null;
        }

        switch (clickTypeIn) {
            case PICKUP:
                // normal left-click
                if (slot < N_FILTER_SLOTS && slot >= 0) {
                    Slot s = inventorySlots.get(slot);
                    if (player.inventory.getItemStack() != null) {
                        ItemStack stack1 = player.inventory.getItemStack().copy();
                        stack1.stackSize = 1;
                        s.putStack(stack1);
                    } else {
                        s.putStack(null);
                    }
                    return null;
                }
            case THROW:
                if (slot < N_FILTER_SLOTS && slot >= 0) {
                    return null;
                }
        }
        return super.slotClick(slot, dragType, clickTypeIn, player);
    }
}
