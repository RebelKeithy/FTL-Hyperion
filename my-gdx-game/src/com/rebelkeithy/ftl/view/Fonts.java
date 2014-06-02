package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts 
{
	public static BitmapFont font8;
	public static BitmapFont font10;
	public static BitmapFont font11;
	public static BitmapFont font11bold;
	public static BitmapFont font12;
	public static BitmapFont font12bold;
	public static BitmapFont ccFont;
	public static BitmapFont ccNewFont;
	public static BitmapFont ccNewBigFont;
	public static BitmapFont ccNew64;
	public static BitmapFont numFont;
	
	public static void init()
	{

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont8.ttf", FileType.Absolute));
		font8 = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont10.ttf", FileType.Absolute));
		font10 = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont11Bold.ttf", FileType.Absolute));
		font11bold = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont11.ttf", FileType.Absolute));
		font11 = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont12Bold.ttf", FileType.Absolute));
		font12bold = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/JustinFont12.ttf", FileType.Absolute));
		font12 = generator.generateFont(16);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/c&c.ttf", FileType.Absolute));
		ccFont = generator.generateFont(12);
		ccFont.setColor(235/256f, 245/256f, 229/256f, 1); 
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/c&cnew.ttf", FileType.Absolute));
		ccNewFont = generator.generateFont(24);
		ccNewFont.setColor(235/256f, 245/256f, 229/256f, 1); 
		ccNewBigFont = generator.generateFont(32);
		ccNew64 = generator.generateFont(48);
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.getFileHandle("resources/fonts/num_font.ttf", FileType.Absolute));
		numFont = generator.generateFont(16);
		numFont.setColor(235/256f, 245/256f, 229/256f, 1); 
		generator.dispose();
	}
}
