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
import it.FedeWar.Fractnet.math.*;

/* Disegna il classico frattale di Mandelbrot */
public class Mandelbrot extends ComplexFract
{
	/* Costruttore, non deve prendere parametri */
	public Mandelbrot() {}
	
	/* Calcola l'immagine */
	public void draw()
	{
		int count;
		Complex z = new Complex(0, 0);
		
		for(int x = 0; x < Width; x++)
    	{
    		for(int y = 0; y < Height; y++)
    		{
    			c.r = (double)(x - trasl[0]) / zoom;
    			c.i = (double)(y - trasl[1]) / zoom;
    			z.r = z.i = 0;
    			
    			for(count = 0; count < MAX && z.norm() < 2.0f; count++)
    			{
    		        z = z.pow(2);
    		        z = CMath.sum(z, c);
    		    }
    			
    			Image.setRGB(x, y, (255 << 24) | count);
    		}
    	}
	}
	
	/* Mandelbrot non accetta argomenti aggiuntivi */
	public void setC(double r, double i) {}
}
