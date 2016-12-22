package riorio.diamondcraft.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ToolUltimateDiamondShovel extends ItemSpade {

	public ToolUltimateDiamondShovel(final Item.ToolMaterial mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister reg) {
		this.itemIcon = reg.registerIcon("diamondcraft:ultimatediamond_shovel");
	}

	@Override
	public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int par4, final boolean par5) {
		super.onUpdate(stack, world, entity, par4, par5);
		{
			final EntityPlayer player = (EntityPlayer) entity;
			final ItemStack equipped = player.getCurrentEquippedItem();
			if (equipped==stack)
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 99999, 0));
		}
	}

}
