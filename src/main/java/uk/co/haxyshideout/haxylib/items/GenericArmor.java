package uk.co.haxyshideout.haxylib.items;

import net.minecraft.item.ItemArmor;

public class GenericArmor extends ItemArmor implements IJsonItem {

	public static enum ArmorType {Helm,Plate,Legs,Boots}
	public boolean equippedSet = false;

	public GenericArmor(ArmorMaterial material, int renderIndex, ArmorType armorType) {
		super(material, renderIndex, armorType.ordinal());
	}

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureFolder() {
		return "items/armor";
	}
}