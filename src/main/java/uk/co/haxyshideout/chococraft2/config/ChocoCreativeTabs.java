package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ChocoCreativeTabs {
	public static final CreativeTabs chococraft2 = new CreativeTabs("Chococraft2"){
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {return Item.getItemFromBlock(Additions.gysahlStemBlock);}
	};
}
