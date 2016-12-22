package riorio.diamondcraft.common;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import riorio.diamondcraft.armor.DiamondArmor;
import riorio.diamondcraft.armor.ObsidianArmor;
import riorio.diamondcraft.armor.UltimateArmor;
import riorio.diamondcraft.block.OreLightDiamond;
import riorio.diamondcraft.block.OreUranium;
import riorio.diamondcraft.block.machine.MachineDiamondAlloySmelter;
import riorio.diamondcraft.block.machine.MachineDiamondSmelter;
import riorio.diamondcraft.generator.OreGenerator;
import riorio.diamondcraft.gui.GuiHandler;
import riorio.diamondcraft.item.ItemDiamond;
import riorio.diamondcraft.item.ItemFlightRing;
import riorio.diamondcraft.tool.ToolLightDiamondAxe;
import riorio.diamondcraft.tool.ToolLightDiamondHoe;
import riorio.diamondcraft.tool.ToolLightDiamondPickaxe;
import riorio.diamondcraft.tool.ToolLightDiamondShovel;
import riorio.diamondcraft.tool.ToolLightDiamondSword;
import riorio.diamondcraft.tool.ToolObsidianAxe;
import riorio.diamondcraft.tool.ToolObsidianHoe;
import riorio.diamondcraft.tool.ToolObsidianPickaxe;
import riorio.diamondcraft.tool.ToolObsidianShovel;
import riorio.diamondcraft.tool.ToolObsidianSword;
import riorio.diamondcraft.tool.ToolUltimateDiamondAxe;
import riorio.diamondcraft.tool.ToolUltimateDiamondHoe;
import riorio.diamondcraft.tool.ToolUltimateDiamondPickaxe;
import riorio.diamondcraft.tool.ToolUltimateDiamondShovel;
import riorio.diamondcraft.tool.ToolUltimateDiamondSword;
import riorio.diamondcraft.tool.hammer.HammerBase;

@Mod(modid = NDCID.MODID, name = NDCID.MODID, version = NDCID.VERSION)
public class DiamondCraft {

	@Instance("NDCID.MODID")
	@SidedProxy(clientSide = "riorio.diamondcraft.common.ClientProxy", serverSide = "riorio.diamondcraft.common.ServerProxy")
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper NETWORK;

	@Metadata
	public static ModMetadata meta;

	@Instance(NDCID.MODID)
	public static DiamondCraft DiamondCraftInstance;

	//GUI_ID;
	public static int GUI_ID;

	public static int amountDamage = 0;

	//int
	public static int D1;
	public static int D2;
	public static int D3;
	public static int D4;

	//enumArmor
	public static ArmorMaterial enumArmorMaterialLightDiamond = EnumHelper.addArmorMaterial("LIGHTDIAMOND", 8, new int[] { 1600, 5, 10, 8, 3 }, 30);
	public static ArmorMaterial enumArmorMaterialObsidian = EnumHelper.addArmorMaterial("Obsidian", 6, new int[] { 3000, 5, 10, 8, 3 }, 30);
	//enumTool
	public static ToolMaterial enumToolMaterialObsidian = EnumHelper.addToolMaterial("ObsidianTool", 4, 1600, 11.0F, 5.0F, 30);
	public static ToolMaterial enumToolMaterialLightDiamond = EnumHelper.addToolMaterial("LightTool", 3, 3000, 11.0F, 6.0F, 30);
	public static ToolMaterial enumToolMaterialUltimateDiamond = EnumHelper.addToolMaterial("UltimateTool", 9999, 0, 99.0F, 99.0F, 999);

	//防具
	//LightDiamond
	public static Item LightDiamondHelmet;
	public static Item LightDiamondChestplate;
	public static Item LightDiamondLeggings;
	public static Item LightDiamondBoots;

	//Obsidian
	public static Item ObsidianHelmet;
	public static Item ObsidianChestplate;
	public static Item ObsidianLeggings;
	public static Item ObsidianBoots;

	//UltimateDiamond
	public static Item UltimateDiamondHelmet;
	public static Item UltimateDiamondChestplate;
	public static Item UltimateDiamondLeggings;
	public static Item UltimateDiamondBoots;

