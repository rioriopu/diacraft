package riorio.diamondcraft.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import riorio.diamondcraft.common.SmeltingRecipe;
import riorio.diamondcraft.tileentity.TileEntityMachineSmelter;

public class ContainerDiamondSmelter extends Container {

	private TileEntityMachineSmelter tileFurnace;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerDiamondSmelter(final InventoryPlayer player, final TileEntityMachineSmelter tileEntityFurnace) {
		this.tileFurnace = tileEntityFurnace;
		addSlotToContainer(new Slot(tileEntityFurnace, 0, 56, 17));
		addSlotToContainer(new Slot(tileEntityFurnace, 1, 56, 53));
		addSlotToContainer(new SlotFurnace(player.player, tileEntityFurnace, 2, 116, 35));
		int i;

		for (i = 0; i<3; ++i)
			for (int j = 0; j<9; ++j)
				addSlotToContainer(new Slot(player, j+i*9+9, 8+j*18, 84+i*18));

		for (i = 0; i<9; ++i)
			addSlotToContainer(new Slot(player, i, 8+i*18, 142));
	}

	@Override
	public void addCraftingToCrafters(final ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileFurnace.smelterCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileFurnace.smelterBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileFurnace.currentBurnTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i<this.crafters.size(); ++i) {
			final ICrafting craft = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime!=this.tileFurnace.smelterCookTime)
				craft.sendProgressBarUpdate(this, 0, this.tileFurnace.smelterCookTime);

			if (this.lastBurnTime!=this.tileFurnace.smelterBurnTime)
				craft.sendProgressBarUpdate(this, 1, this.tileFurnace.smelterBurnTime);

			if (this.lastItemBurnTime!=this.tileFurnace.currentBurnTime)
				craft.sendProgressBarUpdate(this, 2, this.tileFurnace.currentBurnTime);
		}

		this.lastBurnTime = this.tileFurnace.smelterBurnTime;
		this.lastCookTime = this.tileFurnace.smelterCookTime;
		this.lastItemBurnTime = this.tileFurnace.currentBurnTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int par1, final int par2) {
		if (par1==0)
			this.tileFurnace.smelterCookTime = par2;

		if (par1==1)
			this.tileFurnace.smelterBurnTime = par2;

		if (par1==2)
			this.tileFurnace.currentBurnTime = par2;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player) {
		return this.tileFurnace.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int par2) {
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
				if (SmeltingRecipe.smelting().getSmeltingResult(itemstack1)!=null) {
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				} else if (TileEntityMachineSmelter.isItemFuel(itemstack1)) {
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
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}

}
