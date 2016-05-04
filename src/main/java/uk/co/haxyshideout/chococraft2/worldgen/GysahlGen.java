package uk.co.haxyshideout.chococraft2.worldgen;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.haxylib.worldgen.BushGen;

/**
 * Created by clienthax on 2/6/2015.
 */
public class GysahlGen implements IWorldGenerator
{
	BushGen bushGen = new BushGen((BlockBush) Additions.gysahlStemBlock, Additions.gysahlStemBlock.getDefaultState().withProperty(GysahlStemBlock.STAGE, GysahlStemBlock.MAXSTAGE));

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if (!world.provider.isSurfaceWorld())// Only spawn in the main world
			return;

		if (!(random.nextInt(1000) < 100))
			return;

		int poxX = chunkX * 16 + random.nextInt(16);// gets us the world position of where to spawn the gysahls
		int posZ = chunkZ * 16 + random.nextInt(16);
		BlockPos finalPosition = world.getTopSolidOrLiquidBlock(new BlockPos(poxX, 0, posZ));// Gets the top block
		bushGen.generate(world, random, finalPosition);// Generate the blocks in world
	}
}
