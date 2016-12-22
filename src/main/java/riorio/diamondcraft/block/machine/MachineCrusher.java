package riorio.diamondcraft.block.machine;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riorio.diamondcraft.common.NDCID;

public class MachineCrusher extends BlockContainer {
	private Random random = new Random();
	private final boolean isOnline;
	private static boolean keepInventory = true;

	@SideOnly(Side.CLIENT)
	private IIcon Top;
	@SideOnly(Side.CLIENT)
	private IIcon Front;

	public MachineCrusher(final boolean blockstate) {
		super(Material.iron);
		this.isOnline = blockstate;
	}

	public void onBlockActivated(final World world, final int x, final int y, final int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(final World world, final int x, final int y, final int z) {
		if (!world.isRemote) {
			final Block block = world.getBlock(x, y, z-1);
			final Block block1 = world.getBlock(x, y, z+1);
			final Block block2 = world.getBlock(x-1, y, z);
			final Block block3 = world.getBlock(x+1, y, z);
			byte b0 = 3;

			if (block.func_149730_j()&&!block1.func_149730_j())
				b0 = 3;

			if (block1.func_149730_j()&&!block.func_149730_j())
				b0 = 2;

			if (block2.func_149730_j()&&!block3.func_149730_j())
				b0 = 5;

			if (block3.func_149730_j()&&!block2.func_149730_j())
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(NDCID.MODID+":DiamondBlockTexture");
		this.Front = iconregister.registerIcon(this.isOnline ? NDCID.MODID+":DiamondSmelterActive" : NDCID.MODID+":DiamondSmelterInactive");
		this.Top = iconregister.registerIcon(NDCID.MODID+":DiamondBlockTexture");
	}

	@Override
	public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
