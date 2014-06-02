package com.rebelkeithy.ftl.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.rebelkeithy.ftl.view.scene.FTLScreen;

public class InputHandler implements InputProcessor
{
	private List<Button> buttons = new ArrayList<Button>();;

	public Object selected;
	
	private long mouseStillTime;
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		screenY = Gdx.graphics.getHeight() - screenY;
		/*for(Button buttonObj : buttons)
		{
			if(buttonObj.containsPoint(screenX, screenY))
			{
				if(button == Buttons.RIGHT)
					buttonObj.rightClick();
				if(button == Buttons.LEFT)
					buttonObj.leftClick();
			}
		}*/
		
		if(button == 0)
		{
			selected = null;
		}
		
		boolean used = ((FTLScreen)FTLView.instance().getScreen()).click(screenX, screenY, button);
		
		return used;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) 
	{
		mouseStillTime = System.currentTimeMillis();
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerButton(Button button) 
	{
		this.buttons.add(button);
	}

	public long mouseStillTime() 
	{
		return mouseStillTime;
	}

}
