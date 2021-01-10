package mainApplication;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public AudioPlayer() {}
	
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
}
