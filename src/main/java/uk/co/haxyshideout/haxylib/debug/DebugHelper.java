package uk.co.haxyshideout.haxylib.debug;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("unchecked")
public class DebugHelper
{

	public static void langCheck(String modid)
	{
		checkMissingBlockLangEntries(modid);
		checkMissingItemLangEntries(modid);
	}

	private static void checkMissingItemLangEntries(String modID)
	{
		for (Item item : (Iterable<Item>) GameData.getItemRegistry())
		{
			if (GameRegistry.findItem(modID, item.getUnlocalizedName()) != null)// ONly check items for pixelmon
				continue;

			String itemName = item.getItemStackDisplayName(new ItemStack(item, 1));
			if (itemName.contains("."))
			{
				String translated = I18n.translateToLocal(itemName);
				if (translated.equals(itemName))
				{
					System.out.println("Item " + itemName + " Doesn't have a lang entry");
				}
			}
		}
	}

	private static void checkMissingBlockLangEntries(String modID)
	{// Check missing block lang entries
		for (Block block : (Iterable<Block>) GameData.getBlockRegistry())
		{
			if (GameRegistry.findBlock(modID, block.getUnlocalizedName()) != null)// Only check blocks for pixelmon
				continue;

			Item blockItem = Item.getItemFromBlock(block);
			if (blockItem == null)
				continue;

			String blockName = blockItem.getItemStackDisplayName(new ItemStack(blockItem, 1));

			if (blockName.contains("."))
			{
				String translated = I18n.translateToLocal(blockName);
				if (translated.equals(blockName))
				{
					System.out.println("Block " + blockName + " Doesn't have a lang entry");
				}
			}

		}
	}

}
