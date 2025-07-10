package net.uebliche.midi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.function.Consumer;

public class MidiReader implements Receiver {
    
    private static final Logger log = LoggerFactory.getLogger(MidiReader.class);
    
    private MidiDevice device;
    private Consumer<MidiAction> action;
    private String name;
    
    public MidiReader(MidiDevice device, Consumer<MidiAction> action) {
        this.device = device;
        this.action = action;
        this.name = device.getDeviceInfo().getName();
        log.debug("Opened MIDI device {}", name);
    }
    
    public MidiDevice getDevice() {
        return device;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage sm) {
            int command = sm.getCommand();
            int channel = sm.getChannel();
            int data1 = sm.getData1();
            int data2 = sm.getData2();
            
            log.debug("Received MIDI: command={}, channel={}, data1={}, data2={}",
            command, channel, data1, data2);
            action.accept(new MidiAction(name, command, channel, data1, data2));
        } else {
            log.debug("Received non-short MIDI message");
        }
    }
    
    @Override
    public void close() {
        log.debug("Closing MIDI device {}", name);
        device.close();
    }
}
