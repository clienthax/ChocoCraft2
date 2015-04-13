package uk.co.haxyshideout.haxylib.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

/**
 * Created by clienthax on 13/4/2015.
 */
public class GenericItemSeeds extends ItemSeeds implements IJsonItem {

	public GenericItemSeeds(Block crops, Block soil) {
		super(crops, soil);
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureFolder() {
		return "items";
	}

}
