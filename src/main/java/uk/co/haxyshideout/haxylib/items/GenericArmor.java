package uk.co.haxyshideout.haxylib.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class GenericArmor extends ItemArmor implements IJsonItem
{
	public boolean equippedSet = false;

	public GenericArmor(ItemArmor.ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(material, renderIndex, armorType);
	}

	@Override
	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureFolder()
	{
		return "items/armor";
	}
}
