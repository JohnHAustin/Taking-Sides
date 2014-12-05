package org.flan.LD26;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundManager 
{	
	private static HashMap<String, Audio> audio = new HashMap<String, Audio>();
		
	public static void play(String s, float vol, float pitch)
	{
		if(!audio.containsKey(s))
		{
			try
			{
				audio.put(s, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/" + s + ".wav")));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		audio.get(s).playAsSoundEffect(vol, pitch / 3F, false);
	}	
	
	public static void playMusic(String s, float vol, float pitch)
	{
		stop();
		if(!audio.containsKey(s))
		{
			try
			{
				audio.put(s, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/" + s + ".wav")));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		audio.get(s).playAsMusic(vol, pitch, false);
	}
	
	public static void stop()
	{
		for(Audio someAudio : audio.values())
		{
			someAudio.stop();
		}
	}
}
