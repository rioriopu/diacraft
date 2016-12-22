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
import riorio.diamondcraft.tileentity.TileEntityMachineSmelter;

public class MachineDiamondSmelter extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon front;

	private static boolean isBurning;
	private final boolean isBurning2;
	private final Random random = new Random();

	private int GUIID;

	public MachineDiamondSmelter(final boolean isActive) {
		super(Material.rock);
		this.isBurning2 = isActive;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(NDCID.MODID+":DiamondBlockTexture");
		this.front = iconregister.registerIcon(this.isBurning2 ? NDCID.MODID+":DiamondSmelterActive" : NDCID.MODID+":DiamondSmelterInactive");
		this.top = iconregister.registerIcon(NDCID.MODID+":DiamondBlockTexture");
	}

	@Override
	public IIcon getIcon(final int side, final int meta) {
		if (side==1)
			return this.top;
		else if (side==3)
			return this.front;
		else
			return this.blockIcon;
	}

	@Override
	public boolean onBlockActivated(final World World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
		this.GUIID = this.GUIID;
		if (World.isRemote)
			return true;
		else {
			final TileEntityMachineSmelter tileEntityMachineSmelter = (TileEntityMachineSmelter) World.getTileEntity(par2, par3, par4);

			if (tileEntityMachineSmelter!=null)
				par5EntityPlayer.openGui(DiamondCraft.DiamondCraftInstance, GuiHandler.GUI_ID = 0, World, par2, par3, par4);

			return true;
		}
	}

	@Override
	public Item getItemDropped(final int par1, final Random random, final int par3) {
		return Item.getItemFromBlock(DiamondCraft.MachineDiamondSmelter);
	}

	@Override
	public Item getItem(final World world, final int par2, final int par3, final int par4) {
		return Item.getItemFromBlock(DiamondCraft.MachineDiamondSmelter);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World world, final int par2) {
		return new TileEntityMachineSmelter();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockAdded(final World world, final int x, final int y, final int z) {
		super.onBlockAdded(world, x, y, z);
		direction(world, x, y, z);
	}

	private void direction(final World world, final int x, final int y, final int z) {
		if (!world.isRemote) {
			final Block direction = world.getBlock(x, y, z-1);
			final Block direction1 = world.getBlock(x, y, z+1);
			final Block direction2 = world.getBlock(x-1, y, z);
			final Block direction3 = world.getBlock(x+1, y, z);
			byte byte0 = 3;

			if (direction.func_149730_j()&&direction.func_149730_j())
				byte0 = 3;

			if (direction1.func_149730_j()&&direction1.func_149730_j())
				byte0 = 2;

			if (direction2.func_149730_j()&&direction2.func_149730_j())
				byte0 = 5;

			if (direction3.func_149730_j()&&direction3.func_149730_j())
				byte0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, byte0, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack itemstack) {
		final int direction = MathHelper.floor_double(entity.rotationYaw*4.0F/360.0F+0.5D)&3;

		if (direction==0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if (direction==1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if (direction==2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if (direction==3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);

		if (itemstack.hasDisplayName())
			((TileEntityMachineSmelter) world.getTileEntity(x, y, z)).furnaceName(itemstack.getDisplayName());
	}

	public static void updateBlockState(final boolean burning, final World world, final int x, final int y, final int z) {
		final int direction = world.getBlockMetadata(x, y, z);
		final TileEntity tileentity = world.getTileEntity(x, y, z);
		isBurning = true;

		if (burning)
			world.setBlock(x, y, z, DiamondCraft.MachineDiamondSmelterActive);
		else
			world.setBlock(x, y, z, DiamondCraft.MachineDiamondSmelter);

		isBurning = false;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);

		if (tileentity!=null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
		if (!isBurning) {
			final TileEntityMachineSmelter tileentitydsmelter = (TileEntityMachineSmelter) world.getTileEntity(x, y, z);

			if (tileentitydsmelter!=null) {
				for (int i = 0; i<tileentitydsmelter.getSizeInventory(); ++i) {
					final ItemStack itemstack = tileentitydsmelter.getStackInSlot(i);

					if (itemstack!=null) {
						final float f = this.random.nextFloat()*0.6F+0.1F;
						final float f1 = this.random.nextFloat()*0.6F+0.1F;
						final float f2 = this.random.nextFloat()*0.6F+0.1F;

						while (itemstack.stackSize>0) {
							int j = this.random.nextInt(21)+10;

							if (j>itemstack.stackSize)
								j = itemstack.stackSize;

							itemstack.stackSize -= j;
							final EntityItem entityitem = new EntityItem(world, x+f, y+f1, z+f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

							final float f3 = 0.025F;
							entityitem.motionX = (float) this.random.nextGaussian()*f3;
							entityitem.motionY = (float) this.random.nextGaussian()*f3+0.1F;
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
		if (this.isBurning2) {
			final int direction = world.getBlockMetadata(x, y, z);

			final float xx = x+0.5F, yy = y+random.nextFloat()*6.0F/16.0F, zz = z+0.5F, xx2 = random.nextFloat()*0.3F-0.2F, zz2 = 0.5F;

			if (direction==4) {
				world.spawnParticle("smoke", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
			} else if (direction==5) {
				world.spawnParticle("smoke", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
			} else if (direction==3) {
				world.spawnParticle("smoke", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
			} else if (direction==2) {
				world.spawnParticle("smoke", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", xx-zz2, yy, zz+xx2, 0.0F, 0.0F, 0.0F);
			}
		}
	}

}
