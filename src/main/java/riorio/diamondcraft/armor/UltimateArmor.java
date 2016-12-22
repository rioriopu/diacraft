package riorio.diamondcraft.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import riorio.diamondcraft.common.DiamondCraft;
import riorio.diamondcraft.inventory.FlightHandlerClient;
import riorio.diamondcraft.inventory.FlightHandlerServer;

public class UltimateArmor extends ItemArmor {

	public UltimateArmor(final ItemArmor.ArmorMaterial p_i45325_1_, final int p_i45325_2_, final int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}

	@Override
	public String getArmorTexture(final ItemStack stack, final net.minecraft.entity.Entity entity, final int slot, final String type) {
		if (stack.getItem()==DiamondCraft.LightDiamondHelmet||stack.getItem()==DiamondCraft.LightDiamondChestplate||stack.getItem()==DiamondCraft.LightDiamondBoots)
			return "diamondcraft:textures/models/armor/ultimatediamond_layer_1.png";
		if (stack.getItem()==DiamondCraft.LightDiamondLeggings)
			return "diamondcraft:textures/models/armor/ultimatediamond_layer_2.png";
		else
			return null;
	}

	@Override
	public void registerIcons(final IIconRegister reg) {
		if (this==DiamondCraft.LightDiamondHelmet)
			this.itemIcon = reg.registerIcon("diamondcraft:ultimatediamond_helmet");
		if (this==DiamondCraft.LightDiamondChestplate)
			this.itemIcon = reg.registerIcon("diamondcraft:ultimatediamond_chestplate");
		if (this==DiamondCraft.LightDiamondLeggings)
			this.itemIcon = reg.registerIcon("diamondcraft:ultimatediamond_leggings");
		if (this==DiamondCraft.LightDiamondBoots)
			this.itemIcon = reg.registerIcon("diamondcraft:ultimatediamond_boots");
	}

	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
		if (player.getCurrentArmor(0)!=null&&player.getCurrentArmor(1)!=null&&player.getCurrentArmor(2)!=null&&player.getCurrentArmor(3)!=null) {
			final ItemStack boots = player.getCurrentArmor(0);
			final ItemStack legs = player.getCurrentArmor(1);
			final ItemStack chest = player.getCurrentArmor(2);
			final ItemStack helmet = player.getCurrentArmor(3);

			if (boots.getItem()==DiamondCraft.LightDiamondBoots&&legs.getItem()==DiamondCraft.LightDiamondLeggings&&chest.getItem()==DiamondCraft.LightDiamondChestplate&&helmet.getItem()==DiamondCraft.LightDiamondHelmet)
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 100, 1));
		}
	}

	@Override
	public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int slot, final boolean isHeld) {
		// 第一引数に不備があるなら終了。
		if (itemStack==null||itemStack.getItem()!=this)
			return;
		// 持ち主がLivingBase継承でないなら終了。
		if (!(entity instanceof EntityLivingBase))
			return;
		// ItemStackがNBTを持っていなければ設定。
		if (!itemStack.hasTagCompound())
			itemStack.setTagCompound(new NBTTagCompound());
		// ItemStackのNBTを取得。
		final NBTTagCompound nbt = itemStack.getTagCompound();
		// 持ち主が地上にいるか、飛行中でないか、無効になっているならダメージ間隔を設定して終了。
		if (entity.onGround||!isFlying(entity)||!nbt.getBoolean("IsFlying")) {
			nbt.setInteger("Duration", 100);
			return;
		}
	}

	/** 飛行しているかどうか。 */
	private boolean isFlying(final Entity entity) {
		// プレイヤーでないならfalse。
		if (!(entity instanceof EntityPlayer))
			return false;
		if (!entity.worldObj.isRemote)
			// サーバー側ならFlightHandlerで判定。
			return FlightHandlerServer.canPlayerFly((EntityPlayer) entity);
		// クライアント側なら本人プレイヤーかどうか判定。
		if (!FlightHandlerClient.isPlayer(entity))
			return false;
		// 本人プレイヤーならFlightHandlerで判定。
		return FlightHandlerClient.canFly();
	}

}
