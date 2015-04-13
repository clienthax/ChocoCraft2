package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by clienthax on 13/4/2015.
 */

public class RecipeHelper {

	public static void makePickaxeRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(new ItemStack(result, 1),
				"XXX",
				" Y ",
				" Y ",
				'X', craftingMaterial,
				'Y', Items.stick
		);
	}

	public static void makeAxeRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"XX ",
				"XY ",
				" Y ",
				'X', craftingMaterial,
				'Y', Items.stick
		);
	}

	public static void makeShovelRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				" X ",
				" Y ",
				" Y ",
				'X', craftingMaterial,
				'Y', Items.stick
		);
	}

	public static void makeHoeRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"XX ",
				" Y ",
				" Y ",
				'X', craftingMaterial,
				'Y', Items.stick
		);
	}

	public static void makeSwordRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				" X ",
				" X ",
				" Y ",
				'X', craftingMaterial,
				'Y', Items.stick
		);
	}

	public static void makeHelmRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"XXX",
				"X X",
				"   ",
				'X', craftingMaterial
		);
	}

	public static void makePlateRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"X X",
				"XXX",
				"XXX",
				'X', craftingMaterial
		);
	}

	public static void makeLegsRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"XXX",
				"X X",
				"X X",
				'X', craftingMaterial
		);
	}

	public static void makeBootsRecipe(Item result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"X X",
				"X X",
				'X', craftingMaterial
		);
	}

	public static void makeItemToBlockRecipe(Block result, Item craftingMaterial)
	{
		GameRegistry.addRecipe(
				new ItemStack(result, 1),
				"XXX",
				"XXX",
				"XXX",
				'X', craftingMaterial
		);
	}

	public static void makeItemsFromBlock(Item result, Block craftingMaterial)
	{
		GameRegistry.addShapelessRecipe(
				new ItemStack(result, 9),
				new ItemStack(craftingMaterial, 1)
		);
	}

}
