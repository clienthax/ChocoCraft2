package uk.co.haxyshideout.haxylib.items;

import net.minecraft.item.ItemFood;

/**
 * Created by clienthax on 13/4/2015.
 */
public class GenericItemFood extends ItemFood implements IJsonItem {

	public GenericItemFood(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
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