	//*アイテム*
	//ダイヤモンド系
	public static Item BlackDiamond;
	public static Item UltimateDiamond;
	public static Item SuperDiamond;
	public static Item UltraDiamond;
	public static Item LightDiamond;

	//インゴット
	public static Item UraniumIngot;
	public static Item DiamondIngot;
	public static Item DarkDiamondIngot;
	public static Item UltraDiamondIngot;
	public static Item SuperDiamondIngot;
	public static Item UltimateDiamondIngot;
	public static Item BlackDiamondIngot;
	public static Item SteelIngot;
	public static Item TinIngot;
	public static Item ObsidianIngot;
	public static Item UpgradeIngot_MK1;
	public static Item UpgradeIngot_MK2;
	public static Item UpgradeIngot_MK3;

	//プレート
	public static Item DiamondPlate;
	public static Item UraniumPlate;
	public static Item SteelPlate;
	public static Item TinPlate;
	public static Item UpgradePlate_Mk1;
	public static Item IronPlate;
	public static Item GoldPlate;
	public static Item ObsidianPlate;

	//ハンマー
	public static Item TestHammer;
	public static Item IronHammer;
	public static Item GoldHammer;
	public static Item UraniumHammer;
	public static Item SteelHammer;
	public static Item TinHammer;
	public static Item DiamondHammer;

	//普通のアイテム
	public static Item DiamondStick;
	public static Item FlightRing;
	public static Item Uranium;
	public static Item DiamondBase;
	public static Item DiamondFragment;

	//*ブロック*
	//Machine系ブロック
	public static Block MachineDiamondSmelter;
	public static Block MachineDiamondSmelterActive;
	public static Block MachineDiamondAlloySmelter;
	public static Block MachineDiamondAlloySmelterActive;
	public static Block DiamondEngine;

	//普通のブロック
	public static Block BlockDiamond;
	//鉱石
	public static Block OreTin;
	public static Block OreUranium;
	public static Block OreLightDiamond;

