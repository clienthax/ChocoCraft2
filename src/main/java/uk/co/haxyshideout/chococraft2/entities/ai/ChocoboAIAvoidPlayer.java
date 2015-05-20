package uk.co.haxyshideout.chococraft2.entities.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.haxylib.items.GenericArmor;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

import java.util.List;

/**
 * Created by clienthax on 20/5/2015.
 */
public class ChocoboAIAvoidPlayer extends EntityAIAvoidEntity {

	public ChocoboAIAvoidPlayer(EntityChocobo chocobo, float searchDistance, double farSpeedIn, double nearSpeedIn) {
		super(chocobo, new Predicate()
		{
			public boolean apply(Entity entity)
			{
				if(1 == 1)
					return true;

				if(entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					int chance = 0;
					for (ItemStack stack : player.inventory.armorInventory) {
						if(stack != null) {
							if(stack.getItem() instanceof GenericArmor)//lazy way..
								chance += 25;
						}
					}

					return !RandomHelper.getChanceResult(chance);

				}
				return false;
			}
			public boolean apply(Object object)
			{
				return this.apply((Entity)object);
			}
		}, searchDistance, farSpeedIn, nearSpeedIn);
	}

	@Override
	public boolean shouldExecute() {

		boolean superResult = super.shouldExecute();

		if(superResult && ((EntityChocobo)theEntity).isTamed())
			return false;
		else
			return superResult;
	}


}
