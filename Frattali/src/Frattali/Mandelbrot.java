package Frattali;

import java.awt.Color;

import it.fractals.FractalApp;
import it.fractals.math.CMath;
import it.fractals.math.Complex;

/* Disegna il classico frattale di Mandelbrot */
public class Mandelbrot extends FrattaleComplesso
{
	public Mandelbrot()
	{
		
	}
	
	public void Draw()
	{
		for(int i = 0; i < Width; i++)
    	{
    		for(int n = 0; n < Height; n++)
    		{
    			c.r = (double)((i - trasl.r) / zoom);
    			c.i = (double)((n - trasl.i) / zoom);
    			z = new Complex(0,0);
    			for(count = 0; count < MAX && z.Modulo() < 2.0f; count++)
    			{
    		        z = z.Potenza(2);
    		        z = CMath.Somma(z, c);
    		    }
    			Color c = new Color( 0, 0, count);
    			FractalApp.mainInstance.canvas.setRGB(i,n, c.getRGB());
    		}
    	}
	}
	
	/* Mandelbrot non accetta argomenti aggiuntivi */
	public void setC(double r, double i) {}
}
