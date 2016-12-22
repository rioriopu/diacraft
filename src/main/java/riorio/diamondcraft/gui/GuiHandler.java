package riorio.diamondcraft.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import riorio.diamondcraft.inventory.ContainerDiamondAlloySmelter;
import riorio.diamondcraft.inventory.ContainerDiamondSmelter;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;
import riorio.diamondcraft.tileentity.TileEntityMachineSmelter;

public class GuiHandler implements IGuiHandler {
	/*サーバー側の処理*/
	public static int GUI_ID;

	public GuiHandler() {

	}

	@Override
	public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		if (GuiHandler.GUI_ID==0)
			return new ContainerDiamondSmelter(player.inventory, (TileEntityMachineSmelter) world.getTileEntity(x, y, z));
		else if (GuiHandler.GUI_ID==1)
			return new ContainerDiamondAlloySmelter(player.inventory, (TileEntityMachineDiamondAlloySmelter) world.getTileEntity(x, y, z));
		return null;
	}

	/*クライアント側の処理*/
	@Override
	public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		if (GuiHandler.GUI_ID==0)
			return new GuiDFurnace(player.inventory, (TileEntityMachineSmelter) world.getTileEntity(x, y, z));
		else if (GuiHandler.GUI_ID==1)
			return new GuiDiamondAlloySmelter(player.inventory, (TileEntityMachineDiamondAlloySmelter) world.getTileEntity(x, y, z));
		return null;
	}

}