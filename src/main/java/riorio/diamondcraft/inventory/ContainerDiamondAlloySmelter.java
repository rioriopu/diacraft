package riorio.diamondcraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;

public class ContainerDiamondAlloySmelter extends Container {

	private TileEntityMachineDiamondAlloySmelter alloy;
	private int furnaceCookTime;
	private int furnacePower;
	private int lastItemBurnTime;

	/** 変換機のインベントリの第一スロットの番号 */
	private static final int index0 = 0;
	/** プレイヤーのインベントリの第一スロットの番号 */
	private static final int index1 = 5;
	/** クイックスロットの第一スロットの番号 */
	private static final int index2 = index1+27;
	/** このコンテナの全体のスロット数 */
	private static final int index3 = index2+9;

	public ContainerDiamondAlloySmelter(final InventoryPlayer invPlayer, final TileEntityMachineDiamondAlloySmelter tileentity) {
		this.furnaceCookTime = 0;
		this.furnacePower = 200;
		this.lastItemBurnTime = 0;

		this.alloy = tileentity;

		//スロットの位置
		addSlotToContainer(new Slot(tileentity, 0, 36, 27));
		addSlotToContainer(new Slot(tileentity, 1, 62, 27));
		//燃料スロット
		addSlotToContainer(new Slot(tileentity, 2, 11, 40));
		addSlotToContainer(new Slot(tileentity, 3, 62, 53));
		addSlotToContainer(new Slot(tileentity, 4, 36, 53));
		//精錬後のスロット
		addSlotToContainer(new SlotFurnace(invPlayer.player, tileentity, 5, 120, 40));

		int i;
		for (i = 0; i<3; ++i)
			for (int j = 0; j<9; ++j)
				addSlotToContainer(new Slot(invPlayer, j+i*9+9, 8+j*18, 84+i*18));
		for (i = 0; i<9; ++i)
			addSlotToContainer(new Slot(invPlayer, i, 8+i*18, 142));
	}

	@Override
	public void addCraftingToCrafters(final ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.alloy.furnaceCookTime);
		crafting.sendProgressBarUpdate(this, 1, this.alloy.furnacePower);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
		ItemStack itemstack = null;
		final Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot!=null&&slot.getHasStack()) {
			final ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2==2) {
				if (!mergeItemStack(itemstack1, 3, 39, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (par2!=1&&par2!=0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1)!=null) {
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!mergeItemStack(itemstack1, 1, 2, false))
						return null;
				} else if (par2>=3&&par2<30) {
					if (!mergeItemStack(itemstack1, 30, 39, false))
						return null;
				} else if (par2>=30&&par2<39&&!mergeItemStack(itemstack1, 3, 30, false))
					return null;
			} else if (!mergeItemStack(itemstack1, 3, 39, false))
				return null;

			if (itemstack1.stackSize==0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize==itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player) {
		return this.alloy.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i<this.crafters.size(); i++) {
			final ICrafting k = (ICrafting) this.crafters.get(i);

			if (this.furnaceCookTime!=this.alloy.furnaceCookTime)
				k.sendProgressBarUpdate(this, 0, this.alloy.furnaceCookTime);

			if (this.furnacePower!=this.alloy.furnacePower)
				k.sendProgressBarUpdate(this, 1, this.alloy.furnacePower);
		}

		this.furnaceCookTime = this.alloy.furnaceCookTime;
		this.furnacePower = this.alloy.furnacePower;
	}

	@Override
	public void updateProgressBar(final int i, final int j) {
		if (i==0)
			this.alloy.furnaceCookTime = j;

		if (i==1)
			this.alloy.furnacePower = j;
	}
}