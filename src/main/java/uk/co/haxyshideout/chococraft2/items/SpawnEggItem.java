package uk.co.haxyshideout.chococraft2.items;

import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.haxylib.items.GenericItem;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

/**
 * Created by clienthax on 2/6/2015.
 */
public class SpawnEggItem extends GenericItem {

    private ChocoboColor mobColor;
    
    public SpawnEggItem(ChocoboColor mobColor)
    {
        super();
        
        this.mobColor = mobColor;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode)
            itemstack.stackSize--;

        if (!player.worldObj.isRemote && !player.capabilities.isFlying) {
            player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (RandomHelper.random.nextFloat() * 0.4f + 0.8f));
            EntityChocobo entity = new EntityChocobo(player.worldObj);
            entity.setColor(mobColor);
            entity.setLocationAndAngles(player.posX, player.posY, player.posZ, player.cameraYaw, player.cameraPitch);
            player.worldObj.spawnEntityInWorld(entity);
        }

        return itemstack;
    }

}
