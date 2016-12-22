package riorio.diamondcraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import riorio.diamondcraft.common.DiamondCraft;
import riorio.diamondcraft.inventory.FlightHandlerClient;
import riorio.diamondcraft.inventory.FlightHandlerServer;

public class ItemFlightRing extends Item {

	public ItemFlightRing() {
		super();
		setCreativeTab(CreativeTabs.tabMisc);
		setHasSubtypes(false);
		setMaxStackSize(1);
	}

	/** アップデート時の処理。 */
	@Override
	public void onUpdate(ItemStack itemStack, final World world, final Entity entity, final int slot, final boolean isHeld) {
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
		// ダメージ間隔を取得。
		final int duration = nbt.getInteger("Duration");
		// 0より大きいなら1減らす。
		if (duration>0)
			nbt.setInteger("Duration", duration-1);
		// 減らしたうえで0より大きいなら終了。
		if (duration-1>0)
			return;
		// モードに応じた量のダメージを与える。
		itemStack.damageItem(DiamondCraft.amountDamage, (EntityLivingBase) entity);
		// 耐久値が残っているなら間隔を再設定して終了。
		if (itemStack.getItemDamage()+DiamondCraft.amountDamage<=itemStack.getMaxDamage()) {
			nbt.setInteger("Duration", 100);
			return;
		}
		// 耐久値が残っていないなら破壊。
		itemStack = null;
		if (world.isRemote&&FlightHandlerClient.isPlayer(entity))
			FlightHandlerClient.checkPlayer();
	}

	/** 右クリック時の処理。 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer player) {
		if (player.isSneaking()) {
			// スニークしていたら無効にする。
			if (world.isRemote)
				player.addChatMessage(new ChatComponentTranslation("飛行モード解除"));
			if (world.isRemote)
				player.addChatMessage(new ChatComponentTranslation("右クリックで起動"));
			itemStack.getTagCompound().setBoolean("IsFlying", false);
			if (world.isRemote)
				FlightHandlerClient.checkPlayer();
		} else {
			// スニークしていなければ有効にする。
			if (world.isRemote)
				player.addChatMessage(new ChatComponentTranslation("飛行モード"));
			if (world.isRemote)
				player.addChatMessage(new ChatComponentTranslation("Shift + 右クリックで停止"));
			itemStack.getTagCompound().setBoolean("IsFlying", true);
			if (world.isRemote)
				FlightHandlerClient.allowPlayerToFly();
		}
		return itemStack;

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
