package riorio.diamondcraft.generator;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class OreItem extends ItemBlockWithMetadata {

	public OreItem(final Block block) {
		super(block, block);
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemStack) {
		return this.getUnlocalizedName()+"."+itemStack.getItemDamage();
	}
}