package com.alekiponi.firmaciv.events;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventHandler {

    /**
     * Set {@link GameRules#RULE_REDUCEDDEBUGINFO} to true
     */
    @SubscribeEvent
    public static void onWorldLoad(final LevelEvent.Load event) {
        if (!(event.getLevel() instanceof final ServerLevel level)) return;
        if (!FirmacivConfig.SERVER.forceReducedDebugInfo.get()) return;

        level.getGameRules().getRule(GameRules.RULE_REDUCEDDEBUGINFO).set(true, level.getServer());
    }

    /**
     * Disable vanilla boats
     */
    @SubscribeEvent
    public static void onEntityInteract(final PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Boat)) return;
        if (!FirmacivConfig.SERVER.disableVanillaBoatFunctionality.get()) return;

        event.setCancellationResult(InteractionResult.FAIL);
        event.setCanceled(true);
    }
}