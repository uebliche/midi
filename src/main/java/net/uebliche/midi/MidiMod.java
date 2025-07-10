package net.uebliche.midi;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MoverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;

public class MidiMod implements ModInitializer {
    public static final String MOD_ID = "midi";
    
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(MidiAction.ID, MidiAction.CODEC);
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() != 0) {
                    device.open();
                    Transmitter transmitter = device.getTransmitter();
                    transmitter.setReceiver(new MidiReader(device, this::sendCustomPacket));
                }
            } catch (MidiUnavailableException ignored) {
            }
        }
    }
    
    private void sendCustomPacket(CustomPacketPayload payload) {
        if (Minecraft.getInstance().isGameLoadFinished() && Minecraft.getInstance().getConnection() != null)
            ClientPlayNetworking.send(payload);
    }
}