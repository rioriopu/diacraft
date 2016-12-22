package riorio.diamondcraft.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ToolObsidianShovel extends ItemSpade{

	public ToolObsidianShovel(Item.ToolMaterial mat){
		super(mat);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg){
		this.itemIcon = reg.registerIcon("diamondcraft:obsidian_shovel");
	}

}

