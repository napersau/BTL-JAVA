package main;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import main.Disk;
public class Sound {
	
	Clip clip;
	File soundURL[] = new File[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	public Sound() {
		
		soundURL[0] =  new File(Disk.name+ "java-project-group-8\\My2dGame\\res\\sound\\BlueBoyAdventure.wav");
		soundURL[1] =  new File(Disk.name+ "java-project-group-8\\My2dGame\\res\\sound\\coin.wav");
		soundURL[2] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\powerup.wav");
		soundURL[3] = new File(Disk.name+ "java-project-group-8\\My2dGame\\res\\sound\\unlock.wav");
		soundURL[4] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\fanfare.wav");
		soundURL[5] = new File(Disk.name+ "java-project-group-8\\My2dGame\\res\\sound\\hitmonster.wav");
		soundURL[6] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\receivedamage.wav");
		soundURL[7] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\216675__hitrison__stick-swoosh-whoosh (1).wav");
		soundURL[8] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\powerup.wav");
		soundURL[9] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\cursor.wav");
		soundURL[10] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\burning.wav");
		soundURL[11] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\cuttree.wav");
		soundURL[12] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\gameover.wav");
		soundURL[13] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\stairs.wav");
		soundURL[14] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\sleep.wav");
		soundURL[15] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\blocked.wav");
		soundURL[16] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\parry.wav");
		soundURL[17] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\speak.wav");
		soundURL[18] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\Merchant.wav");
		soundURL[19] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\Dungeon.wav");
		soundURL[20] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\chipwall.wav");
		soundURL[21] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\dooropen.wav");
		soundURL[22] = new File(Disk.name+"java-project-group-8\\My2dGame\\res\\sound\\FinalBattle.wav");

	}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
	public void checkVolume() {
		switch(volumeScale) {
		case 0:volume= -80f;break;
		case 1:volume= -20f;break;
		case 2:volume= -12f;break;
		case 3:volume= -5f;break;
		case 4:volume= 1f;break;
		case 5:volume= 6f;break;
		}
		fc.setValue(volume);
		
	}
}
