package riorio.diamondcraft.inventory;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

public class FlightHandlerServer {

	/** FlightRingを有効にしているプレイヤーの名前のリスト。 */
	private static ArrayList<String> flyingPlayers = new ArrayList<String>();

	/** 初期化処理。 */
	public static void init() {
		flyingPlayers.clear();
	}

	public static void setCanPlayerFly(final EntityPlayer player, final boolean canFly) {
		if (canFly)
			allowPlayerToFly(player);
		else
			forbidPlayerToFly(player);
	}

	public static void allowPlayerToFly(final EntityPlayer player) {
		if (!canPlayerFly(player))
			flyingPlayers.add(player.getCommandSenderName());
	}

	public static void forbidPlayerToFly(final EntityPlayer player) {
		flyingPlayers.remove(player.getCommandSenderName());
	}

	public static boolean canPlayerFly(final EntityPlayer player) {
		return flyingPlayers.contains(player.getCommandSenderName());
	}

}