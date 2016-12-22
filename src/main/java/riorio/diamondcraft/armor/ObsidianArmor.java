package riorio.diamondcraft.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import riorio.diamondcraft.common.DiamondCraft;

public class ObsidianArmor extends ItemArmor {

	public ObsidianArmor(final ItemArmor.ArmorMaterial p_i45325_1_, final int p_i45325_2_, final int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final net.minecraft.entity.Entity entity, final int slot, final String type) {
		if (stack.getItem()==DiamondCraft.ObsidianHelmet||stack.getItem()==DiamondCraft.ObsidianChestplate||stack.getItem()==DiamondCraft.ObsidianBoots)
			return "diamondcraft:textures/models/armor/obsidian_layer_1.png";
		if (stack.getItem()==DiamondCraft.ObsidianLeggings)
			return "diamondcraft:textures/models/armor/obsidian_layer_2.png";
		else
			return null;
	}

	@Override
	public void registerIcons(final IIconRegister reg) {
		if (this==DiamondCraft.ObsidianHelmet)
			this.itemIcon = reg.registerIcon("diamondcraft:obsidian_helmet");
		if (this==DiamondCraft.ObsidianChestplate)
			this.itemIcon = reg.registerIcon("diamondcraft:obsidian_chestplate");
		if (this==DiamondCraft.ObsidianLeggings)
			this.itemIcon = reg.registerIcon("diamondcraft:obsidian_leggings");
		if (this==DiamondCraft.ObsidianBoots)
			this.itemIcon = reg.registerIcon("diamondcraft:obsidian_boots");
	}

	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
		if (player.getCurrentArmor(0)!=null&&player.getCurrentArmor(1)!=null&&player.getCurrentArmor(2)!=null&&player.getCurrentArmor(3)!=null) {
			final ItemStack boots = player.getCurrentArmor(0);
			final ItemStack legs = player.getCurrentArmor(1);
			final ItemStack chest = player.getCurrentArmor(2);
			final ItemStack helmet = player.getCurrentArmor(3);

			if (boots.getItem()==DiamondCraft.ObsidianBoots&&legs.getItem()==DiamondCraft.ObsidianLeggings&&chest.getItem()==DiamondCraft.ObsidianChestplate&&helmet.getItem()==DiamondCraft.ObsidianHelmet) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 1, 1));
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 0));
			}
		}
	}

}