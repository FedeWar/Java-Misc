/* Copyright 2016 Federico Guerra aka FedeWar
	
	This file is part of Fractnet.

	Fractnet is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Fractnet is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Fractnet.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.FedeWar.Fractnet.fractals.plugins;

import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.fractals.Palette;
import it.FedeWar.Fractnet.math.*;

/* Disegna il classico frattale di Mandelbrot */
public class Mandelbrot extends ComplexFract
{
	private class StdPalette extends Palette
	{

		@Override
		public int getRed(int x)
		{
			return 0;
		}

		@Override
		public int getGreen(int x)
		{
			return 0;
		}

		@Override
		public int getBlue(int x)
		{
			return x;
		}
	}
	
	/* Calcola l'immagine */
	public void draw()
	{
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int count;
		StdPalette palette = new StdPalette();
		Complex z = new Complex(0, 0);
		
		for(int x = 0; x < width; x++)
    	{
    		for(int y = 0; y < height; y++)
    		{
    			// La proiezione dalle coordinate dello schermo a quelle
    			// del frattale avviene con la formula:
    			// z = rot * (p * clipSize / screenSize - clipPos)
    			c.r = rotation[0] * (x * clipSize[0] / width() - clipPos[0]);
    			c.i = rotation[1] * (y * clipSize[1] / height() - clipPos[1]);
    			z.r = z.i = 0;
    			
    			for(count = 0; count < MAX && z.norm() < 2.0f; count++)
    			{
    		        z = z.pow(2);
    		        z = CMath.sum(z, c);
    		    }
    			
    			canvas.setRGB(x, y, palette.getRGB(count));
    		}
    	}
	}
	
	/* Mandelbrot non accetta argomenti aggiuntivi */
	public void setC(double r, double i) {}
}
