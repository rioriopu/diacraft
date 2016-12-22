package riorio.diamondcraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import riorio.diamondcraft.common.DiamondCraft;

public class OreUranium extends Block {
	public OreUranium() {
		super(Material.rock);
		final String BebeniumBlock = "BebeniumBlock";
		setHardness(1.5F);/*硬さ*/
	}

	@Override
	public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
		return riorio.diamondcraft.common.DiamondCraft.OreUranium==DiamondCraft.OreUranium ? DiamondCraft.Uranium : Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(final Random random) {
		//ドロップさせる量を返す
		return 1;
	}

}