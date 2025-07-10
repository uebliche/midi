package net.uebliche.midi;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MidiAction(
String name,
int command,
int channel,
int data1,
int data2
) implements CustomPacketPayload {
    #if MC_VER <= MC_1_21_1
    public static final Type<MidiAction> ID = new Type<>(ResourceLocation.tryParse("midi:action"));
    #else
    public static final Type<MidiAction> ID = new Type<>(ResourceLocation.parse("midi:action"));
    #endif
    public static final StreamCodec<FriendlyByteBuf, MidiAction> CODEC = CustomPacketPayload.codec(
    MidiAction::write,
    MidiAction::new
    );
    
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
    
    public MidiAction(final FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readUtf(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(),
        packetBuffer.readInt());
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeInt(command);
        buf.writeInt(channel);
        buf.writeInt(data1);
        buf.writeInt(data2);
    }
}
