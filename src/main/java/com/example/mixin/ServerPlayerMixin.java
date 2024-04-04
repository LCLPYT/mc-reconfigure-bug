package com.example.mixin;

import com.example.type.WaitingForConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Shadow public ServerGamePacketListenerImpl connection;

    @Inject(
            method = "sendSystemMessage(Lnet/minecraft/network/chat/Component;Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void example$preventSystemMessageReconfigure(Component component, boolean bl, CallbackInfo ci) {
        // do not send messages when waiting for configuration
        if (((WaitingForConfig) connection).example$isWaiting()) {
            ci.cancel();
        }
    }
}
