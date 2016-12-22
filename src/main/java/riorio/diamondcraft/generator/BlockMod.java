package riorio.diamondcraft.generator;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import riorio.diamondcraft.block.OreLightDiamond;

public class BlockMod {

	public static Block ore;

	public static final String[] ORE = new String[] {
			"oreRuby", "oreSapphire", "orePeridot", "oreTopaz"
	};

	public static void init() {

		ore = new OreLightDiamond();
		GameRegistry.registerBlock(ore, OreItem.class, "ore");

		ore = new OreLightDiamond();
		GameRegistry.registerBlock(ore, OreItem.class, "ore");

		for (int i = 0; i<4; i++)
			OreDictionary.registerOre(ORE[i], new ItemStack(ore, 1, i));
	}
}