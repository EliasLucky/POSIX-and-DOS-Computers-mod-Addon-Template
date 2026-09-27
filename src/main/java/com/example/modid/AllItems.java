package com.example.modid;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AllItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ComputersAddon.MODID);

    public static final RegistryObject<Item> PLOTTER_ITEM = ITEMS.register(
            "plotter",
            () -> new BlockItem(AllBlocks.PLOTTER.get(), new Item.Properties()));

    private AllItems() {}

    public static void register(IEventBus modBus) { ITEMS.register(modBus); }
}