package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by clienthax on 20/5/2015.
 */
public class WorldHelper {

	public static List<IBlockState> getBlockstatesInRangeOfEntity(Block blockType, Entity entity, int rangeXZ, int rangeY) {
		ArrayList<IBlockState> blockStates = new ArrayList<IBlockState>();
		Iterator iterator = BlockPos.getAllInBox(entity.getPosition().add(-rangeXZ, -rangeY, -rangeXZ), entity.getPosition().add(rangeXZ, rangeY, rangeXZ)).iterator();
		while (iterator.hasNext()) {
			BlockPos pos = (BlockPos) iterator.next();
			IBlockState blockState = entity.worldObj.getBlockState(pos);
			if (blockState.getBlock() == blockType)
				blockStates.add(blockState);
		}
		return blockStates;
	}

	public static List<BlockPos> getBlockPositionsInRangeOfEntity(Block blockType, Entity entity, int rangeXZ, int rangeY) {
		ArrayList<BlockPos> blockPoses = new ArrayList<BlockPos>();
		Iterator iterator = BlockPos.getAllInBox(entity.getPosition().add(-rangeXZ, -rangeY, -rangeXZ), entity.getPosition().add(rangeXZ, rangeY, rangeXZ)).iterator();
		while (iterator.hasNext()) {
			BlockPos pos = (BlockPos) iterator.next();
			IBlockState blockState = entity.worldObj.getBlockState(pos);
			if (blockState.getBlock() == blockType)
				blockPoses.add(pos);
		}

		return blockPoses;
	}
}
