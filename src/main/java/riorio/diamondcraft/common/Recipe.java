package riorio.diamondcraft.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipe

{

	public static void registry() {
		//アイテムレシピ
		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.DiamondStick, 1),
				new Object[] {
						" x ",
						" x ",
						'x', Items.diamond });

		//ブロックレシピ
		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.MachineDiamondAlloySmelter, 1),
				new Object[] {
						"xxx",
						"x x",
						"xxx",
						'x', Blocks.diamond_block });

		//防具レシピ
		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.LightDiamondHelmet, 1),
				new Object[] {
						"xxx",
						"x x",
						'x', DiamondCraft.LightDiamond });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.LightDiamondChestplate, 1),
				new Object[] {
						"x x",
						"xxx",
						"xxx",
						'x', DiamondCraft.LightDiamond });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.LightDiamondLeggings, 1),
				new Object[] {
						"xxx",
						"x x",
						"x x",
						'x', DiamondCraft.LightDiamond });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.LightDiamondBoots, 1),
				new Object[] {
						"x x",
						"x x",
						'x', DiamondCraft.LightDiamond });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.ObsidianHelmet, 1),
				new Object[] {
						"xxx",
						"x x",
						'x', Blocks.obsidian });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.ObsidianChestplate, 1),
				new Object[] {
						"x x",
						"xxx",
						"xxx",
						'x', Blocks.obsidian });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.ObsidianLeggings, 1),
				new Object[] {
						"xxx",
						"x x",
						"x x",
						'x', Blocks.obsidian });

		GameRegistry.addRecipe(
				new ItemStack(DiamondCraft.ObsidianBoots, 1),
				new Object[] {
						"x x",
						"x x",
						'x', Blocks.obsidian });

		{

			//ツールレシピ
			//黒曜石ツール
			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianSword, 1),
					new Object[] {
							" y ",
							" y ",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });

			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianPickaxe, 1),
					new Object[] {
							"yyy",
							" x ",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });

			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianShovel, 1),
					new Object[] {
							" y ",
							" x ",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });

			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianAxe, 1),
					new Object[] {
							"yy ",
							"yx ",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });
			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianAxe, 1),
					new Object[] {
							" yy",
							" xy",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });

			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.ObsidianHoe, 1),
					new Object[] {
							"yy ",
							" x ",
							" x ",
							'x', DiamondCraft.DiamondStick,
							'y', Blocks.obsidian });

			//ライトダイヤモンドツール

			//*起動系*
			GameRegistry.addRecipe(
					new ItemStack(DiamondCraft.FlightRing, 1),
					new Object[] {
							"xxx",
							"xyx",
							"xxx",
							'x', DiamondCraft.UltimateDiamondIngot,
							'y', Items.nether_star });

			//プレート作成
			//ウラニウムハンマーで
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.IronPlate, 2, 3),
					new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(DiamondCraft.UraniumHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.GoldPlate, 2, 3),
					new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(DiamondCraft.UraniumHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.DiamondPlate, 2, 3),
					new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(DiamondCraft.UraniumHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.UraniumPlate, 2, 3),
					new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(Items.diamond), new ItemStack(DiamondCraft.UraniumHammer, 1, 32767));
			//ゴールドハンマーで
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.IronPlate, 2, 3),
					new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(DiamondCraft.GoldHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.GoldPlate, 2, 3),
					new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(DiamondCraft.GoldHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.DiamondPlate, 2, 3),
					new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(DiamondCraft.GoldHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.UraniumPlate, 2, 3),
					new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.GoldHammer, 1, 32767));

			//ダイヤモンドハンマーで
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.IronPlate, 2, 3),
					new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(DiamondCraft.DiamondHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.GoldPlate, 2, 3),
					new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(DiamondCraft.DiamondHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.DiamondPlate, 2, 3),
					new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(DiamondCraft.DiamondHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.UraniumPlate, 2, 3),
					new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.DiamondHammer, 1, 32767));

			//アイアンハンマーで
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.IronPlate, 2, 3),
					new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(DiamondCraft.IronHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.GoldPlate, 2, 3),
					new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(DiamondCraft.IronHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.DiamondPlate, 2, 3),
					new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(DiamondCraft.IronHammer, 1, 32767));
			GameRegistry.addShapelessRecipe(new ItemStack(DiamondCraft.UraniumPlate, 2, 3),
					new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.UraniumIngot), new ItemStack(DiamondCraft.IronHammer, 1, 32767));

			return;
		}
	}
}