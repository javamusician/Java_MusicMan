package JavaMusician.MusicMan.MIDIPlayer;

import java.io.FileOutputStream;
import java.util.Scanner;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;   
import javax.sound.midi.Sequence;   
import javax.sound.midi.Sequencer;   
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
  
public class MIDIPlayer   
{   
    private static Sequence sequence;   
    private static Sequencer sequencer;     
    
    public static void play(String message)
    {
    	try    
        {    
            sequencer=MidiSystem.getSequencer();
            sequence=new Sequence(Sequence.PPQ, 5);
            Track track1=sequence.createTrack();
            ShortMessage sh=new ShortMessage();
            sh.setMessage(192,0,0);
            track1.add(new MidiEvent(sh,00));
            ShortMessage sh1=new ShortMessage();
            sh1.setMessage(144,Integer.parseInt(message),127);
            track1.add(new MidiEvent(sh1,1));
            FileOutputStream out=new FileOutputStream("D:\\test.txt");
            MidiSystem.write(sequence,MidiSystem.getMidiFileTypes()[1],out);
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(300);
            sequencer.start();
        } 
        catch (Exception ex) 
        {   
       	    ex.printStackTrace();
        }
    }
} 