package net.seeals;

import net.fabricmc.api.ModInitializer;

import net.seeals.registry.ModBlocks;
import net.seeals.registry.ModItemGroups;
import net.seeals.registry.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackHome implements ModInitializer {
	public static final String MOD_ID = "backhome";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.modBlocksInit();
		ModItems.modItemInit();
		ModItemGroups.modItemGroupsInit();

	}
}