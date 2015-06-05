package uk.co.haxyshideout.chococraft2.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.network.PacketRegistry;
import uk.co.haxyshideout.chococraft2.network.side.server.ChocopediaEditPacket;
import uk.co.haxyshideout.chococraft2.network.side.server.DropGearPacket;

import java.awt.*;
import java.io.IOException;

/**
 * Created by clienthax on 4/5/2015.
 */
public class ChocopediaGui extends GuiScreen {

	public EntityChocobo chocobo;
	GuiButton renameButton;
	GuiButton followOwnerButton;
	GuiButton changeOwnerButton;
	GuiButton applyButton;
	GuiButton dropGearButton;
	GuiTextField inputTextField;

	public String owner;//can't update these on the client, store here temperally and send to server on apply
	public String name;
	public EntityChocobo.MovementType movementType;

	enum InputType {
		none, name, owner
	}
	InputType currentInputType = InputType.none;

	public ChocopediaGui(EntityChocobo chocobo) {
		this.chocobo = chocobo;
		owner = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
		name = chocobo.getCustomNameTag();
		movementType = chocobo.getMovementType();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	@Override
	public void initGui() {
		int componentID = 0;
		int yPos = 24;
		int xPos = this.width / 2 - 100;
		renameButton = new GuiButton(componentID++, xPos, yPos+=24, 90, 20, "Rename");
		followOwnerButton = new GuiButton(componentID++, xPos, yPos+=24, 90, 20, "Follow");
		changeOwnerButton = new GuiButton(componentID++, xPos, yPos+=24, 90, 20, "Change Owner");
		applyButton = new GuiButton(componentID++, xPos, yPos+=24, 90, 20, "Apply Changes");
		dropGearButton = new GuiButton(componentID++, xPos, yPos+=48, 90, 20, "Drop Gear");
		inputTextField = new GuiTextField(componentID++, fontRendererObj, xPos, 24, 100, 20);
		inputTextField.setVisible(false);
		updateMovementButton();

		buttonList.add(renameButton);
		buttonList.add(followOwnerButton);
		buttonList.add(changeOwnerButton);
		buttonList.add(applyButton);
		buttonList.add(dropGearButton);
	}

	@Override
	public void updateScreen() {
		if(chocobo.isDead)
			this.mc.displayGuiScreen(null);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		int yPos = 30;
		int xPos = this.width / 2;

		drawString(fontRendererObj, "Name: "+name, xPos, yPos+=24, -8355712);//gray
		drawString(fontRendererObj, "Owner: "+owner, xPos, yPos+=12, -8355712);
		drawString(fontRendererObj, "Health: "+chocobo.getHealth(), xPos, yPos+=12, -8355712);
		drawString(fontRendererObj, "Sex: "+(chocobo.isMale() ? "Male" : "Female"), xPos, yPos+=12, -8355712);//TODO breeding

		if(inputTextField.getVisible())
			inputTextField.drawTextBox();

	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.inputTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		this.inputTextField.textboxKeyTyped(typedChar, keyCode);
		if(keyCode == 28 && currentInputType != InputType.none) {//Enter key
			if(currentInputType == InputType.name)
				name = inputTextField.getText();
			else if(currentInputType == InputType.owner)
				owner = inputTextField.getText();
			closeInputBox();
		}
	}

	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		if(currentInputType != InputType.none)
			return;

		if(button == renameButton) {
			displayInputBox(InputType.name);
		}
		if(button == followOwnerButton) {
			changeMovementType();
		}
		if(button == changeOwnerButton) {
			displayInputBox(InputType.owner);
		}
		if(button == applyButton) {
			PacketRegistry.INSTANCE.sendToServer(new ChocopediaEditPacket(this));
			this.mc.displayGuiScreen(null);
		}
		if(button == dropGearButton) {
			PacketRegistry.INSTANCE.sendToServer(new DropGearPacket(chocobo));
			this.mc.displayGuiScreen(null);
		}
	}

	private void changeMovementType() {
		switch (movementType) {
			case STANDSTILL:
				movementType = EntityChocobo.MovementType.FOLLOW_OWNER;
				break;
			case FOLLOW_OWNER:
				movementType = EntityChocobo.MovementType.WANDER;
				break;
			case WANDER:
				movementType = EntityChocobo.MovementType.STANDSTILL;
				break;
		}
		updateMovementButton();
	}

	private void updateMovementButton() {
		switch (movementType) {
			case STANDSTILL:
				followOwnerButton.displayString =  "Stand Still";
				break;
			case FOLLOW_OWNER:
				followOwnerButton.displayString = "Follow Owner";
				break;
			case WANDER:
				followOwnerButton.displayString = "Wander";
				break;
			default:
				followOwnerButton.displayString = "ERROR";
				break;
		}
	}

	private void displayInputBox(InputType name) {
		currentInputType = name;
		inputTextField.setText("");
		inputTextField.setVisible(true);
		inputTextField.setCanLoseFocus(false);
		inputTextField.setFocused(true);
	}

	private void closeInputBox() {
		currentInputType = InputType.none;
		inputTextField.setVisible(false);
		inputTextField.setCanLoseFocus(true);
		inputTextField.setFocused(false);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
