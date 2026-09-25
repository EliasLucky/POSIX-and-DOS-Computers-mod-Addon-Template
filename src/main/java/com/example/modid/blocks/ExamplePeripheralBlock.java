package com.example.modid.blocks;

import com.eliaslucky.mc_dos.blocks.ICustomCreativeTab;
import com.example.modid.AllCreativeTabs;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The example peripheral block. Instantiates an
 * {@link ExamplePeripheralBlockEntity} which implements
 * {@code com.eliaslucky.mc_dos.api.hardware.Peripheral}.
 */
public class ExamplePeripheralBlock extends Block implements EntityBlock, ICustomCreativeTab {
	public ExamplePeripheralBlock(Properties props) {
		super(props);
	}

	@Override
	public ResourceKey<CreativeModeTab> getCreativeTab() {
		return AllCreativeTabs.ADDON_TAB.getKey();
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ExamplePeripheralBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
			Level level, BlockState state, BlockEntityType<T> type) {
		return (lvl, pos, st, be) -> {
			if (be instanceof ExamplePeripheralBlockEntity e) {
				ExamplePeripheralBlockEntity.tick(lvl, pos, st, e);
			}
		};
	}
}
