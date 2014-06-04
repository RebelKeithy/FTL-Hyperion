package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.ButtonToggleable;

public class ListLayoutButton extends ButtonToggleable
{
	private ListGUI list;
	private int page;

	public ListLayoutButton(ListGUI list, int page, int i, int j, Texture autofire_up) 
	{
		super(i, j, autofire_up);
		
		this.list = list;
		this.page = page;
	}

	public void leftClick()
	{
		list.setPage(page);
		this.setSelected(true);
	}
	
}
