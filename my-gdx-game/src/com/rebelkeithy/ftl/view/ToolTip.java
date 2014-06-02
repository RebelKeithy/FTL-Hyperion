package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ToolTip 
{
	private String text;
	
	private boolean draw = false;
	private int x;
	private int y;
	private int width;
	private int height;
	
	private float stringWidth;
	private float stringHeight;
	
	private Texture white;
	
	public ToolTip()
	{
		white = TextureRegistry.getTexture("white");
		
		if(white == null)
		{
			Pixmap map = new Pixmap(1, 1, Format.RGBA8888);
			map.setColor(Color.WHITE);
			map.fill();
			white = new Texture(map);
			map.dispose();
			TextureRegistry.registerSprite("white", white);
		}
	}
	
	public boolean mouseInside(int x, int y, int width, int height)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		if(mouseX < x || mouseX > x + width || mouseY < y || mouseY > y + height)
		{
			return false;
		}
		
		return true;
	}
	
	public void setToolTip(String text, int x, int y, int width, int height)
	{
		if(!mouseInside(x, y, width, height))
		{
			return;
		}
		
		if(this.text == null || !this.text.equals(text))
		{
			this.stringWidth = Fonts.font8.getMultiLineBounds(text).width;
			this.stringHeight = Fonts.font8.getMultiLineBounds(text).height;
			draw = false;
		}
		
		this.text = text;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void render(SpriteBatch batch)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if(mouseX < x || mouseX > x + width || mouseY < y || mouseY > y + height)
		{
			draw = false;
			text = null;
		}

		if(text != null && System.currentTimeMillis() - FTLView.inputHandler.mouseStillTime() > 00)
		{
			draw = true;
		}
		
		if(draw)
		{
			int margins = 4;
			batch.setColor(0, 0, 0, 0.8f);
			batch.draw(new TextureRegion(white, (int)(stringWidth + margins*2 - 1), (int)(stringHeight + margins*2 - 1)), mouseX - (margins - 1), mouseY - (margins - 1) - stringHeight - 25);
			batch.setColor(1, 1, 1, 1);
			batch.draw(new TextureRegion(white, (int) (stringWidth + margins*2 + 1), 1), mouseX - margins, mouseY - margins - stringHeight - 25);
			batch.draw(new TextureRegion(white, (int) (stringWidth + margins*2 + 1), 1), mouseX - margins, mouseY + stringHeight + margins - stringHeight - 25);
			batch.draw(new TextureRegion(white, 1, (int) (stringHeight + margins*2)), mouseX - margins, mouseY - margins - stringHeight - 25);
			batch.draw(new TextureRegion(white, 1, (int) (stringHeight + margins*2)), mouseX + stringWidth + margins, mouseY - margins - stringHeight - 25);
			
			Fonts.font8.setColor(Color.WHITE);
			Fonts.font8.drawMultiLine(batch, text, mouseX, mouseY - 25);
		}
	}
}
