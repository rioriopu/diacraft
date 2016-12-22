package riorio.diamondcraft.block.machine;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import riorio.diamondcraft.tileentity.TileEntityMachineDiamondAlloySmelter;

public class MachineBase extends BlockContainer {

	protected MachineBase(final Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int metadata) {
		if (metadata==0)
			return new TileEntityMachineDiamondAlloySmelter();
		//else if (metadata==1)
		//	return new TileCompactEngine32();
		return null;
	}

}
