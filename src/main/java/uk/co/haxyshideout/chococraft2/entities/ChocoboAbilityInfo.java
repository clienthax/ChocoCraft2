package uk.co.haxyshideout.chococraft2.entities;

import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clienthax on 5/6/2015.
 */
public class ChocoboAbilityInfo {

	private static HashMap<EntityChocobo.ChocoboColor, ChocoboAbilityInfo> abilityInfos = new HashMap<EntityChocobo.ChocoboColor, ChocoboAbilityInfo>();

	private EntityChocobo.ChocoboColor chocoboColor;
	private int maxHP;
	private boolean canClimb = false;
	private boolean canWalkOnWater = false;
	private boolean canFly = false;
	private boolean immuneToFire = false;
	private float landSpeed;
	private float waterSpeed;
	private float airbornSpeed;
	private float mountedStepHeight;
	private float normalStepHeight;
	private List<PotionEffect> effectList = new ArrayList<PotionEffect>();
	private float airSpeed;

	public ChocoboAbilityInfo(EntityChocobo.ChocoboColor chocoboColor) {//TODO tie this into the config system
		if(abilityInfos.containsKey(chocoboColor))
			throw new UnsupportedOperationException("Editing defined abilities is not permitted");
		this.chocoboColor = chocoboColor;
	}

	public static ChocoboAbilityInfo getAbilityInfo(EntityChocobo.ChocoboColor chocoboColor) {
		return abilityInfos.get(chocoboColor);
	}

	public ChocoboAbilityInfo setMaxHP(int maxHP) {
		this.maxHP = maxHP;
		return this;
	}

	public ChocoboAbilityInfo setCanClimb(boolean value) {
		this.canClimb = value;
		return this;
	}
	
	public boolean canClimb()
	{
		return this.canClimb;
	}

	public ChocoboAbilityInfo setCanWalkOnWater(boolean value) {
		this.canWalkOnWater = value;
		return this;
	}
	
	public boolean canWalkOnWater()
	{
		return this.canWalkOnWater;
	}

	public ChocoboAbilityInfo setCanFly(boolean value) {
		this.canFly = value;
		return this;
	}
	
	public boolean canFly()
	{
		return this.canFly;
	}

	public ChocoboAbilityInfo setImmuneToFire(boolean value) {
		this.immuneToFire = value;
		return this;
	}
	
	public boolean isImmuneToFire()
	{
		return this.immuneToFire;
	}

	public ChocoboAbilityInfo setSpeeds(float landSpeed, float waterSpeed, float airbornSpeed) {
		this.landSpeed = landSpeed;
		this.waterSpeed = waterSpeed;
		this.airbornSpeed = airbornSpeed;
		return this;
	}
	
	public float getLandSpeed()
	{
		return this.landSpeed;
	}
	
	public float getWaterSpeed()
	{
		return this.waterSpeed;
	}
	
	public float getAirbornSpeed()
	{
		return this.airbornSpeed;
	}

	public ChocoboAbilityInfo setStepHeight(float mountedStepHeight, float normalStepHeight) {
		this.mountedStepHeight = mountedStepHeight;
		this.normalStepHeight = normalStepHeight;
		return this;
	}

	public ChocoboAbilityInfo setAirSpeed(float speed)
	{
		this.airSpeed = speed;
		return this;
	}
	
	public float getAirSpeed()
	{
		return this.airSpeed;
	}
	
	public ChocoboAbilityInfo setRiderAbilities(List<PotionEffect> effectList) {
		this.effectList = effectList;
		return this;
	}

	public ChocoboAbilityInfo addRiderAbility(PotionEffect effect) {
		this.effectList.add(effect);
		return this;
	}

	public void save() {
		abilityInfos.put(chocoboColor, this);
	}

	public int getMaxHP() {
		return maxHP;
	}

	public float getStepHeight(boolean mounted) {
		return mounted ? mountedStepHeight : normalStepHeight;
	}

	public boolean getCanFly() {
		return canFly;
	}
}
