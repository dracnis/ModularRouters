package me.desht.modularrouters.logic.compiled;

import me.desht.modularrouters.block.tile.ModularRouterBlockEntity;
import me.desht.modularrouters.item.module.TargetedModule;
import me.desht.modularrouters.logic.ModuleTarget;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CompiledSenderModule2 extends CompiledSenderModule1 {
    public CompiledSenderModule2(ModularRouterBlockEntity router, ItemStack stack) {
        super(router, stack);
    }

    @Override
    protected List<ModuleTarget> setupTargets(ModularRouterBlockEntity router, ItemStack stack) {
        return Collections.singletonList(TargetedModule.getTarget(stack, !router.getLevel().isClientSide));
    }

    @Override
    protected PositionedItemHandler findTargetInventory(ModularRouterBlockEntity router) {
        ModuleTarget target = getEffectiveTarget(router);
        if (target == null || !validate(router, target)) {
            return PositionedItemHandler.INVALID;
        }

        return target.getItemHandler().map(h -> new PositionedItemHandler(target.gPos.pos(), h))
                .orElse(PositionedItemHandler.INVALID);
    }

    private boolean validate(ModularRouterBlockEntity router, ModuleTarget target) {
        if (!isRangeLimited()) return true;
        return target.isSameWorld(router.getLevel()) && router.getBlockPos().distSqr(target.gPos.pos()) <= getRangeSquared();
    }

    @Override
    protected int getBeamColor() {
        return 0xFF8000;
    }

    boolean isRangeLimited() {
        return true;
    }

    @Override
    public ModuleTarget getEffectiveTarget(ModularRouterBlockEntity router) {
        return getTarget();
    }
}
