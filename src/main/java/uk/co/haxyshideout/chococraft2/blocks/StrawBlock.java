package uk.co.haxyshideout.chococraft2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import uk.co.haxyshideout.haxylib.blocks.GenericBlock;

/**
 * Created by clienthax on 13/4/2015.
 */
public class StrawBlock extends GenericBlock {

	public StrawBlock() {
		super(Material.carpet);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		setOpaque(false);
		setFullCube(false);
		setStepSound(Block.soundTypeGrass);
	}

}
