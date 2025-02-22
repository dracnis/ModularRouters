package me.desht.modularrouters.logic.compiled;

import me.desht.modularrouters.block.tile.ModularRouterBlockEntity;
import me.desht.modularrouters.item.module.TargetedModule;
import me.desht.modularrouters.logic.ModuleTarget;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CompiledFluidModule2 extends CompiledFluidModule1 {
    public CompiledFluidModule2(ModularRouterBlockEntity router, ItemStack stack) {
        super(router, stack);
    }

    @Override
    List<ModuleTarget> setupTargets(ModularRouterBlockEntity router, ItemStack stack) {
        return Collections.singletonList(TargetedModule.getTarget(stack, !router.getLevel().isClientSide));
    }
}
