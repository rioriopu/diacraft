package riorio.diamondcraft.inventory;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class FlightEventHandler {

	private boolean allowLevitatiton = true;//飛べるかどうか

	/** Entityがワールドに追加された時の処理。 */
	@SubscribeEvent
	public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
		// サーバー側か、プレイヤーでないなら終了。
		if (!event.world.isRemote||!(event.entity instanceof EntityPlayer))
			return;
		final EntityPlayer player = (EntityPlayer) event.entity;
		FMLLog.info("onEntityJoinWorld");
		if (player.getCommandSenderName().equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
			// 本人プレイヤーならFlightHandlerを初期化。
			FlightHandlerClient.init();
	}

	/** EntityLivingBaseのアップデート時の処理。 */
	@SubscribeEvent
	public void onLivingUpdate(final LivingUpdateEvent event) {
		// サーバー側か、本人プレイヤーでないなら終了。
		if (!event.entityLiving.worldObj.isRemote||!FlightHandlerClient.isPlayer(event.entityLiving))
			return;
		// 飛行可能でないなら終了。
		if (!FlightHandlerClient.canFly())
			return;
		// プレイヤーを飛行させる。
		FlightHandlerClient.onPlayerFly();
	}

	/** EntityLivingBaseが落下した時の処理。 */
	@SubscribeEvent
	public void onPlayerFall(final LivingFallEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (this.allowLevitatiton)
				event.setCanceled(true);
		}
	}

}
