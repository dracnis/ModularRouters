package me.desht.modularrouters.client.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import me.desht.modularrouters.block.tile.ModularRouterBlockEntity;
import me.desht.modularrouters.client.gui.ModularRouterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ClientUtil {
    public static Level theClientWorld() {
        return Minecraft.getInstance().level;
    }

    public static Optional<ModularRouterBlockEntity> getOpenItemRouter() {
        if (Minecraft.getInstance().screen instanceof ModularRouterScreen mrs) {
            return Optional.of(mrs.getMenu().getRouter());
        } else {
            return Optional.empty();
        }
    }

    public static boolean thisScreenPassesEvents() {
        return Minecraft.getInstance().screen == null || Minecraft.getInstance().screen.passEvents;
    }

    public static MutableComponent xlate(String key, Object... args) {
        // not using TranslationTextComponent here because each argument starts a separate child component,
        // which resets any formatting each time
        return new TextComponent(I18n.get(key, args));
    }

    public static boolean isInvKey(int keyCode) {
        return keyCode == Minecraft.getInstance().options.keyInventory.getKey().getValue();
    }

    public static VertexConsumer posF(VertexConsumer builder, Matrix4f posMat, Vec3 vec) {
        return builder.vertex(posMat, (float)vec.x, (float)vec.y, (float)vec.z);
    }

    public static Slot getHoveredSlot() {
        if (Minecraft.getInstance().screen instanceof ContainerScreen cs) {
            return cs.getSlotUnderMouse();
        }
        return null;
    }
}
