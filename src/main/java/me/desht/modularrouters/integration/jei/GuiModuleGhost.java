package me.desht.modularrouters.integration.jei;

import me.desht.modularrouters.client.gui.module.GuiModule;
import me.desht.modularrouters.container.FilterSlot;
import me.desht.modularrouters.network.ModuleFilterMessage;
import me.desht.modularrouters.network.PacketHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiModuleGhost implements IGhostIngredientHandler<GuiModule> {
    @Override
    public <I> List<Target<I>> getTargets(GuiModule gui, I ingredient, boolean doStart) {
        List<Target<I>> res = new ArrayList<>();
        for (int i = 0; i < gui.getContainer().inventorySlots.size(); i++) {
            Slot s = gui.getContainer().getSlot(i);
            if (s instanceof FilterSlot) {
                res.add((Target<I>) new ItemTarget(gui, s));
            }
        }
        return res;
    }

    @Override
    public void onComplete() {
    }

    static class ItemTarget implements Target<ItemStack> {
        private final GuiModule gui;
        private final Slot slot;

        ItemTarget(GuiModule gui, Slot slot) {
            this.gui = gui;
            this.slot = slot;
        }

        @Override
        public Rectangle2d getArea() {
            return new Rectangle2d(slot.xPos + gui.getGuiLeft(), slot.yPos + gui.getGuiTop(), 16, 16);
        }

        @Override
        public void accept(ItemStack itemStack) {
            PacketHandler.NETWORK.sendToServer(new ModuleFilterMessage(slot.slotNumber, itemStack));
        }
    }
}
