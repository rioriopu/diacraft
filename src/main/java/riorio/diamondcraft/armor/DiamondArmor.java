package riorio.diamondcraft.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import riorio.diamondcraft.common.DiamondCraft;

public class DiamondArmor extends ItemArmor{

	public DiamondArmor(ItemArmor.ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_)
	{
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}

	public String getArmorTexture(ItemStack stack, net.minecraft.entity.Entity entity, int slot, String type) {
		if ((stack.getItem() == DiamondCraft.LightDiamondHelmet) || (stack.getItem() == DiamondCraft.LightDiamondChestplate) || (stack.getItem() == DiamondCraft.LightDiamondBoots)) {
			return "diamondcraft:textures/models/armor/lightdiamond_layer_1.png";
		}
		if (stack.getItem() == DiamondCraft.LightDiamondLeggings) {
			return "diamondcraft:textures/models/armor/lightdiamond_layer_2.png";
		}
		else return null;
		}

		@Override
		public void registerIcons(IIconRegister reg){
			if(this == DiamondCraft.LightDiamondHelmet)
				this.itemIcon = reg.registerIcon("diamondcraft:lightdiamond_helmet");
			if(this == DiamondCraft.LightDiamondChestplate)
				this.itemIcon = reg.registerIcon("diamondcraft:lightdiamond_chestplate");
			if(this == DiamondCraft.LightDiamondLeggings)
				this.itemIcon = reg.registerIcon("diamondcraft:lightdiamond_leggings");
			if(this == DiamondCraft.LightDiamondBoots)
				this.itemIcon = reg.registerIcon("diamondcraft:lightdiamond_boots");
		}
		@Override
		public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
			if(player.getCurrentArmor(0) != null && player.getCurrentArmor(1) != null && player.getCurrentArmor(2) != null && player.getCurrentArmor(3) != null){
				ItemStack boots = player.getCurrentArmor(0);
				ItemStack legs = player.getCurrentArmor(1);
				ItemStack chest = player.getCurrentArmor(2);
				ItemStack helmet = player.getCurrentArmor(3);

				if(boots.getItem() == DiamondCraft.LightDiamondBoots && legs.getItem() == DiamondCraft.LightDiamondLeggings && chest.getItem() == DiamondCraft.LightDiamondChestplate && helmet.getItem() == DiamondCraft.LightDiamondHelmet){
					player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 100, 1));
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 0));
				}
			}
		}

	}
