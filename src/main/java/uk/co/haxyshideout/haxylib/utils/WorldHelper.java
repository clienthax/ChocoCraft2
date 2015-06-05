package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by clienthax on 20/5/2015.
 */
public class WorldHelper {

	public static boolean isHellWorld(World world) {
		return world.provider instanceof WorldProviderHell;
	}

	public static boolean isBlockAtPositionLiquid(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().getMaterial().isLiquid();
	}

	public static boolean isNormalCubesAround(World world, BlockPos pos) {
		for(int x = pos.getX() -1; x <= pos.getX() + 1; x++)
			for(int z = pos.getZ() -1; z <= pos.getZ() + 1; z++)
				if(!isNormalBlockAtPos(world, pos.add(x,0,z)))
					return false;
		return true;
	}

	private static boolean isNormalBlockAtPos(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().isNormalCube();
	}

	public static BlockPos getFirstSolidWithAirAbove(World world, BlockPos pos) {//TODO more efficient way of doing this
		for(int y = pos.getY(); y < world.getHeight(); y++) {
			pos = new BlockPos(pos.getX(), y, pos.getZ());
			if(world.getBlockState(pos).getBlock().isNormalCube() && world.isAirBlock(pos.up()))
				return pos;
		}
		return null;
	}

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
