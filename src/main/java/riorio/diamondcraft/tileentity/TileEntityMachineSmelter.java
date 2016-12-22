package riorio.diamondcraft.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import riorio.diamondcraft.block.machine.MachineDiamondSmelter;
import riorio.diamondcraft.common.SmeltingRecipe;

public class TileEntityMachineSmelter extends TileEntity implements ISidedInventory {

	//private static Map classToNameMap = new HashMap();
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private ItemStack[] furnaceItemStacks = new ItemStack[3];

	public int smelterBurnTime;
	public int currentBurnTime;
	public int smelterCookTime;

	private String furnaceName;

	public void furnaceName(final String string) {
		this.furnaceName = string;
	}

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return this.furnaceItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(final int par1, final int par2) {
		if (this.furnaceItemStacks[par1]!=null) {
			ItemStack itemstack;
			if (this.furnaceItemStacks[par1].stackSize<=par2) {
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);

				if (this.furnaceItemStacks[par1].stackSize==0)
					this.furnaceItemStacks[par1] = null;
				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot) {
		if (this.furnaceItemStacks[slot]!=null) {
			final ItemStack itemstack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemstack) {
		this.furnaceItemStacks[slot] = itemstack;

		if (itemstack!=null&&itemstack.stackSize>getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();

	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.furnaceName : "Diamond Furnace";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.furnaceName!=null&&this.furnaceName.length()>0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		final NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i<tagList.tagCount(); ++i) {
			final NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			final byte byte0 = tabCompound1.getByte("Slot");

			if (byte0>=0&&byte0<this.furnaceItemStacks.length)
				this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
		}

		this.smelterBurnTime = tagCompound.getShort("BurnTime");
		this.smelterCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

		if (tagCompound.hasKey("CustomName", 8))
			this.furnaceName = tagCompound.getString("CustomName");
	}

	protected ItemStack[] itemStacks = new ItemStack[54];

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		final NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i<this.itemStacks.length; i++) {
			if (this.itemStacks[i]==null)
				continue;
			final NBTTagCompound nbt1 = new NBTTagCompound();
			nbt1.setByte("Slot", (byte) i);
			this.itemStacks[i].writeToNBT(nbt1);
			nbttaglist.appendTag(nbt1);
		}
		nbt.setTag("Items", nbttaglist);
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(final int par1) {
		return this.smelterCookTime*par1/200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(final int par1) {
		if (this.currentBurnTime==0)
			this.currentBurnTime = 200;

		return this.smelterBurnTime*par1/this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.smelterBurnTime>0;
	}

	@Override
	public void updateEntity() {
		final boolean flag = this.smelterBurnTime>0;
		boolean flag1 = false;

		if (this.smelterBurnTime>0)
			--this.smelterBurnTime;

		if (!this.worldObj.isRemote) {
			if (this.smelterBurnTime==0&&canSmelt()) {
				this.currentBurnTime = this.smelterBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

				if (this.smelterBurnTime>0) {
					flag1 = true;
					if (this.furnaceItemStacks[1]!=null) {
						--this.furnaceItemStacks[1].stackSize;

						if (this.furnaceItemStacks[1].stackSize==0)
							this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
					}
				}
			}

			if (isBurning()&&canSmelt()) {
				++this.smelterCookTime;
				if (this.smelterCookTime==200) {
					this.smelterCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else
				this.smelterCookTime = 0;
		}

		if (flag!=this.smelterBurnTime>0) {
			flag1 = true;
			MachineDiamondSmelter.updateBlockState(this.smelterBurnTime>0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}

		if (flag1)
			markDirty();
	}

	private boolean canSmelt() {
		if (this.furnaceItemStacks[0]==null)
			return false;
		else {
			final ItemStack itemstack = SmeltingRecipe.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			if (itemstack==null)
				return false;
			if (this.furnaceItemStacks[2]==null)
				return true;
			if (!this.furnaceItemStacks[2].isItemEqual(itemstack))
				return false;
			final int result = this.furnaceItemStacks[2].stackSize+itemstack.stackSize;
			return result<=getInventoryStackLimit()&&result<=this.furnaceItemStacks[2].getMaxStackSize();
		}
	}

	public void smeltItem() {
		if (canSmelt()) {
			final ItemStack itemstack = SmeltingRecipe.smelting().getSmeltingResult(this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[2]==null)
				this.furnaceItemStacks[2] = itemstack.copy();
			else if (this.furnaceItemStacks[2].getItem()==itemstack.getItem())
				this.furnaceItemStacks[2].stackSize += itemstack.stackSize;

			--this.furnaceItemStacks[0].stackSize;

			if (this.furnaceItemStacks[0].stackSize>=0)
				this.furnaceItemStacks[0] = null;
		}
	}

	public static int getItemBurnTime(final ItemStack itemstack) {
		if (itemstack==null)
			return 0;
		else {
			final Item item = itemstack.getItem();

			//			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air){
			//				Block block = Block.getBlockFromItem(item);
			//
			//
			//			}

			if (item instanceof ItemTool&&((ItemTool) item).getToolMaterialName().equals("EMERALD"))
				return 300;
			return GameRegistry.getFuelValue(itemstack);
		}
	}

	public static boolean isItemFuel(final ItemStack itemstack) {
		return getItemBurnTime(itemstack)>0;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord)!=this ? false : player.getDistanceSq(this.xCoord+0.5D, this.yCoord+0.5D, this.zCoord+0.5D)<=64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(final int par1, final ItemStack itemstack) {
		return par1==2 ? false : par1==1 ? isItemFuel(itemstack) : true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int par1) {
		return par1==0 ? slotsBottom : par1==1 ? slotsTop : slotsSides;
	}

	@Override
	public boolean canInsertItem(final int par1, final ItemStack itemstack, final int par3) {
		return isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(final int par1, final ItemStack itemstack, final int par3) {
		return par3!=0||par1!=1||itemstack.getItem()==Items.bucket;
	}

}
