package uk.co.haxyshideout.chococraft2.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.co.haxyshideout.haxylib.blocks.GenericBlock;

/**
 * Created by clienthax on 13/4/2015.
 */
public class StrawBlock extends GenericBlock
{
	public StrawBlock()
	{
		super(Material.carpet);
		setOpaque(false);
		setFullCube(false);
		setStepSound(SoundType.GROUND);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state)
	{
		return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}
}
