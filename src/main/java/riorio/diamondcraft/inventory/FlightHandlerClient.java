package riorio.diamondcraft.inventory;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import riorio.diamondcraft.item.ItemFlightRing;

@SideOnly(Side.CLIENT)
public class FlightHandlerClient {

	private static boolean canFly;
	private static boolean isFlying;
	private static byte time;
	private static byte flyToggleTimer;
	private static byte sprintToggleTimer;
	private static EntityPlayerSP player;

	/** 初期化処理。 */
	public static void init() {
		FMLLog.info("Client init");
		// プレイヤーのインスタンスを代入する。
		player = Minecraft.getMinecraft().thePlayer;
		time = 20;
	}

	/** プレイヤーのインベントリを調査し、飛行可能かどうかを更新する。 */
	public static void checkPlayer() {
		time = 0;
		canFly = false;
		// インベントリを取得。
		final IInventory inventory = player.inventory;
		for (int i = 0; i<inventory.getSizeInventory(); i++) {
			// インベントリを調査し、有効になっているFlight Ringがあれば飛行を許可する。
			final ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack==null||!(itemStack.getItem() instanceof ItemFlightRing)||!itemStack.hasTagCompound())
				continue;
			if (!itemStack.getTagCompound().getBoolean("IsFlying"))
				continue;
			canFly = true;
			break;
		}
		sendMessageToServer();
	}

	/** プレイヤーの飛行を許可する。 */
	public static void allowPlayerToFly() {
		canFly = true;
		sendMessageToServer();
	}

	/** プレイヤーの飛行を禁止する。 */
	public static void forbidPlayerToFly() {
		canFly = false;
		sendMessageToServer();
	}

	/** サーバーに飛行の可否を伝える。 */
	public static void sendMessageToServer() {
		FMLLog.info("sendMessageToServer:"+canFly);
		//DiamondCraft.wrapper.sendToServer(new DiamondCraftFlightMessage(canFly));
	}

	/** プレイヤーの飛行が許可されているかどうか。 */
	public static boolean canFly() {
		return canFly;
	}

	/** 本人プレイヤーかどうか。 */
	public static boolean isPlayer(final Entity entity) {
		return player.getCommandSenderName().equals(entity.getCommandSenderName());
	}

	/** プレイヤーを飛行させる処理。 */
	public static void onPlayerFly() {
		// 20 tickに一度プレイヤーのインベントリを確認する。
		time++;
		if (time>20)
			checkPlayer();
		// クリエイティブモードか、飛行禁止なら終了。
		if (player.capabilities.isCreativeMode||!canFly)
			return;
		// ジャンプ入力があったかどうか。
		final boolean jump = player.movementInput.jump;
		// ダッシュのための変数。
		final float f0 = 0.8F;
		// 移動速度がf0以上かどうか。
		final boolean flag1 = player.movementInput.moveForward>=f0;
		// 入力情報を更新。
		player.movementInput.updatePlayerMoveState();

		// 新たにジャンプ入力があるとき、
		if (!jump&&player.movementInput.jump)
			if (flyToggleTimer==0)
				// 10 tick以内にジャンプ入力がなければ、タイマーを設定。
				flyToggleTimer = 10;
			else {
				// 10 tick以内にジャンプ入力があったなら飛行しているかを反転し、タイマーをリセット。
				isFlying = !isFlying;
				flyToggleTimer = 0;
			}

		// 満腹度がダッシュ可能な量残っているか。
		final boolean flag2 = player.getFoodStats().getFoodLevel()>0.0F;
		if (player.onGround&&!flag1&&player.movementInput.moveForward>=f0&&!player.isSprinting()&&flag2&&!player.isUsingItem()&&!player.isPotionActive(Potion.blindness))
			// ダッシュを設定する。
			if (sprintToggleTimer==0)
				sprintToggleTimer = 7;
			else {
				player.setSprinting(true);
				sprintToggleTimer = 0;
			}

		// タイマーを進める。
		if (sprintToggleTimer>0)
			--sprintToggleTimer;
		if (flyToggleTimer>0)
			--flyToggleTimer;

		// プレイヤーが地上についているなら飛行を中断。
		if (player.onGround&&isFlying)
			isFlying = false;

		if (isFlying) {
			// 飛行中なら落下を無効にし、キー入力によって移動を設定する。
			player.motionY = 0D;
			player.jumpMovementFactor = 0.08f;
			if (player.movementInput.sneak)
				player.motionY -= 0.4D;
			if (player.movementInput.jump)
				player.motionY += 0.4D;
		} else
			// 飛行が中断されているなら空中移動速度をデフォルトにする。
			player.jumpMovementFactor = 0.02f;
	}

}