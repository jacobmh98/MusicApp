package mainApplication;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import mainApplication.Track.TimeBlock;

public class AudioPlayer {
	public AudioPlayer() {
	}
	
	public void playKey(int audioID, int playingType) {
		System.out.println("playing..");
		String location;
		try {
			if(playingType == -1 && audioID < 81) {
				location = "major_cords";
			} else if(playingType == 0 && audioID < 81) {
				location = "minor_cords";
			} else {
				location = "keys";
			}
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("./resources/" + location + "/"+ audioID + ".wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start(); //This plays the audio} catch(Exception e) {}
		
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playSong(Track track) {
		System.out.println("playing song");
		int timeBlockCounter = 0;
		
		for(TimeBlock t : track.getTimeBlocks()) {
			for(SoundBlock s : t.getSoundBlocks()) {
				playKey(Math.abs(s.getKeyID()), s.getPlayingType());
			}
			
			timeBlockCounter++;
			
			if(timeBlockCounter != track.getTimeBlocks().size() && containsMoreSounds(timeBlockCounter, track)) {
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean containsMoreSounds(int timeBlockCounter, Track track) {
		for(TimeBlock t : track.getLeftOverTimeBlocks(timeBlockCounter)) {
			if(t.getSoundBlocks().size() != 0) {
				return true;
			}
		}
		return false;
	}
}
