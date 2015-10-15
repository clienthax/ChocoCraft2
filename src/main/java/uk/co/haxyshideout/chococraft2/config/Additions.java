package uk.co.haxyshideout.chococraft2.config;

import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.chococraft2.blocks.StrawBlock;
import uk.co.haxyshideout.chococraft2.items.SpawnEggItem;
import uk.co.haxyshideout.haxylib.items.GenericArmor;
import uk.co.haxyshideout.haxylib.items.GenericItem;
import uk.co.haxyshideout.haxylib.items.GenericItemFood;
import uk.co.haxyshideout.haxylib.items.GenericItemSeeds;
import uk.co.haxyshideout.haxylib.utils.RegistryHelper;

/**
 * Created by clienthax on 12/4/2015. Should contain every block and item that
 * is added from the mod.
 */
public class Additions {

    public static Block gysahlStemBlock;
    public static Block strawBlock;

    public static Item gysahlGreenItem;
    public static Item gysahlSeedsItem;
    public static Item gysahlGoldenItem;
    public static Item gysahlLoverlyItem;
    public static Item gysahlPinkItem;
    public static Item gysahlRedItem;
    public static Item gysahlRawPicklesItem;
    public static Item gysahlCookedPicklesItem;
    public static Item gysahlCakeItem;
    public static Item gysahlChibiItem;// I have no idea what this is for.

    public static Item chocoboFeatherItem;
    public static Item chocoboLegRawItem;
    public static Item chocoboLegCookedItem;
    public static Item chocopediaItem;
    public static Item chocoboSaddleItem;
    public static Item chocoboSaddleBagItem;
    public static Item chocoboPackBagItem;
    public static Item chocoboWhistleItem;
    
    public static Item purpleSpawnEggItem;
    public static Item redSpawnEggItem;
    public static Item blackSpawnEggItem;
    public static Item greenSpawnEggItem;
    public static Item yellowSpawnEggItem;
    public static Item goldSpawnEggItem;
    public static Item whiteSpawnEggItem;
    public static Item blueSpawnEggItem;
    public static Item pinkSpawnEggItem;
    
    public static Item chocoDisguiseHelm;
    public static Item chocoDisguiseChest;
    public static Item chocoDisguiseLegs;
    public static Item chocoDisguiseBoots;

    public static final ArmorMaterial chocoDisguiseMaterial = EnumHelper.addArmorMaterial("chocoDisguise", Constants.MODID + ":chocoDisguise", 200,
            new int[] {3, 7, 6, 3}, 10);

