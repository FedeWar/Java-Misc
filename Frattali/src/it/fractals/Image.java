package it.fractals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image extends BufferedImage
{

	public Image(int w, int h, int c)
	{
		super(w, h, c);
	}
	
	public Image(Dimension size, int type)
	{
		super(size.width, size.height, type);
	}

	public void fill(Color c)
	{
		int color = c.getRGB();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                setRGB(x, y, color);
            }
        }
	}
	
	public void drawRect(Color c, int x1, int y1, int w, int h)
    {
        int color = c.getRGB();
        for (int x = x1; x < x1 + w; x++)
        {
            for (int y = y1; y < y1 + h; y++)
            {
            	if(x>=getWidth())x=getWidth()-1;
            	if(y>=getHeight())y=getHeight()-1;
                setRGB(x, y, color);
            }
        }
    }
	
	/*La funzione drawRect qui sotto rispetto a quella sopra di segna solamente il bordo del rettangolo
	* utilizzando "int s" come misura in pixels per lo spessore del bordo stesso*/
	public void drawRect(Color c, int x1, int y1, int w, int h, int s)
	{
		int color = c.getRGB();
		for(int x=x1; x<x1+w; x++)
		{
			for(int y=y1; y<y1+h; y++)
			{
				if(x>x1+s && y>y1+s && x<x1+w-s && y<y1+h-s)
					continue;
				setRGB(x, y, color);
			}
		}
	}
	
	public void drawCircle(Color c, int x1, int y1, int r, boolean filled)
	{
		int color = c.getRGB();
		for(int x = 0; x<getWidth(); x++)
		{
			for(int y = 0; y<getHeight(); y++)
			{
				if(dst(x,y,x1,y1) == r)
				{
					setRGB(x, y, color);
				}
				else if(dst(x,y,x1,y1) < r && filled)
				{
					setRGB(x, y, color);
				}
			}
		}
	}
	protected static float dst(int x, int y, int x1, int y1)
	{
		return (float) Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
	}
	
	public void saveTo(String path)
	{
		try {
			File out = new File(path);
			ImageIO.write(this, "bmp", out);
		} catch (IOException e) { e.printStackTrace(); }
	}
}
