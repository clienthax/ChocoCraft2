package uk.co.haxyshideout.chococraft2.events;

import com.google.common.io.CharSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.apache.commons.io.IOUtils;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.haxylib.utils.FormattingHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by clienthax on 13/4/2015.
 */
public class EventHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onConnect(PlayerEvent.PlayerLoggedInEvent event) {
		ForgeVersion.CheckResult checkResult = ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(ChocoCraft2.instance));
		if (checkResult.status == ForgeVersion.Status.OUTDATED) {
			ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, checkResult.url);
			ChatStyle clickableChatStyle = new ChatStyle().setChatClickEvent(clickEvent);
			event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD+"Chococraft 2 Update available!, Click here to open website").setChatStyle(clickableChatStyle));
			event.player.addChatMessage(new ChatComponentText(checkResult.changes.get(checkResult.target)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRightClickChocopedia(PlayerInteractEvent event) throws IOException {//TODO this is a terrible way to do this
		if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
			if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == Additions.chocopediaItem) {
				ItemStack book = new ItemStack(Items.written_book);
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setString("title", "Chocopedia");
				tagCompound.setString("author", "Clienthax");
				NBTTagList pageList = new NBTTagList();

				String languageCode = Minecraft.getMinecraft().gameSettings.language;

				InputStream inputStream;
				try {
					inputStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Constants.MODID, "lang/chocopedia/" + languageCode +".hocon")).getInputStream();
				} catch (FileNotFoundException e) {
					inputStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Constants.MODID, "lang/chocopedia/en_US.hocon")).getInputStream();
				}

				byte[] data = new byte[inputStream.available()];
				IOUtils.readFully(inputStream, data);
				ConfigurationNode configurationLoader = HoconConfigurationLoader.builder().setSource(CharSource.wrap(new String(data))).build().load();

				List<? extends ConfigurationNode> pages = configurationLoader.getNode("pages").getChildrenList();
				for(ConfigurationNode node : pages) {
					String pageText = "";
					List<? extends ConfigurationNode> lines = node.getChildrenList();
					for(ConfigurationNode line : lines)
						pageText += line.getString();
					pageList.appendTag(new NBTTagString(FormattingHelper.convertFormattingCodes(pageText)));
				}
				tagCompound.setTag("pages", pageList);
				book.setTagCompound(tagCompound);

				Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(event.entityPlayer, book, false));
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBoneMealUse(BonemealEvent event) {
		if(event.block.getBlock() != Additions.gysahlStemBlock)
			return;

		//This tells forge we have processed the event
		event.setResult(Event.Result.ALLOW);
		//If the stage is below max, then set the growth to max stage, if its already fully grown, cancel the event (prevent the player from losing bonemeal)
		if((Integer) event.block.getValue(GysahlStemBlock.STAGE) < GysahlStemBlock.MAXSTAGE) {
			((GysahlStemBlock) event.block.getBlock()).setGrowthStage(event.world, event.pos, event.block);
		} else
			event.setCanceled(true);
	}

}
