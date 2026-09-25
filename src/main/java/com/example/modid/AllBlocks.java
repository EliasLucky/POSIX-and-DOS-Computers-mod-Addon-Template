package com.example.modid;

import com.example.modid.blocks.ExamplePeripheralBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Block registry. Add new block entries here and register the class
 * with {@link #register(IEventBus)} from the mod constructor.
 */
public final class AllBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ComputersAddon.MODID);

	/**
	 * Example peripheral. Adjacent to a computer, this block becomes
	 * available as a device the OS can talk to (via a driver).
	 */
	public static final RegistryObject<Block> EXAMPLE_PERIPHERAL =
			BLOCKS.register("example_peripheral",
					() -> new ExamplePeripheralBlock(BlockBehaviour.Properties.of()
							.mapColor(MapColor.METAL)
							.strength(2.0F)
							.sound(SoundType.METAL)
							.requiresCorrectToolForDrops()));

	private AllBlocks() {}

	public static void register(IEventBus modBus) {
		BLOCKS.register(modBus);
	}
}
