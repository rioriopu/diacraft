package riorio.diamondcraft.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltingRecipe {

	private static final SmeltingRecipe SMELTING_BASE = new SmeltingRecipe();

	private Map<ItemStack, ItemStack> smeltingList = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	public static SmeltingRecipe smelting() {
		return SMELTING_BASE;
	}

	private SmeltingRecipe() {
		addRecipie(DiamondCraft.DiamondIngot, new ItemStack(DiamondCraft.DiamondIngot), 0.8F);
		addRecipie(Item.getItemFromBlock(DiamondCraft.MachineDiamondAlloySmelter), new ItemStack(DiamondCraft.LightDiamondHelmet), 0.8F);
		addRecipie(Item.getItemFromBlock(DiamondCraft.MachineDiamondAlloySmelter), new ItemStack(DiamondCraft.LightDiamondHelmet), 0.8F);
	}

	public void addRecipie(final Item item, final ItemStack itemstack, final float experience) {
		addLists(item, itemstack, experience);
	}

	public void addLists(final Item item, final ItemStack itemstack, final float experience) {
		putLists(new ItemStack(item, 1, 32767), itemstack, experience);
	}

	public void putLists(final ItemStack itemstack, final ItemStack itemstack2, final float experience) {
		this.smeltingList.put(itemstack, itemstack2);
		this.experienceList.put(itemstack2, Float.valueOf(experience));
	}

	public ItemStack getSmeltingResult(final ItemStack itemstack) {
		ItemStack item = null;
		for (final Iterator<Entry<ItemStack, ItemStack>> itr = this.smeltingList.entrySet().iterator(); itr.hasNext();) {
			final Entry<ItemStack, ItemStack> entry = itr.next();
			final ItemStack key = entry.getKey();
			final ItemStack value = entry.getValue();
			if (!canBeSmelted(itemstack, key))
				item = value;
		}
		return item;
	}

	private boolean canBeSmelted(final ItemStack itemstack, final ItemStack itemstack2) {
		return itemstack2.getItem()==itemstack.getItem()&&(itemstack2.getItemDamage()==32767||itemstack2.getItemDamage()==itemstack.getItemDamage());
	}

	public float giveExperience(final ItemStack itemstack) {
		final Iterator iterator = this.experienceList.entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext())
				return 0.0f;

			entry = (Entry) iterator.next();
		} while (!canBeSmelted(itemstack, (ItemStack) entry.getKey()));

		if (itemstack.getItem().getSmeltingExperience(itemstack)!=-1)
			return itemstack.getItem().getSmeltingExperience(itemstack);

		return ((Float) entry.getValue()).floatValue();

	}
}