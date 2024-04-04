# Minecraft reconfiguration bug fix demo
Entering the configuration networking phase does not currently work, when transitioning from the play phase.
The reason being a game message packet, which is sent on in the configuration protocol, which is unsupported.

This small example mod demonstrates how this can be fixed.

*The 1.20.4 version can be found [here](https://github.com/LCLPYT/mc-reconfigure-bug/tree/1.20.4)*.

## Patches (using mixins)
The main fix here is in the `java/com/example/mixin/ServerPlayerMixin.java` class, which cancels the sending of system messages, when the configuration was requested earlier.

(optional) The other class `java/com/example/mixin/ServerGamePacketListenerImplMixin.java` is only there for demonstration purposes.
It initiates the actual configuration start, so that the client is not stuck indefinitely.