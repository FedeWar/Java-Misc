package Frattali;

import java.awt.Color;

import it.fractals.FractalApp;
import it.fractals.math.CMath;
import it.fractals.math.Complex;

public class Julia extends FrattaleComplesso
{
	public Julia() {}
	
	public void Draw()
    {
    	for(int i = 0; i < Width; i++)
    	{
    		for(int n = 0; n < Height; n++)
    		{
    			temp = new Complex((i - trasl.r) / zoom, -(n - trasl.i) / zoom);
    			z = new Complex(0, 0);
    			for(count = 0; count < MAX && temp.Modulo() < 2; count++){
    		          z = temp.Potenza(2);
    		          z = CMath.Somma(z, c);
    		          temp = z;
    		    }
    			FractalApp.mainInstance.canvas.setRGB(i, n,
    					new Color(count*1.0f / (2 * MAX),
    						(float)Math.abs(Math.sin(count*1.0f / MAX * 2 * Math.PI)),//(float) Math.sqrt((double)count/MAX),
    						count*1.0f / MAX).getRGB());//(float)(1-Math.sqrt((double)count/MAX))
    		}
    	}
    }
	
	@Override
	public void animation(String path)
	{
		for(float i = 0; i < 2 * Math.PI; i += 0.005 * Math.PI)
		{
			c.r = 0.7885f * Math.cos(i);
			c.i =  0.7885f * Math.sin(i);
			Draw();
			FractalApp.mainInstance.canvas.saveTo(path + (int)(i * 1000) + ".bmp");
		}
	}
}
