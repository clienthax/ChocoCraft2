package uk.co.haxyshideout.chococraft2.blocks;

import uk.co.haxyshideout.chococraft2.config.ChocoCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.haxylib.blocks.GenericBush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by clienthax on 12/4/2015.
 */
public class GysahlStemBlock extends GenericBush implements IGrowable {

	public static final Integer MAXSTAGE = 4;
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, MAXSTAGE);

	public GysahlStemBlock() {
		setTickRandomly(true);
		this.setCreativeTab(ChocoCreativeTabs.chococraft2);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setStepSound(soundTypeGrass);
		this.setHardness(0f);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(Additions.gysahlSeedsItem);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(worldIn.isRemote)
			return;

		super.updateTick(worldIn, pos, state, rand);
		this.growStem(worldIn, rand, pos, state);
	}

	@Override
	public java.util.List<net.minecraft.item.ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		//if stage = max stage, drop gysahl green item, else drop nothing
		List<ItemStack> ret = new ArrayList<ItemStack>();
		if(state.getValue(STAGE) == MAXSTAGE) {
			Random rand = world instanceof World ? ((World)world).rand : new Random();
			ret.add(getGysahlItem(rand));

			//If fully grown give a seed drop chance
			int seedAmount = 3 + fortune;
			for(int i = 0; i < seedAmount; i++) {
				if(rand.nextInt(15) < 7) {
					ret.add(new ItemStack(Additions.gysahlSeedsItem));
				}
			}
		}

		return ret;
	}

	private ItemStack getGysahlItem(Random random) {
		Item item;
		int chance = random.nextInt(200);
		if(chance < 10)
			item = Additions.gysahlGoldenItem;
		else if(chance < 30)
			item = Additions.gysahlLoverlyItem;
		else
			item = Additions.gysahlGreenItem;


		return new ItemStack(item);
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
				float growthChance = getGrowthChance(this, worldIn, pos);
				if(rand.nextInt((int)(25F / growthChance) + 1) == 0) {
					worldIn.setBlockState(pos, state.cycleProperty(STAGE), 2);
				}
			}
		}
	}

	public void setGrowthStage(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state.withProperty(STAGE, 4), 2);
	}

	//Stolen from BlockCrops
	public float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
	{
		float f = 1.0F;
		BlockPos blockUnder = pos.down();

		for (int i = -1; i <= 1; ++i)
		{
			for (int j = -1; j <= 1; ++j)
			{
				float f1 = 0.0F;
				IBlockState iblockstate = worldIn.getBlockState(blockUnder.add(i, 0, j));

				if (iblockstate.getBlock().canSustainPlant(worldIn, blockUnder.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) blockIn))
				{
					f1 = 1.0F;

					if (iblockstate.getBlock().isFertile(worldIn, blockUnder.add(i, 0, j)))
					{
						f1 = 3.0F;
					}
				}

				if (i != 0 || j != 0)
				{
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos blockNorth = pos.north();
		BlockPos blockSouth = pos.south();
		BlockPos blockWest = pos.west();
		BlockPos blockEast = pos.east();
		boolean flag = blockIn == worldIn.getBlockState(blockWest).getBlock() || blockIn == worldIn.getBlockState(blockEast).getBlock();
		boolean flag1 = blockIn == worldIn.getBlockState(blockNorth).getBlock() || blockIn == worldIn.getBlockState(blockSouth).getBlock();

		if (flag && flag1)
		{
			f /= 2.0F;
		}
		else
		{
			boolean flag2 = blockIn == worldIn.getBlockState(blockWest.north()).getBlock() || blockIn == worldIn.getBlockState(blockEast.north()).getBlock() || blockIn == worldIn.getBlockState(blockEast.south()).getBlock() || blockIn == worldIn.getBlockState(blockWest.south()).getBlock();

			if (flag2)
			{
				f /= 2.0F;
			}
		}

		return f;
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
