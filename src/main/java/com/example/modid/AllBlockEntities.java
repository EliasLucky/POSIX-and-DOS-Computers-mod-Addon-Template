package com.example.computersaddon;

import com.example.computersaddon.blocks.PlotterBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AllBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ComputersAddon.MODID);

    public static final RegistryObject<BlockEntityType<PlotterBlockEntity>> PLOTTER =
            BLOCK_ENTITIES.register("plotter",
                    () -> BlockEntityType.Builder.of(
                            PlotterBlockEntity::new,
                            AllBlocks.PLOTTER.get()
                    ).build(null));

    private AllBlockEntities() {}

    public static void register(IEventBus modBus) { BLOCK_ENTITIES.register(modBus); }
}