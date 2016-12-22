package riorio.diamondcraft.block.machine;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import riorio.diamondcraft.common.DiamondCraft;
import riorio.diamondcraft.common.NDCID;
import riorio.diamondcraft.gui.GuiHandler;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;

public class MachineDiamondAlloySmelter extends BlockContainer {

	private Random random = new Random();
	private final boolean isActive;
	private static boolean keepInventory = true;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public MachineDiamondAlloySmelter(final boolean blockState) {
		super(Material.iron);
		this.isActive = blockState;
	}

	public void onBlocAdded(final World world, final int x, final int y, final int z) {
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
		this.iconFront = iconregister.registerIcon(this.isActive ? NDCID.MODID+":DiamondSmelterActive" : NDCID.MODID+":DiamondSmelterInactive");
		this.iconTop = iconregister.registerIcon(NDCID.MODID+":DiamondBlockTexture");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int side, final int meta) {
		return side==1 ? this.iconTop : side==0 ? this.iconTop : side==3 ? this.iconFront : side!=meta ? this.blockIcon : this.iconFront;
	}

	@Override
	public boolean onBlockActivated(final World World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
		if (World.isRemote)
			return true;
		else {
			final TileEntityMachineDiamondAlloySmelter tileEntityMachineDiamondAlloySmelter = (TileEntityMachineDiamondAlloySmelter) World.getTileEntity(par2, par3, par4);

			if (tileEntityMachineDiamondAlloySmelter!=null)
				par5EntityPlayer.openGui(DiamondCraft.DiamondCraftInstance, GuiHandler.GUI_ID = 1, World, par2, par3, par4);

			return true;
		}
	}

	public static void updateAlloySmelterBlockState(final boolean isMashing, final World world, final int xCoord, final int yCoord, final int zCoord) {
		final int l = world.getBlockMetadata(xCoord, yCoord, zCoord);
		final TileEntity entity = world.getTileEntity(xCoord, yCoord, zCoord);
		keepInventory = true;

		if (isMashing)
			world.setBlock(xCoord, yCoord, zCoord, DiamondCraft.MachineDiamondAlloySmelter);
		else
			world.setBlock(xCoord, yCoord, zCoord, DiamondCraft.MachineDiamondAlloySmelterActive);

		keepInventory = false;
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, l, 2);

		if (entity!=null) {
			entity.validate();
			world.setTileEntity(xCoord, yCoord, zCoord, entity);
		}
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int tile) {
		return new TileEntityMachineDiamondAlloySmelter();
	}

	@Override
	public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack item) {
		final int l = MathHelper.floor_double(entity.rotationYaw*4.0F/360.0F+0.5D)&3;

		if (l==0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if (l==1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if (l==2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if (l==3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
		if (!keepInventory) {
			final TileEntityMachineDiamondAlloySmelter tileentity = (TileEntityMachineDiamondAlloySmelter) world.getTileEntity(x, y, z);

			if (tileentity!=null) {
				for (int j1 = 0; j1<tileentity.getSizeInventory(); ++j1) {
					final ItemStack itemstack = tileentity.getStackInSlot(j1);

					if (itemstack!=null) {
						final float f = this.random.nextFloat()*0.8F+0.1F;
						final float f1 = this.random.nextFloat()*0.8F+0.1F;
						final float f2 = this.random.nextFloat()*0.8F+0.1F;

						while (itemstack.stackSize>0) {
							int k1 = this.random.nextInt(21)+10;

							if (k1>itemstack.stackSize)
								k1 = itemstack.stackSize;

							itemstack.stackSize -= k1;
							final EntityItem entityitem = new EntityItem(world, x+f, y+f1, z+f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

							final float f3 = 0.05F;
							entityitem.motionX = (float) this.random.nextGaussian()*f3;
							entityitem.motionY = (float) this.random.nextGaussian()*f3+0.2F;
							entityitem.motionZ = (float) this.random.nextGaussian()*f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random random) {
		if (this.isActive) {
			final int l = world.getBlockMetadata(x, y, z);
			final float f = x+0.5F;
			final float f1 = y+0.0F+random.nextFloat()*6.0F/16.0F;
			final float f2 = z+0.5F;
			final float f3 = 0.52F;
			final float f4 = random.nextFloat()*0.6F-0.3F;

			if (l==4) {
				world.spawnParticle("smoke", f-f3, f1, f2+f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f-f3, f1, f2+f4, 0.0D, 0.0D, 0.0D);
			} else if (l==5) {
				world.spawnParticle("smoke", f+f3, f1, f2+f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f3, f1, f2+f4, 0.0D, 0.0D, 0.0D);
			} else if (l==2) {
				world.spawnParticle("smoke", f+f4, f1, f2-f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4, f1, f2-f3, 0.0D, 0.0D, 0.0D);
			} else if (l==3) {
				world.spawnParticle("smoke", f+f4, f1, f2+f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4, f1, f2+f3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World world, final int x, final int y, final int z, final int meta) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
	}

	@Override
	public Item getItemDropped(final int i, final Random random, final int j) {
		return Item.getItemFromBlock(DiamondCraft.MachineDiamondAlloySmelter);
	}
}
