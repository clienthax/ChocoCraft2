package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

/**
 * Created by clienthax on 12/4/2015.
 */
public class RegistryHelper {

	public static void registerFieldsWithGameRegistry(Class clazz) {
		try {
			for(Field field : clazz.getFields()) {
				if(field.get(null) instanceof Block) {
					Block block = (Block) field.get(null);
					if(block != null) {
						GameRegistry.registerBlock(block, block.getUnlocalizedName());
						JsonGenerator.generateSimpleBlockJson(block.getUnlocalizedName());
					}
				} else
				if(field.get(null) instanceof Item) {
					Item item = (Item) field.get(null);
					if(item != null) {
						GameRegistry.registerItem(item, item.getUnlocalizedName());
						JsonGenerator.generateSimpleItemJson(item.getUnlocalizedName(), item instanceof ItemBlock, item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderers(ItemModelMesher itemModelMesher, Class clazz, String modId) {
		try {
			for (Field field : clazz.getFields()) {
				if (field.get(null) instanceof Block) {
					Block block = (Block) field.get(null);
					if(block != null)
						itemModelMesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modId + ":" + block.getUnlocalizedName(), "inventory"));
				} else
				if (field.get(null) instanceof Item) {
					Item item = (Item) field.get(null);
					if(item != null)
						itemModelMesher.register(item, 0, new ModelResourceLocation(modId + ":" + item.getUnlocalizedName(), "inventory"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
