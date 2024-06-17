package net.seeals;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.seeals.events.GlassBottleTicker;
import net.seeals.events.SleepEventHandler;
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

		//register sleep event
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (!player.isSpectator()) {
					for (int i = 0; i < player.getInventory().size(); i++) {
						ItemStack stack = player.getInventory().getStack(i);
						if (stack.isOf(Items.GLASS_BOTTLE)) {
							GlassBottleTicker.tick(stack, player.getWorld(), player, i);
						}
					}
				}

				if (player.isSleeping() && player.getSleepTimer() == 100) { // Check if player just woke up
					BackHome.LOGGER.info("Player just woke up!");
					SleepEventHandler.onPlayerSleep(player, true, true);
				}
			}
		});
	}
}