package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import uk.co.haxyshideout.haxylib.utils.RecipeHelper;

/**
 * Created by clienthax on 13/4/2015.
 */
public class RecipeHandler {

	public static void registerRecipies() {
		//Recipe to make 3 seeds from a gysahl green
		GameRegistry.addShapelessRecipe(new ItemStack(Additions.gysahlSeedsItem, 3), new ItemStack(Additions.gysahlGreenItem));

		//Add saddle recipe
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.chocoboSaddleItem),
				"   ",
				"sls",
				" f ",
				's', Items.string,
				'l', Items.leather,
				'f', Additions.chocoboFeatherItem
		);

		//Saddle Bag
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.chocoboSaddleBagItem),
				" f ",
				"l l",
				" l ",
				'l', Items.leather,
				'f', Additions.chocoboFeatherItem
		);

		//Pack Bag
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.chocoboPackBagItem),
				"sfs",
				"w w",
				"sls",
				's', Items.string,
				'l', Items.leather,
				'f', Additions.chocoboFeatherItem,
				'w', Blocks.wool
		);

		//Whistle
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.chocoboWhistleItem),
				"   ",
				" g ",
				" f ",
				'g', Items.gold_ingot,
				'f', Additions.chocoboFeatherItem
		);

		//Gysahl Cake
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.gysahlCakeItem),
				"bgb",
				"ses",
				"wgw",
				'b', Items.milk_bucket,
				'g', Additions.gysahlGreenItem,
				's', Items.sugar,
				'e', Items.egg,
				'w', Items.wheat
		);

		//Raw Pickles
		GameRegistry.addShapelessRecipe(
				new ItemStack(Additions.gysahlRawPicklesItem),
				Additions.gysahlGreenItem,
				Items.sugar
		);

		//Straw
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.strawBlock, 4),
				"ss ",
				"   ",
				"   ",
				's', Items.wheat
		);

		//Alternative arrow recipe
		GameRegistry.addShapedRecipe(
				new ItemStack(Items.arrow, 4),
				"f  ",
				"s  ",
				"c  ",
				'f', Items.flint,
				's', Items.stick,
				'c', Additions.chocoboFeatherItem
		);

		//Chocopedia
		GameRegistry.addShapedRecipe(
				new ItemStack(Additions.chocopediaItem),
				"fnf",
				"ibi",
				"flf",
				'f', Additions.chocoboFeatherItem,
				'n', Items.gold_nugget,
				'i', new ItemStack(Items.dye, 1, 0),
				'b', Items.book,
				'l', new ItemStack(Items.dye, 1, 4)
		);

		//Chocobo disguise
		RecipeHelper.makeHelmRecipe(Additions.chocoDisguiseHelm, Additions.chocoboFeatherItem);
		RecipeHelper.makePlateRecipe(Additions.chocoDisguiseChest, Additions.chocoboFeatherItem);
		RecipeHelper.makeLegsRecipe(Additions.chocoDisguiseLegs, Additions.chocoboFeatherItem);
		RecipeHelper.makeBootsRecipe(Additions.chocoDisguiseBoots, Additions.chocoboFeatherItem);

		//Cooking
		GameRegistry.addSmelting(Additions.chocoboLegRawItem, new ItemStack(Additions.chocoboLegCookedItem), 1f);
		GameRegistry.addSmelting(Additions.gysahlRawPicklesItem, new ItemStack(Additions.gysahlCookedPicklesItem), 1f);

		//Gysahls
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(Additions.gysahlRedItem),
				Additions.gysahlGreenItem,
				"dyeRed"
		));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(Additions.gysahlPinkItem),
				Additions.gysahlGreenItem,
				"dyePink"
		));

	}
}
