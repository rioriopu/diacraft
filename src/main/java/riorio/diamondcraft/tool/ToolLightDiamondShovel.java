package riorio.diamondcraft.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ToolLightDiamondShovel extends ItemSpade {

	public ToolLightDiamondShovel(final Item.ToolMaterial mat) {
		super(mat);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister reg) {
		this.itemIcon = reg.registerIcon("diamondcraft:lightdiamond_shovel");

	}

}
