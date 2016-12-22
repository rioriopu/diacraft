package riorio.diamondcraft.tool.hammer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HammerBase extends Item {
	private boolean repair = false;

	public HammerBase() {
		super();
		setMaxStackSize(1);
		//効果がわかりやすいように数値を低く設定
	}
	//鉄ハンマーレシピ

	//アイテムがクラフト後に戻らないようにする
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(final ItemStack par1ItemStack) {
		return false;
	}

	//修理以外ならクラフト後にgetContainerItemStackを呼び出す
	@Override
	public boolean hasContainerItem() {
		return !this.repair;
	}

	//修理かどうかを判定する
	@SubscribeEvent
	public void onCrafting(final ItemCraftedEvent event) {
		//IDが無くなったので、アイテムインスタンスで比較。

	}

	//クラフト後のアイテムを、ダメージを与えて返す
	@Override
	public ItemStack getContainerItem(final ItemStack itemStack) {
		if (itemStack!=null&&itemStack.getItem()==this)
			itemStack.setItemDamage(itemStack.getItemDamage()+1);
		return itemStack;
	}

}
