package riorio.diamondcraft.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import riorio.diamondcraft.common.NDCID;
import riorio.diamondcraft.inventory.ContainerDiamondSmelter;
import riorio.diamondcraft.tileentity.TileEntityMachineSmelter;

@SideOnly(Side.CLIENT)
public class GuiDFurnace extends GuiContainer {

	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(NDCID.MODID+":"+"/textures/gui/container/GuiDiamondSmelter.png");
	private TileEntityMachineSmelter tileFurnace;

	public GuiDFurnace(final InventoryPlayer invPlayer, final TileEntityMachineSmelter tile) {
		super(new ContainerDiamondSmelter(invPlayer, tile));
		this.tileFurnace = tile;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
		final String string = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(this.tileFurnace.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize/2-this.fontRendererObj.getStringWidth(string), 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize-94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float var1, final int var2, final int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		final int k = (this.width-this.xSize)/2;
		final int l = (this.height-this.ySize)/2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.tileFurnace.isBurning()) {
			i1 = this.tileFurnace.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(k+56, l+36+12-i1, 176, 12-i1, 14, i1+2);
		}

		i1 = this.tileFurnace.getCookProgressScaled(24);
		drawTexturedModalRect(k+79, l+34, 176, 14, i1+1, 16);
	}

}
