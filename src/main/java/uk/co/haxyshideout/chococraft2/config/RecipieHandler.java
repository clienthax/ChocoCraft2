package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by clienthax on 13/4/2015.
 */
public class RecipieHandler {

	public static void registerRecipies() {
		//Recipe to make 2 seeds from a gysahl green
		GameRegistry.addShapelessRecipe(new ItemStack(Additions.gysahlSeedsItem, 2), new ItemStack(Additions.gysahlGreenItem));



	}
}
