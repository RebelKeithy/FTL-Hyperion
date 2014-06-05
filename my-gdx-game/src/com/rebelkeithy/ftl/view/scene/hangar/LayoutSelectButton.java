package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.ButtonToggleable;

public class LayoutSelectButton extends ButtonToggleable
{
	private HangerScreen hanger;
	private String shipName;
	
	public LayoutSelectButton(HangerScreen hanger, int x, int y, Texture image_up) 
	{
		super(x, y, image_up);
		this.hanger = hanger;
	}
	
	public void setShip(String shipName)
	{
		this.shipName = shipName;
	}
	
	public String getShip()
	{
		return shipName;
	}
	
	public void leftClick()
	{
		super.leftClick();
		
		hanger.setShip(this);
	}
}
