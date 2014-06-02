package com.rebelkeithy.ftl.view.scene;

import com.badlogic.gdx.Screen;

public interface FTLScreen extends Screen
{
	public boolean click(int screenX, int screenY, int button);
	
	public void setTooltipText(String text, int x, int y, int width, int height);
}
