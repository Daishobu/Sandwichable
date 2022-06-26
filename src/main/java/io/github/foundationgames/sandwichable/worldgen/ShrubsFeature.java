package io.github.foundationgames.sandwichable.worldgen;

import com.mojang.serialization.Codec;
import io.github.foundationgames.sandwichable.blocks.BlocksRegistry;
import io.github.foundationgames.sandwichable.blocks.ShrubBlock;
import io.github.foundationgames.sandwichable.config.SandwichableConfig;
import io.github.foundationgames.sandwichable.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ShrubsFeature extends Feature<DefaultFeatureConfig> {

    public ShrubsFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> ctx) {
        var world = ctx.getWorld();
        var random = ctx.getRandom();

        SandwichableConfig sconfig = Util.getConfig();

        BlockPos pos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, ctx.getOrigin());

        BlockState blockState = BlocksRegistry.SHRUB.getDefaultState();
        int i = 0;
        for(int j = 0; j < sconfig.shrubGenOptions.spawnTries; j++) {
            BlockPos blockPos = randPosInRadius(random, pos, 5);
            if (world.isAir(blockPos) && blockPos.getY() < 255 && ShrubBlock.canGenerateOn(blockState, world, blockPos)) {
                world.setBlockState(blockPos, blockState, 2);
                i++;
            }
        }

        return i > 0;
    }

    private BlockPos randPosInRadius(Random random, BlockPos pos, int radius) {
        int x = pos.getX() + random.nextInt(radius*2) - radius;
        int y = pos.getY() + random.nextInt(radius*2) - radius;
        int z = pos.getZ() + random.nextInt(radius*2) - radius;
        return new BlockPos(x, y, z);
    }
}
