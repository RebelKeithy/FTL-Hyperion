package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rebelkeithy.ftl.view.FTLView;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-gdx-game";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new FTLView(), cfg);
	}
}
