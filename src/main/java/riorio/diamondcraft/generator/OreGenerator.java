package riorio.diamondcraft.generator;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import riorio.diamondcraft.common.DiamondCraft;

public class OreGenerator implements IWorldGenerator {

	@Override
	public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
		if (world.provider.dimensionId==0) {
			generateOreUranium(world, random, chunkX*16, chunkZ*16);
			generateOreLightDiamond(world, random, chunkX*16, chunkZ*16);
		}
	}

	private void generateOreUranium(final World world, final Random random, final int x, final int z) {
		for (int i = 0; i<5; i++) {
			final int uraniumX = x+random.nextInt(16);
			final int uraniumY = random.nextInt(20);
			final int uraniumZ = z+random.nextInt(16);
			new WorldGenMinable(DiamondCraft.OreUranium, 0, 3, Blocks.stone).generate(world, random, uraniumX, uraniumY, uraniumZ);
		}
	}

	private void generateOreLightDiamond(final World world, final Random random, final int x, final int z) {
		for (int i = 0; i<5; i++) {
			final int lightdiamondX = x+random.nextInt(16);
			final int lightdiamondY = random.nextInt(20);
			final int lightdiamondZ = z+random.nextInt(16);
			new WorldGenMinable(DiamondCraft.OreLightDiamond, 1, 3, Blocks.stone).generate(world, random, lightdiamondX, lightdiamondY, lightdiamondZ);
		}
	}

}
