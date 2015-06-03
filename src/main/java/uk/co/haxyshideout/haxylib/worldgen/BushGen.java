package uk.co.haxyshideout.haxylib.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by clienthax on 2/6/2015.
 * Like WorldGenFlowers, but for bushs
 */
public class BushGen {

	private BlockBush flower;
	private IBlockState blockState;

	/**
	 * @param block the bush block
	 * @param state the state at which the flower should be fully grown
	 */
	public BushGen(BlockBush block, IBlockState state)
	{
		this.flower = block;
		this.blockState = state;
	}

	public void generate(World worldIn, Random rand, BlockPos position)
	{
		for (int i = 0; i < 64; ++i)
		{
			BlockPos blockPos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (worldIn.isAirBlock(blockPos) && (!worldIn.provider.getHasNoSky() || blockPos.getY() < 255) && this.flower.canBlockStay(worldIn, blockPos, this.blockState))
			{
				worldIn.setBlockState(blockPos, this.blockState, 2);
			}
		}
	}

}
