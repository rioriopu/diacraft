package riorio.diamondcraft.inventory;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class DiamondCraftFlightMessage implements IMessage {

	public boolean canFly;

	public DiamondCraftFlightMessage() {
	}

	public DiamondCraftFlightMessage(final boolean canFly) {
		this.canFly = canFly;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.canFly = buf.readBoolean();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeBoolean(this.canFly);
	}

	public static class Handler implements IMessageHandler<DiamondCraftFlightMessage, IMessage> {

		@Override
		public IMessage onMessage(final DiamondCraftFlightMessage message, final MessageContext ctx) {
			// FlightHandlerに送信元プレイヤーの飛行許可状況を通知。
			FlightHandlerServer.setCanPlayerFly(ctx.getServerHandler().playerEntity, message.canFly);
			return null;
		}

	}

}
