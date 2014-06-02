package com.rebelkeithy.ftl.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class TextureRegistry 
{
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture registerSprite(String name, String image)
	{
		if(textures.containsKey(name))
			return textures.get(name);
		
		FileHandle handle = Gdx.files.getFileHandle("resources/img/" + image + ".png", FileType.Absolute);
		Texture texture = new Texture(handle);
		textures.put(name, texture);
		
		return texture;
	}
	
	public static Texture registerSprite(String name, Texture texture) 
	{
		if(textures.containsKey(name))
			return texture;
		
		textures.put(name, texture);
		return texture;
	}
	
	public static Texture getTexture(String name)
	{
		if(!textures.containsKey(name))
			System.out.println("Cannot find texture " + name);
		return textures.get(name);
	}

}
