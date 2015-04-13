package uk.co.haxyshideout.haxylib.blocks;

import net.minecraft.block.BlockBush;

/**
 * Created by clienthax on 13/4/2015.
 */
public class GenericBush extends BlockBush {

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().substring(5);
	}

}
