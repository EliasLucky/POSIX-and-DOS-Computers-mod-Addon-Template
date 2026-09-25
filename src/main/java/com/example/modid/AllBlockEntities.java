package com.example.modid;

import com.example.modid.blocks.ExamplePeripheralBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Block entity registry. Each block entity type is bound to the blocks
 * that spawn it — pass every block that owns an instance to
 * {@link BlockEntityType.Builder#of}.
 */
public final class AllBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ComputersAddon.MODID);

	public static final RegistryObject<BlockEntityType<ExamplePeripheralBlockEntity>> EXAMPLE_PERIPHERAL =
			BLOCK_ENTITIES.register("example_peripheral",
					() -> BlockEntityType.Builder.of(
							ExamplePeripheralBlockEntity::new,
							AllBlocks.EXAMPLE_PERIPHERAL.get()
					).build(null));

	private AllBlockEntities() {}

	public static void register(IEventBus modBus) {
		BLOCK_ENTITIES.register(modBus);
	}
}
