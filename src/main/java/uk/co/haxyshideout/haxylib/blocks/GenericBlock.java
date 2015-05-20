package uk.co.haxyshideout.haxylib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by clienthax on 12/4/2015.
 */
public class GenericBlock extends Block {

	private boolean opaque = true;
	private boolean fullcube = true;

	public GenericBlock(Material materialIn) {
		super(materialIn);
	}

	public Block setFullCube(boolean fullcube) {
		this.fullcube = fullcube;
		return this;
	}

	public boolean isFullCube() {
		return fullcube;
	}

	public Block setOpaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}

	@Override
	public boolean isOpaqueCube() {
		return opaque;
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().substring(5);
	}

}
