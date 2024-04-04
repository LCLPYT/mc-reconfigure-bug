package com.example.mixin;

import com.example.type.WaitingForConfig;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ServerboundConfigurationAcknowledgedPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin extends ServerCommonPacketListenerImpl implements WaitingForConfig {

    @Shadow private boolean waitingForSwitchToConfig;

    // pass-through
    public ServerGamePacketListenerImplMixin(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraftServer, connection, commonListenerCookie);
    }

    @Override
    public boolean example$isWaiting() {
        return waitingForSwitchToConfig;
    }

    @Inject(
            method = "handleConfigurationAcknowledged",
            at = @At("TAIL")
    )
    public void example$startConfiguration(ServerboundConfigurationAcknowledgedPacket serverboundConfigurationAcknowledgedPacket, CallbackInfo ci) {
        // initiate configuration manually
        if (this.connection.getPacketListener() instanceof ServerConfigurationPacketListenerImpl listener) {
            listener.startConfiguration();
        }
    }
}
