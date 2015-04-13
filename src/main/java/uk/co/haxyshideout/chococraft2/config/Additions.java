package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import uk.co.haxyshideout.chococraft2.blocks.GysahlGreenBlock;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.haxylib.blocks.GenericBlock;
import uk.co.haxyshideout.haxylib.utils.RegistryHelper;

/**
 * Created by clienthax on 12/4/2015.
 * Should contain every block and item that is added from the mod.
 */
public class Additions {

	public static Block gysahlStemBlock;
	public static Block gysahlGreenBlock;
	public static Block strawBlock;

	//Register items and blocks etc in here
	public static void registerAdditions() {
		gysahlStemBlock = new GysahlStemBlock().setUnlocalizedName("gysahlStemBlock").setCreativeTab(ChocoCreativeTabs.chococraft2);
		gysahlGreenBlock = new GysahlGreenBlock().setUnlocalizedName("gysahlGreenBlock").setCreativeTab(ChocoCreativeTabs.chococraft2);
		strawBlock = new GenericBlock(Material.glass).setOpaque(false).setUnlocalizedName("strawBlock").setStepSound(Block.soundTypeGrass).setCreativeTab(ChocoCreativeTabs.chococraft2);

		RegistryHelper.registerFieldsWithGameRegistry(Additions.class);
	}


}
