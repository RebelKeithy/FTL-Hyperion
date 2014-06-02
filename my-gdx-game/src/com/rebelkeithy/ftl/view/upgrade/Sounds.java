package com.rebelkeithy.ftl.view.upgrade;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class Sounds 
{
	private static Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	public static void registerSound(String name, String soundFile)
	{
		FileHandle handle = Gdx.files.getFileHandle("resources/audio/waves/" + soundFile, FileType.Absolute);
		Sound sound = Gdx.audio.newSound(handle);
		sounds.put(name, sound);
	}
	
	public static void playSound(String name)
	{
		if(sounds.containsKey(name))
		{
			sounds.get(name).play();
		}
	}
}
