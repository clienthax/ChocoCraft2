package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.potion.PotionEffect;
import uk.co.haxyshideout.chococraft2.entities.ChocoboAbilityInfo;

import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.*;

/**
 * Created by clienthax on 13/4/2015.
 */
public class ChococraftConfig
{

	public ChococraftConfig()
	{
		loadConfig();
	}

	public void loadConfig()
	{
		// TODO ability stuff may be missing stuff like fall damage modifiers etc
		new ChocoboAbilityInfo(YELLOW).setSpeeds(20, 10, 0).setMaxHP(30).setStepHeight(1, 0.5f).save();
		new ChocoboAbilityInfo(GREEN).setSpeeds(27, 10, 0).setMaxHP(30).setStepHeight(2, 0.5f).save();
		new ChocoboAbilityInfo(BLUE).setSpeeds(27, 55, 0).setMaxHP(30).setStepHeight(1, 0.5f).setCanWalkOnWater(true).save();
		new ChocoboAbilityInfo(WHITE).setSpeeds(35, 45, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).save();
		new ChocoboAbilityInfo(BLACK).setSpeeds(40, 20, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).save();
		new ChocoboAbilityInfo(GOLD).setSpeeds(50, 20, 55).setMaxHP(50).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).setCanFly(true).setImmuneToFire(true).save();// TODO needs particles
		new ChocoboAbilityInfo(PINK).setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true).save();
		new ChocoboAbilityInfo(RED).setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true).save();
		new ChocoboAbilityInfo(PURPLE).setSpeeds(40, 10, 55).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(new PotionEffect(12, 100, -1, true, false)).save();
	}

}
