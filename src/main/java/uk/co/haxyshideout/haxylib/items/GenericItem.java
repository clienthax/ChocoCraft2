package uk.co.haxyshideout.haxylib.items;

import net.minecraft.item.Item;

/**
 * Created by clienthax on 13/4/2015.
 */
public class GenericItem extends Item implements IJsonItem {

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureFolder() {
		return "items";
	}

}