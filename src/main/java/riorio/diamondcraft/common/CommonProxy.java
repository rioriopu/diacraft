package riorio.diamondcraft.common;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import riorio.diamondcraft.gui.GuiHandler;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;
import riorio.diamondcraft.tileentity.TileEntityMachineSmelter;

public abstract class CommonProxy {
	public void registerRenderThings() {
	}

	public int addArmor(final String armor) {
		return 0;
	}

	public void registerNetworkStuff() {
		NetworkRegistry.INSTANCE.registerGuiHandler(DiamondCraft.DiamondCraftInstance, new GuiHandler());
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityMachineSmelter.class, NDCID.MODID+"DiamondSmelter");
		GameRegistry.registerTileEntity(TileEntityMachineDiamondAlloySmelter.class, NDCID.MODID+"AlloyFurnace");
		//GameRegistry.registerTileEntity(TileEntityEngineDiamond.class, "engine.diamondengine");
	}

}
