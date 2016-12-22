package riorio.diamondcraft.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import riorio.diamondcraft.common.NDCID;
import riorio.diamondcraft.inventory.ContainerDiamondAlloySmelter;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;

public class GuiDiamondAlloySmelter extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(NDCID.MODID+":"+"/textures/gui/container/GuiAlloySmelter.png");
	private TileEntityMachineDiamondAlloySmelter alloySmelter;

	public GuiDiamondAlloySmelter(final InventoryPlayer invPlayer, final TileEntityMachineDiamondAlloySmelter tileentity) {
		super(new ContainerDiamondAlloySmelter(invPlayer, tileentity));
		this.alloySmelter = tileentity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int i, final int j) {
		final String name = this.alloySmelter.hasCustomInventoryName() ? this.alloySmelter.getInventoryName() : I18n.format(this.alloySmelter.getInventoryName(), new Object[0]);

		this.fontRendererObj.drawString(name, this.xSize/2-this.fontRendererObj.getStringWidth(name)/2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize-96+2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		/*int k = (this.width - this.xSize) /2;
		int l = (this.height - this.ySize) /2;*/
		final int k = (this.width-this.xSize)/2;
		final int l = (this.height-this.ySize)/2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.alloySmelter.hasPower()) {
			i1 = this.alloySmelter.getPowerReamingScaled(12);
			drawTexturedModalRect(k+12, l+30+4-i1, 176, 12-i1, 14, i1+2);
		}

		i1 = this.alloySmelter.getSmelterProgressScaled(24);
		drawTexturedModalRect(k+85, l+32, 176, 14, i1+1, 16);
		i1 = this.alloySmelter.getSmelterProgressScaled(24);
		drawTexturedModalRect(k+85, l+47, 176, 14, i1+1, 16);
	}
}