package com.example.computersaddon;

import com.example.computersaddon.blocks.PlotterBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ComputersAddon.MODID);

    /**
     * A pen plotter. When placed adjacent to a computer, it appears on
     * the peripheral bus as a device of class {@code "plotter"}.
     */
    public static final RegistryObject<Block> PLOTTER = BLOCKS.register(
            "plotter",
            () -> new PlotterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(2.0F)
                    .sound(SoundType.METAL)));

    private AllBlocks() {}

    public static void register(IEventBus modBus) { BLOCKS.register(modBus); }
}