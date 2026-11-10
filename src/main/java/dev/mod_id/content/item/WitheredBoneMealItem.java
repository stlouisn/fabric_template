package dev.mod_id.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class WitheredBoneMealItem extends Item {

    public WitheredBoneMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final Level level = context.getLevel();
        final BlockPos pos = context.getClickedPos();
        final ItemStack stack = context.getItemInHand();
        if (growCrop(stack, level, pos)) {
            if (!level.isClientSide()) {
                final Player player = context.getPlayer();
                if (player != null) {
                    stack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                }
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean growCrop(ItemStack itemStack, Level level, BlockPos pos) {
        final BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.NETHER_WART)) {
            final int age = state.getValue(NetherWartBlock.AGE);
            if (age < NetherWartBlock.MAX_AGE) {
                if (level instanceof ServerLevel) {
                    final int newAge = Math.min(NetherWartBlock.MAX_AGE, age + level.getRandom().nextIntBetweenInclusive(1, 2));
                    level.setBlock(pos, state.setValue(NetherWartBlock.AGE, newAge), Block.UPDATE_CLIENTS);
                    itemStack.shrink(1);
                } else {
                    ParticleUtils.spawnParticleInBlock(level, pos, 15, ParticleTypes.SOUL_FIRE_FLAME);
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