    // Register items and blocks etc in here
    public static void registerAdditions() {
        gysahlStemBlock = new GysahlStemBlock().setUnlocalizedName("gysahlStemBlock").setCreativeTab(ChocoCreativeTabs.chococraft2);
        strawBlock = new StrawBlock().setUnlocalizedName("strawBlock").setCreativeTab(ChocoCreativeTabs.chococraft2);

        gysahlGreenItem = new GenericItem().setUnlocalizedName("gysahlGreenItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlSeedsItem =
                new GenericItemSeeds(gysahlStemBlock, Blocks.farmland).setUnlocalizedName("gysahlSeedsItem").setCreativeTab(
                        ChocoCreativeTabs.chococraft2);
        gysahlGoldenItem = new GenericItem().setUnlocalizedName("gysahlGoldenItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlLoverlyItem = new GenericItem().setUnlocalizedName("gysahlLoverlyItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlPinkItem = new GenericItem().setUnlocalizedName("gysahlPinkItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlRedItem = new GenericItem().setUnlocalizedName("gysahlRedItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlRawPicklesItem = new GenericItem().setUnlocalizedName("gysahlRawPicklesItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlCookedPicklesItem =
                new GenericItemFood(2, false).setUnlocalizedName("gysahlCookedPicklesItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlCakeItem = new GenericItem().setUnlocalizedName("gysahlCakeItem").setMaxStackSize(8).setCreativeTab(ChocoCreativeTabs.chococraft2);
        gysahlChibiItem = new GenericItem().setUnlocalizedName("gysahlChibiItem").setCreativeTab(ChocoCreativeTabs.chococraft2);

        chocoboFeatherItem = new GenericItem().setUnlocalizedName("chocoboFeatherItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocoboLegRawItem =
                new GenericItemFood(4, 0.3F, true).setPotionEffect(Potion.hunger.getId(), 30, 0, 0.3F).setUnlocalizedName("chocoboLegRawItem")
                        .setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocoboLegCookedItem = new GenericItemFood(8, true).setUnlocalizedName("chocoboLegCookedItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocopediaItem = new GenericItem().setUnlocalizedName("chocopediaItem").setMaxStackSize(1).setCreativeTab(ChocoCreativeTabs.chococraft2);
        
        purpleSpawnEggItem = new SpawnEggItem(ChocoboColor.PURPLE).setUnlocalizedName("purpleSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        redSpawnEggItem = new SpawnEggItem(ChocoboColor.RED).setUnlocalizedName("redSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        blackSpawnEggItem = new SpawnEggItem(ChocoboColor.BLACK).setUnlocalizedName("blackSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        greenSpawnEggItem = new SpawnEggItem(ChocoboColor.GREEN).setUnlocalizedName("greenSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        goldSpawnEggItem = new SpawnEggItem(ChocoboColor.GOLD).setUnlocalizedName("goldSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        blueSpawnEggItem = new SpawnEggItem(ChocoboColor.BLUE).setUnlocalizedName("blueSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        pinkSpawnEggItem = new SpawnEggItem(ChocoboColor.PINK).setUnlocalizedName("pinkSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        whiteSpawnEggItem = new SpawnEggItem(ChocoboColor.WHITE).setUnlocalizedName("whiteSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        yellowSpawnEggItem = new SpawnEggItem(ChocoboColor.YELLOW).setUnlocalizedName("yellowSpawnEggItem").setCreativeTab(ChocoCreativeTabs.chococraft2);
        
        chocoboSaddleItem =
                new GenericItem().setUnlocalizedName("chocoboSaddleItem").setMaxStackSize(5).setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocoboSaddleBagItem =
                new GenericItem().setUnlocalizedName("chocoboSaddleBagItem").setMaxStackSize(8).setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocoboPackBagItem =
                new GenericItem().setUnlocalizedName("chocoboPackBagItem").setMaxStackSize(8).setCreativeTab(ChocoCreativeTabs.chococraft2);
        chocoboWhistleItem = new GenericItem().setUnlocalizedName("chocoboWhistleItem").setCreativeTab(ChocoCreativeTabs.chococraft2);

        chocoDisguiseHelm =
                new GenericArmor(chocoDisguiseMaterial, 0, GenericArmor.ArmorType.Helm).setUnlocalizedName("chocoDisguiseHelm").setCreativeTab(
                        ChocoCreativeTabs.chococraft2);
        chocoDisguiseChest =
                new GenericArmor(chocoDisguiseMaterial, 0, GenericArmor.ArmorType.Plate).setUnlocalizedName("chocoDisguiseChest").setCreativeTab(
                        ChocoCreativeTabs.chococraft2);
        chocoDisguiseLegs =
                new GenericArmor(chocoDisguiseMaterial, 0, GenericArmor.ArmorType.Legs).setUnlocalizedName("chocoDisguiseLegs").setCreativeTab(
                        ChocoCreativeTabs.chococraft2);
        chocoDisguiseBoots =
                new GenericArmor(chocoDisguiseMaterial, 0, GenericArmor.ArmorType.Boots).setUnlocalizedName("chocoDisguiseBoots").setCreativeTab(
                        ChocoCreativeTabs.chococraft2);

        RegistryHelper.registerFieldsWithGameRegistry(Constants.MODID, Additions.class);
    }

}
