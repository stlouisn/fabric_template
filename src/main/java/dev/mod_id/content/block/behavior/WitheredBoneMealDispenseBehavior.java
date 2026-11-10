package dev.mod_id.content.block.behavior;

import dev.mod_id.content.item.WitheredBoneMealItem;
import dev.mod_id.particle.WitheredParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class WitheredBoneMealDispenseBehavior extends OptionalDispenseItemBehavior {

    @Override
    protected ItemStack execute(final BlockSource source, final ItemStack dispensed) {
        final Level level = source.level();
        final BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        final boolean didCropGrow = WitheredBoneMealItem.growCrop(dispensed, level, target);
        this.setSuccess(didCropGrow);
        if (didCropGrow && level instanceof ServerLevel serverLevel) {
            WitheredParticles.spawnSoulFireParticles(serverLevel, target, 15);
            serverLevel.playSound(null, target, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return dispensed;
    }
}
