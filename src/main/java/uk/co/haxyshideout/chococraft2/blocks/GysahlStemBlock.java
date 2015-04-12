package uk.co.haxyshideout.chococraft2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by clienthax on 12/4/2015.
 */
public class GysahlStemBlock extends BlockBush implements IGrowable {

	public static final Integer MAXSTAGE = 4;
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, MAXSTAGE);

	public GysahlStemBlock() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setStepSound(soundTypeGrass);
		this.setHardness(0f);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(worldIn.isRemote)
			return;

		super.updateTick(worldIn, pos, state, rand);
		this.growStem(worldIn, rand, pos, state);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		super.updateTick(worldIn, pos, state, rand);
		this.growStem(worldIn, rand, pos, state);
	}

	private void growStem(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if(worldIn.getLightFromNeighbors(pos) >= 9) {
			if( (Integer) state.getValue(STAGE) < MAXSTAGE) {
				float growthRate = getGrowthRate(worldIn, pos.getX(), pos.getX(), pos.getZ());
				if(rand.nextInt((int)(25F / growthRate) + 1) == 0) {
					worldIn.setBlockState(pos, state.cycleProperty(STAGE), 2);
				}
			}
		}
	}

	private float getGrowthRate(World theWorld, int xPos, int yPos, int zPos)
	{
		float growRate = 1.0F;
		Block blockNorth = theWorld.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock();
		Block blockSouth = theWorld.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock();
		Block k = theWorld.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock();
		Block l = theWorld.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock();
		Block i1 = theWorld.getBlockState(new BlockPos(xPos - 1, yPos, zPos - 1)).getBlock();
		Block j1 = theWorld.getBlockState(new BlockPos(xPos + 1, yPos, zPos - 1)).getBlock();
		Block k1 = theWorld.getBlockState(new BlockPos(xPos + 1, yPos, zPos + 1)).getBlock();
		Block l1 = theWorld.getBlockState(new BlockPos(xPos - 1, yPos, zPos + 1)).getBlock();

		boolean samePlantLeftOrRight = k.equals(this) || l.equals(this);
		boolean samePlantFrontOrBack = blockNorth.equals(this) || blockSouth.equals(this);
		boolean samePlantAnyCorner = i1.equals(this) || j1.equals(this) || k1.equals(this) || l1.equals(this);

		for (int xTmp = xPos - 1; xTmp <= xPos + 1; xTmp++)
		{
			for (int zTmp = zPos - 1; zTmp <= zPos + 1; zTmp++)
			{
				IBlockState baseBlockState = theWorld.getBlockState(new BlockPos(xTmp, yPos - 1, zTmp));
				float tmpGrowRate = 0.0F;
				if (baseBlockState.getBlock() == Blocks.farmland)
				{
					tmpGrowRate = 1.0F;
					if((Integer) baseBlockState.getValue(BlockFarmland.MOISTURE) > 0) {
						tmpGrowRate = 3F;
					}
                }

				if (xTmp != xPos || zTmp != zPos)
				{
					tmpGrowRate /= 4F;
				}

				growRate += tmpGrowRate;
			}
		}

		if (samePlantAnyCorner || samePlantLeftOrRight && samePlantFrontOrBack)
		{
			growRate /= 2.0F;
		}

		return growRate;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STAGE);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, STAGE);
	}


}