	//ツール
	public static Item ObsidianSword;
	public static Item ObsidianPickaxe;
	public static Item ObsidianAxe;
	public static Item ObsidianHoe;
	public static Item ObsidianShovel;
	public static Item LightDiamondSword;
	public static Item LightDiamondPickaxe;
	public static Item LightDiamondAxe;
	public static Item LightDiamondHoe;
	public static Item LightDiamondShovel;
	public static Item UltimateDiamondSword;
	public static Item UltimateDiamondPickaxe;
	public static Item UltimateDiamondAxe;
	public static Item UltimateDiamondHoe;
	public static Item UltimateDiamondShovel;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {

		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("diamondcraft");

		//防具追加
		LightDiamondHelmet = new DiamondArmor(enumArmorMaterialLightDiamond, D1, 0).setUnlocalizedName("LightDiamondHelmet").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondChestplate = new DiamondArmor(enumArmorMaterialLightDiamond, D2, 1).setUnlocalizedName("LightDiamondChestplate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondLeggings = new DiamondArmor(enumArmorMaterialLightDiamond, D3, 2).setUnlocalizedName("LightDiamondLeggings").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondBoots = new DiamondArmor(enumArmorMaterialLightDiamond, D4, 3).setUnlocalizedName("LightDiamondBoots").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianHelmet = new ObsidianArmor(enumArmorMaterialObsidian, D1, 0).setUnlocalizedName("ObsidianHelmet").setMaxDamage(3000).setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianChestplate = new ObsidianArmor(enumArmorMaterialObsidian, D2, 1).setUnlocalizedName("ObsidianChestplate").setMaxDamage(3000).setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianLeggings = new ObsidianArmor(enumArmorMaterialObsidian, D3, 2).setUnlocalizedName("ObsidianLeggings").setMaxDamage(3000).setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianBoots = new ObsidianArmor(enumArmorMaterialObsidian, D4, 3).setUnlocalizedName("ObsidianBoots").setMaxDamage(3000).setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondHelmet = new UltimateArmor(enumArmorMaterialObsidian, D1, 0).setUnlocalizedName("UltimateDiamondHelmet").setMaxDamage(0).setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondChestplate = new UltimateArmor(enumArmorMaterialObsidian, D2, 1).setUnlocalizedName("UltimateDiamondChestplate").setMaxDamage(0).setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondLeggings = new UltimateArmor(enumArmorMaterialObsidian, D3, 2).setUnlocalizedName("UltimateDiamondLeggings").setMaxDamage(0).setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondBoots = new UltimateArmor(enumArmorMaterialObsidian, D4, 3).setUnlocalizedName("UltimateDiamondBoots").setMaxDamage(0).setCreativeTab(DiamondCraft.DiamondCraftTab);

		//*アイテム*
		//インゴット
		DiamondIngot = new ItemDiamond().setUnlocalizedName("DiamondIngot").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DarkDiamondIngot = new ItemDiamond().setUnlocalizedName("DarkDiamondIngot").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltraDiamondIngot = new ItemDiamond().setUnlocalizedName("UltraDiamond Ingot").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondIngot = new ItemDiamond().setUnlocalizedName("UltimateDiamondIngot").setTextureName("diamondcraft:ultimatediamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		SuperDiamondIngot = new ItemDiamond().setUnlocalizedName("SuperDiamondIngot").setTextureName("diamondcraft:superdiamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UraniumIngot = new ItemDiamond().setUnlocalizedName("UraniumIngot").setTextureName("diamondcraft:uranium_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		BlackDiamondIngot = new ItemDiamond().setUnlocalizedName("BlackDiamondIngot").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		TinIngot = new ItemDiamond().setUnlocalizedName("TinIngot").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UpgradeIngot_MK1 = new ItemDiamond().setUnlocalizedName("UpgradeIngotMk1").setTextureName("diamondcraft:diamond_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		SteelIngot = new ItemDiamond().setUnlocalizedName("SteelIngot").setTextureName("diamondcraft:steel_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianIngot = new ItemDiamond().setUnlocalizedName("ObsidianIngot").setTextureName("diamondcraft:obsidian_ingot").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//普通のアイテム
		LightDiamond = new ItemDiamond().setUnlocalizedName("LightDiamond").setTextureName("diamondcraft:lightdiamond").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DiamondStick = new ItemDiamond().setUnlocalizedName("DiamondStick").setTextureName("diamondcraft:diamondstick").setCreativeTab(DiamondCraft.DiamondCraftTab);
		Uranium = new ItemDiamond().setUnlocalizedName("Uranium").setTextureName("diamondcraft:uranium").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DiamondBase = new ItemDiamond().setUnlocalizedName("DiamondBase").setTextureName("diamondcraft:diamondbase").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DiamondFragment = new ItemDiamond().setUnlocalizedName("DiamondFragment").setTextureName("diamondcraft:diamond_fragment").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//ブロック
		//Machine
		MachineDiamondSmelter = new MachineDiamondSmelter(false).setBlockName("MachineDiamondSmelter").setCreativeTab(DiamondCraft.DiamondCraftTab);
		MachineDiamondSmelterActive = new MachineDiamondSmelter(true).setBlockName("MachineDiamondSmelterActive").setBlockName("MachineDiamondSmelterActive").setLightLevel(5.0F).setCreativeTab(DiamondCraft.DiamondCraftTab);
		MachineDiamondAlloySmelter = new MachineDiamondAlloySmelter(false).setBlockName("MachineDiamondAlloySmelter").setCreativeTab(DiamondCraft.DiamondCraftTab);
		MachineDiamondAlloySmelterActive = new MachineDiamondAlloySmelter(true).setBlockName("MachineDiamondAlloySmelterActive").setBlockName("MachineDiamondAlloySmelterActive").setLightLevel(5.0F).setCreativeTab(DiamondCraft.DiamondCraftTab);
		//DiamondEngine = new MachineDiamondEngine().setBlockName("DiamondEngine").setBlockTextureName("diamondcraft:DiamondBlockTexture").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//鉱石

		OreTin = new OreLightDiamond().setBlockName("TinOre").setCreativeTab(DiamondCraft.DiamondCraftTab).setHardness(1.5F);
		OreUranium = new OreUranium().setBlockName("UraniumOre").setBlockTextureName("diamondcraft:uranium_ore").setCreativeTab(DiamondCraft.DiamondCraftTab).setHardness(1.5F);
		OreLightDiamond = new OreLightDiamond().setBlockName("LightDiamondOre").setBlockTextureName("diamondcraft:lightdiamond_ore").setCreativeTab(DiamondCraft.DiamondCraftTab).setHardness(1.5F);
		OreTin = new OreLightDiamond().setBlockName("TinOre").setCreativeTab(DiamondCraft.DiamondCraftTab).setHardness(1.5F);

		//ツール
		ObsidianSword = new ToolObsidianSword(enumToolMaterialObsidian, 6).setTextureName("diamondcraft:obsidian_sword").setUnlocalizedName("ObsidianSword").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianPickaxe = new ToolObsidianPickaxe(enumToolMaterialObsidian).setTextureName("diamondcraft:obsidian_pickaxe").setUnlocalizedName("ObsidianPickaxe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianShovel = new ToolObsidianShovel(enumToolMaterialObsidian).setTextureName("diamondcraft:obsidian_shovel").setUnlocalizedName("ObsidianShovel").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianHoe = new ToolObsidianHoe(enumToolMaterialObsidian).setTextureName("diamondcraft:obsidian_hoe").setUnlocalizedName("ObsidianHoe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianAxe = new ToolObsidianAxe(enumToolMaterialObsidian).setTextureName("diamondcraft:obsidian_axe").setUnlocalizedName("ObsidianAxe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondSword = new ToolLightDiamondSword(enumToolMaterialLightDiamond, 8).setUnlocalizedName("LightDiamondSword").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondPickaxe = new ToolLightDiamondPickaxe(enumToolMaterialLightDiamond).setUnlocalizedName("LightDiamondPickaxe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondShovel = new ToolLightDiamondShovel(enumToolMaterialLightDiamond).setUnlocalizedName("LightDiamondShovel").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondHoe = new ToolLightDiamondHoe(enumToolMaterialLightDiamond).setUnlocalizedName("LightDiamondHoe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		LightDiamondAxe = new ToolLightDiamondAxe(enumToolMaterialLightDiamond).setUnlocalizedName("LightDiamondAxe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondSword = new ToolUltimateDiamondSword(enumToolMaterialUltimateDiamond, 8).setUnlocalizedName("UltimateDiamondSword").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondPickaxe = new ToolUltimateDiamondPickaxe(enumToolMaterialUltimateDiamond).setUnlocalizedName("UltimateDiamondPickaxe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondShovel = new ToolUltimateDiamondShovel(enumToolMaterialUltimateDiamond).setUnlocalizedName("UltimateDiamondShovel").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondHoe = new ToolUltimateDiamondHoe(enumToolMaterialUltimateDiamond).setUnlocalizedName("UltimateDiamondHoe").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UltimateDiamondAxe = new ToolUltimateDiamondAxe(enumToolMaterialUltimateDiamond).setUnlocalizedName("UltimateDiamondAxe").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//ハンマー
		TestHammer = new HammerBase().setUnlocalizedName("TestHammer").setMaxDamage(0).setCreativeTab(DiamondCraft.DiamondCraftTab);
		IronHammer = new HammerBase().setUnlocalizedName("IronHammer").setMaxDamage(256).setTextureName("diamondcraft:IronHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);
		GoldHammer = new HammerBase().setUnlocalizedName("GoldHammer").setMaxDamage(33).setTextureName("diamondcraft:GoldHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);
		SteelHammer = new HammerBase().setUnlocalizedName("SteelHammer").setMaxDamage(1200).setTextureName("diamondcraft:SteelHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UraniumHammer = new HammerBase().setUnlocalizedName("UraniumHammer").setMaxDamage(564).setTextureName("diamondcraft:IronHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);
		TinHammer = new HammerBase().setUnlocalizedName("TinHammer").setMaxDamage(128).setTextureName("diamondcraft:IronHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DiamondHammer = new HammerBase().setUnlocalizedName("DiamondHammer").setMaxDamage(1300).setTextureName("diamondcraft:DiamondHammer").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//プレート
		IronPlate = new ItemDiamond().setUnlocalizedName("IronPlate").setTextureName("diamondcraft:IronPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		GoldPlate = new ItemDiamond().setUnlocalizedName("GoldPlate").setTextureName("diamondcraft:GoldPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		TinPlate = new ItemDiamond().setUnlocalizedName("TinPlate").setTextureName("diamondcraft:TinPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		ObsidianPlate = new ItemDiamond().setUnlocalizedName("ObsidianPlate").setTextureName("diamondcraft:ObsidianPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UraniumPlate = new ItemDiamond().setUnlocalizedName("UraniumPlate").setTextureName("diamondcraft:uraniumplate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		SteelPlate = new ItemDiamond().setUnlocalizedName("SteelPlate").setTextureName("diamondcraft:SteelPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		DiamondPlate = new ItemDiamond().setUnlocalizedName("DiamondPlate").setTextureName("diamondcraft:DiamondPlate").setCreativeTab(DiamondCraft.DiamondCraftTab);
		UpgradePlate_Mk1 = new ItemDiamond().setUnlocalizedName("UpgradePlateMk1").setTextureName("diamondcraft:UpgradePlate_Mk1").setCreativeTab(DiamondCraft.DiamondCraftTab);

		//TileEntity

		//*GameRegistry*
		//防具
		GameRegistry.registerItem(LightDiamondHelmet, "LightDiamondHelmet");
		GameRegistry.registerItem(LightDiamondChestplate, "LightDiamondChestplate");
		GameRegistry.registerItem(LightDiamondLeggings, "LightDiamondLeggings");
		GameRegistry.registerItem(LightDiamondBoots, "LightDiamondBoots");
		GameRegistry.registerItem(ObsidianHelmet, "ObsidianHelmet");
		GameRegistry.registerItem(ObsidianChestplate, "ObsidianChestplate");
		GameRegistry.registerItem(ObsidianLeggings, "ObsidianLeggings");
		GameRegistry.registerItem(ObsidianBoots, "ObsidianBoots");
		GameRegistry.registerItem(UltimateDiamondHelmet, "UltimateDiamondHelmet");
		GameRegistry.registerItem(UltimateDiamondChestplate, "UltimateDiamondChestplate");
		GameRegistry.registerItem(UltimateDiamondLeggings, "UltimateDiamondLeggings");
		GameRegistry.registerItem(UltimateDiamondBoots, "UltimateDiamondBoots");
		//Item
		GameRegistry.registerItem(DiamondIngot, "DiamondIngot");
		GameRegistry.registerItem(DarkDiamondIngot, "DarkDiamondIngot");
		GameRegistry.registerItem(UltraDiamondIngot, "UltraDiamondIngot");
		GameRegistry.registerItem(UltimateDiamondIngot, "UltimateDiamondIngot");
		GameRegistry.registerItem(SuperDiamondIngot, "SuperDiamondIngot");
		GameRegistry.registerItem(BlackDiamondIngot, "BlackDiamondIngot");
		GameRegistry.registerItem(TinIngot, "TinIngot");
		GameRegistry.registerItem(UraniumIngot, "UraniumIngot");
		GameRegistry.registerItem(UpgradeIngot_MK1, "UpgradeIngot Mk1");
		GameRegistry.registerItem(SteelIngot, "SteelIngot");

		//フライト
		FlightRing = new ItemFlightRing().setUnlocalizedName("flightring").setTextureName("diamondcraft:flightring").setMaxDamage(0);

		//普通のアイテム
		GameRegistry.registerItem(DiamondStick, "DiamondStick");
		GameRegistry.registerItem(LightDiamond, "DiamondLightDiamond");
		GameRegistry.registerItem(Uranium, "Uranium");

		//ハンマー
		GameRegistry.registerItem(IronHammer, "IronHammer");
		GameRegistry.registerItem(TestHammer, "TestHammer");
		GameRegistry.registerItem(GoldHammer, "GoldHammer");
		GameRegistry.registerItem(UraniumHammer, "UraniumHammer");
		GameRegistry.registerItem(SteelHammer, "SteelHammer");
		GameRegistry.registerItem(DiamondHammer, "DiamondHammer");
		GameRegistry.registerItem(TinHammer, "TinHammer");

		//プレート
		GameRegistry.registerItem(IronPlate, "IronPlate");
		GameRegistry.registerItem(GoldPlate, "GoldPlate");
		GameRegistry.registerItem(TinPlate, "TinPlate");
		GameRegistry.registerItem(UraniumPlate, "UraniumPlate");
		GameRegistry.registerItem(SteelPlate, "SteelPlate");
		GameRegistry.registerItem(DiamondPlate, "DiamondPlate");
		GameRegistry.registerItem(UpgradePlate_Mk1, "UpgradePlate_Mk1");

		//Block
		//Machine
		GameRegistry.registerBlock(MachineDiamondSmelter, "machineDiamondSmelter");
		GameRegistry.registerBlock(MachineDiamondSmelterActive, "machineDiamondSmelterActive");
		GameRegistry.registerBlock(MachineDiamondAlloySmelter, "machineDiamondAlloySmelter");
		GameRegistry.registerBlock(MachineDiamondAlloySmelterActive, "machineDiamonAlloySmelterActive");
		//GameRegistry.registerBlock(DiamondEngine, "DiamondEngine");

		//鉱石
		GameRegistry.registerBlock(OreUranium, "OreUranium");
		GameRegistry.registerBlock(OreLightDiamond, "OreLightDiamond");

		//燃料
		GameRegistry.registerFuelHandler(new IFuelHandler() {
			@Override
			public int getBurnTime(final ItemStack fuel) {
				if (fuel.getItem().equals(Items.apple))
					return 1;
				return 0;
			}
		});

		//ツール
		GameRegistry.registerItem(LightDiamondSword, "LightDiamondSword");
		GameRegistry.registerItem(LightDiamondPickaxe, "LightDiamondPickaxe");
		GameRegistry.registerItem(LightDiamondAxe, "LightDiamondAxe");
		GameRegistry.registerItem(LightDiamondHoe, "LightDiamondHoe");
		GameRegistry.registerItem(LightDiamondShovel, "LightDiamondShovel");
		GameRegistry.registerItem(ObsidianSword, "ObsidianSword");
		GameRegistry.registerItem(ObsidianPickaxe, "ObsidianPickaxe");
		GameRegistry.registerItem(ObsidianAxe, "ObsidianAxe");
		GameRegistry.registerItem(ObsidianHoe, "ObsidianHoe");
		GameRegistry.registerItem(ObsidianShovel, "ObsidianShovel");
		GameRegistry.registerItem(UltimateDiamondSword, "UltimateDiamondSword");
		GameRegistry.registerItem(UltimateDiamondPickaxe, "UltimateDiamondPickaxe");
		GameRegistry.registerItem(UltimateDiamondAxe, "UltimateDiamondAxe");
		GameRegistry.registerItem(UltimateDiamondHoe, "UltimateDiamondHoe");
		GameRegistry.registerItem(UltimateDiamondShovel, "UltimateDiamondShovel");

		//world
		GameRegistry.registerWorldGenerator(new OreGenerator(), 4);

		//TileEntity
		//GameRegistry.registerTileEntity(TileEntityDFurnace.class, NDCID.MODID);
		proxy.registerRenderThings();

		Recipe.registry();
		//MinecraftForge.EVENT_BUS.register(new FlightEventHandler());
		GameRegistry.registerItem(FlightRing, "FlightRing");

	}

	@EventHandler
	public void init(final FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		proxy.registerTileEntities();
	}

	public static CreativeTabs DiamondCraftTab = new CreativeTabs("DiamondCraft") {
		@Override
		public Item getTabIconItem() {
			return Items.diamond;
		}

	};
}
