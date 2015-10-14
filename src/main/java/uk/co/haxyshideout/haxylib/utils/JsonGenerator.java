package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.FileUtils;
import uk.co.haxyshideout.haxylib.items.IJsonItem;

import java.io.File;
import java.io.IOException;

/**
 * Created by clienthax on 6/3/2015.
 */
public class JsonGenerator {

	public static boolean devEnviroment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	static File libResourcesFolder = new File("./src/main/resources/assets/haxylib/json/");
	static File modResourcesFolder = null;
	static String modid = null;

	public static void setModID(String modId) {
		modid = modId;
		modResourcesFolder = new File("./src/main/resources/assets/"+modId);
	}

	public static void generateSimpleBlockJson(String blockName) throws IOException {//TODO generation for stuff with tile's and icons
		if(!devEnviroment)
			return;

		if(new File(modResourcesFolder, "blockstates/" + blockName + ".json").exists())
			return;

		System.out.println(libResourcesFolder.getAbsolutePath());
		String blockstateTemplate = FileUtils.readFileToString(new File(libResourcesFolder, "blockstates/basicState.json"));
		String blockTemplate = FileUtils.readFileToString(new File(libResourcesFolder, "models/block/basicBlock.json"));
		String itemTemplate = FileUtils.readFileToString(new File(libResourcesFolder, "models/item/basicItemBlock.json"));

		blockstateTemplate = blockstateTemplate.replace("%name%", blockName).replace("%modid%",modid);
		blockTemplate = blockTemplate.replace("%name%", blockName).replace("%modid%", modid);
		itemTemplate = itemTemplate.replace("%name%", blockName).replace("%modid%", modid);

		FileUtils.writeStringToFile(new File(modResourcesFolder, "blockstates/"+blockName+".json"), blockstateTemplate);
		FileUtils.writeStringToFile(new File(modResourcesFolder, "models/block/"+blockName+".json"), blockTemplate);
		FileUtils.writeStringToFile(new File(modResourcesFolder, "models/item/"+blockName+".json"), itemTemplate);

	}

	public static void generateSimpleItemJson(String itemName, boolean skip5, Item item) throws IOException {
		if(!devEnviroment)
			return;

		if(new File(modResourcesFolder, "models/item/"+itemName+".json").exists())
			return;

		String itemTemplate = FileUtils.readFileToString(new File(libResourcesFolder, "models/item/basicItem.json"));
		if(item instanceof IJsonItem)
			itemTemplate = itemTemplate.replace("%texfolder%",((IJsonItem)item).getTextureFolder());

		if(skip5)
			itemTemplate = itemTemplate.replace("%name%", itemName.substring(5));
		else
			itemTemplate = itemTemplate.replace("%name%", itemName);

		itemTemplate = itemTemplate.replace("%modid%", modid);
		FileUtils.writeStringToFile(new File(modResourcesFolder, "models/item/"+itemName+".json"), itemTemplate);

	}
}
