package main;

import java.nio.ByteBuffer;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import static main.NoteEnum.*;

// Base code taken from WolinLabs
public class SoundPlayer {

	   //This is just an example - you would want to handle LineUnavailable properly...
	   public SoundPlayer(double BPM, List<Note> notes) throws InterruptedException, LineUnavailableException 
	   {
	      final int SAMPLING_RATE = 44100;            // Audio sampling rate
	      final int SAMPLE_SIZE = 2;                  // Audio sample size in bytes
	      final float VOLUME = .6f;
	      
	      int timeCalc = 0;
	      for (Note n: notes) {
	    	  timeCalc += n.getNumberSamples(BPM, SAMPLING_RATE) + Note.getSpacing(BPM, SAMPLING_RATE);
	      }
	      
	      final int TIME = (int) Math.ceil((double)timeCalc/SAMPLING_RATE);						  // Time in seconds to play
	      
	      SourceDataLine line;
	      
	      if (notes.isEmpty()) {
	    	  return;
	    	  //throw new RuntimeException("Notes list must have at least one note in SoundPlayer constructor");
	      }
	      
	      int noteIndex = 0;
	      double fFreq = notes.get(noteIndex).freq;                         // Frequency of sine wave in hz
	      int noteSampleLength = notes.get(noteIndex).getNumberSamples(BPM, SAMPLING_RATE);

	      //Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
	      double fCyclePosition = 0;        

	      //Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big 
	      // endian byte ordering
	      AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
	      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

	      if (!AudioSystem.isLineSupported(info)){
	         System.out.println("Line matching " + info + " is not supported.");
	         throw new LineUnavailableException();
	      }

	      line = (SourceDataLine)AudioSystem.getLine(info);
	      line.open(format);  
	      line.start();

	      // Make our buffer size match audio system's buffer
	      ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());   

	      int ctSamplesTotal = SAMPLING_RATE*TIME;         // Output for roughly 5 seconds
	      int totalValuesWritten = 0;


	      //On each pass main loop fills the available free space in the audio buffer
	      //Main loop creates audio samples for sine wave, runs until we tell the thread to exit
	      //Each sample is spaced 1/SAMPLING_RATE apart in time
	      
	      boolean isDone = false;
	      boolean isBreak = false;
	      
	      while (ctSamplesTotal>0) {
	         double fCycleInc = fFreq/SAMPLING_RATE;  // Fraction of cycle between samples

	         cBuf.clear();                            // Discard samples from previous pass

	      	  // Figure out how many samples we can add
	         int ctSamplesThisPass = line.available()/SAMPLE_SIZE;
	         
	         for (int i=0; i < ctSamplesThisPass; i++) {
	        	 
	        	cBuf.putShort((short)(Short.MAX_VALUE * VOLUME * Math.sin(2*Math.PI * fCyclePosition)));
	        	
	            totalValuesWritten++;
	            if (totalValuesWritten >= noteSampleLength) {
	            	totalValuesWritten = 0;
	            	isBreak = !isBreak;
	            	if (isBreak) {
	            		fCycleInc = 1;
	            		noteSampleLength = Note.getSpacing(BPM, SAMPLING_RATE);
	            	} else {
	            		noteIndex++;
	            		if (noteIndex > notes.size() - 1) {
	            			isDone = true;
	            			break;
	            		}
	            		fFreq = notes.get(noteIndex).freq;
	            		noteSampleLength = notes.get(noteIndex).getNumberSamples(BPM, SAMPLING_RATE);
	            		fCycleInc = fFreq/SAMPLING_RATE;
	            	}
	            }
	            fCyclePosition += fCycleInc;
	            if (fCyclePosition > 1)
	               fCyclePosition -= 1;
	         }

	         //Write sine samples to the line buffer.  If the audio buffer is full, this will 
	         // block until there is room (we never write more samples than buffer will hold)
	         line.write(cBuf.array(), 0, cBuf.position());            
	         ctSamplesTotal -= ctSamplesThisPass;     // Update total number of samples written

	         //Wait until the buffer is at least half empty  before we add more
	         while (line.getBufferSize()/2 < line.available())
	            Thread.sleep(1);
	         
	         if (isDone) {
	        	 System.out.println("DONE!");
	        	 while (line.available() < SAMPLING_RATE) {
	        		 Thread.sleep(1);
	        	 }
	   	      	 line.drain();                                         
	   	      	 line.close();
	        	 return;
	         }
	      }
	   }
	}


