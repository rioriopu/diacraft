package riorio.diamondcraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import riorio.diamondcraft.block.machine.MachineDiamondAlloySmelter;
import riorio.diamondcraft.common.AlloySmeltingRecipe;
import riorio.diamondcraft.common.SmeltingRecipe;

public class TileEntityMachineDiamondAlloySmelter extends TileEntity implements ISidedInventory {

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 4, 1 };
	private static final int[] slotsSides = new int[] { 1 };

	private ItemStack[] alloySmelterItemStacks = new ItemStack[10];

	public int furnacePower;
	public int furnaceCookTime;
	public int maxPower;
	public static final int mashingSpeed = 200;

	private String customName;

	@Override
	public int getSizeInventory() {
		return this.alloySmelterItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(final int i) {
		return this.alloySmelterItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(final int i, final int j) {
		if (this.alloySmelterItemStacks[i]!=null) {

			ItemStack itemstack;

			if (this.alloySmelterItemStacks[i].stackSize<=j) {

				itemstack = this.alloySmelterItemStacks[i];
				this.alloySmelterItemStacks[i] = null;
				return itemstack;
			}

			final ItemStack itemstack1 = this.alloySmelterItemStacks[i].splitStack(j);

			if (this.alloySmelterItemStacks[i].stackSize==0)
				this.alloySmelterItemStacks[i] = null;
			return itemstack1;
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int i) {
		if (this.alloySmelterItemStacks[i]!=null) {
			final ItemStack itemstack = this.alloySmelterItemStacks[i];
			this.alloySmelterItemStacks[i] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(final int i, final ItemStack itemstack) {
		this.alloySmelterItemStacks[i] = itemstack;
		if (itemstack!=null&&itemstack.stackSize>getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.alloySmelter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName!=null&&this.customName.length()>0;
	}

	public void func_145951_a(final String string) {
		this.customName = string;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		final NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.alloySmelterItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i<nbttaglist.tagCount(); i++) {
			final NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			final byte b0 = nbt1.getByte("Slot");

			if (b0>=0&&0<this.alloySmelterItemStacks.length)
				this.alloySmelterItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbt1);
		}

		this.furnacePower = nbt.getShort("PowerTime");
		this.furnaceCookTime = nbt.getShort("CookTime");
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("PowerTime", (short) this.furnacePower);
		nbt.setShort("CookTime", (short) this.furnaceCookTime);
		final NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i<this.alloySmelterItemStacks.length; ++i)
			if (this.alloySmelterItemStacks[i]!=null) {
				final NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.alloySmelterItemStacks[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}

		nbt.setTag("Items", nbttaglist);

		if (hasCustomInventoryName())
			nbt.setString("CustomName", this.customName);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord)!=this)
			return false;
		else
			return player.getDistanceSq(this.xCoord+0.5D, this.yCoord+0.5D, this.zCoord+0.5D)<=64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack item) {
		return i==2 ? false : i==1 ? hasItemPower(item) : true;
	}

	public boolean hasItemPower(final ItemStack item) {
		return getItemPower(item)>0;
	}

	//燃料の指定
	public static int getItemPower(final ItemStack itemstack) {
		if (itemstack==null)
			return 0;
		else {
			final Item item = itemstack.getItem();

			if (item instanceof ItemBlock&&Block.getBlockFromItem(item)!=Blocks.air) {
				final Block block = Block.getBlockFromItem(item);

				if (block==Blocks.coal_block)
					return 16000;
			}

			if (item==Items.coal)
				return 200;

			return 0;
		}
	}

	@Override
	public boolean canInsertItem(final int var1, final ItemStack itemstack, final int var3) {
		return isItemValidForSlot(var1, itemstack);
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
		return j!=0||i!=1||itemstack.getItem()==Items.bucket;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int i) {
		return i==0 ? slotsBottom : i==1 ? slotsTop : slotsSides;
	}

	public int getSmelterProgressScaled(final int i) {
		return this.furnaceCookTime*i/200;
	}

	public int getPowerReamingScaled(final int i) {
		if (this.maxPower==0)
			this.maxPower = 200;

		return this.furnacePower*1/this.maxPower;
	}

	private boolean canSmelt() {

		if (this.alloySmelterItemStacks[0]==null||this.alloySmelterItemStacks[1]==null||this.alloySmelterItemStacks[2]==null||this.alloySmelterItemStacks[3]==null)
			return false;

		final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.alloySmelterItemStacks[0]);
		final ItemStack itemstack1 = AlloySmeltingRecipe.smelting().getSmeltingResult(this.alloySmelterItemStacks[3], itemstack, itemstack, itemstack);

		if (itemstack==null)
			return false;

		if (this.alloySmelterItemStacks[3]==null)
			return true;

		if (this.alloySmelterItemStacks[3].isItemEqual(itemstack))
			if (this.alloySmelterItemStacks[3].isItemEqual(itemstack1))
				return false;

		if (this.alloySmelterItemStacks[3].stackSize<getInventoryStackLimit()&&this.alloySmelterItemStacks[3].stackSize<this.alloySmelterItemStacks[3].getMaxStackSize())
			return true;
		else
			return this.alloySmelterItemStacks[3].stackSize<itemstack.getMaxStackSize();
	}

	private void smeltItem() {

		if (canSmelt()) {
			final ItemStack itemstack = SmeltingRecipe.smelting().getSmeltingResult(this.alloySmelterItemStacks[0]);

			if (this.alloySmelterItemStacks[5]==null)
				this.alloySmelterItemStacks[5] = itemstack.copy();
			else if (this.alloySmelterItemStacks[5].isItemEqual(itemstack))
				this.alloySmelterItemStacks[5].stackSize += itemstack.stackSize;

			for (int i = 0; i<2; i++) {
				if (this.alloySmelterItemStacks[i].stackSize<=0)
					this.alloySmelterItemStacks[i] = new ItemStack(this.alloySmelterItemStacks[i].getItem().setFull3D());
				else
					this.alloySmelterItemStacks[i].stackSize--;

				if (this.alloySmelterItemStacks[i].stackSize<=0)
					this.alloySmelterItemStacks[i] = null;
			}
		}
	}

	public boolean hasPower() {
		return this.furnacePower>0;
	}

	public boolean isMashing() {
		return this.furnaceCookTime>200;
	}

	@Override
	public void updateEntity() {
		final boolean flag = hasPower();
		boolean flag1 = false;

		if (hasPower()&&isMashing())
			this.furnacePower--;

		if (!this.worldObj.isRemote) {
			if (this.furnacePower==0&&canSmelt()) {
				this.furnacePower += getItemPower(this.alloySmelterItemStacks[2]);

				if (this.alloySmelterItemStacks[2]!=null) {
					flag1 = true;

					--this.alloySmelterItemStacks[2].stackSize;

					if (this.alloySmelterItemStacks[2].stackSize==0)
						this.alloySmelterItemStacks[2] = this.alloySmelterItemStacks[2].getItem().getContainerItem(this.alloySmelterItemStacks[2]);
				}
			}

			if (hasPower()&&canSmelt()) {
				this.furnaceCookTime++;

				if (this.furnaceCookTime==TileEntityMachineDiamondAlloySmelter.mashingSpeed) {
					this.furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else
				this.furnaceCookTime = 0;

			if (flag!=isMashing()) {
				flag1 = true;
				MachineDiamondAlloySmelter.updateAlloySmelterBlockState(isMashing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1)
			markDirty();
	}
}