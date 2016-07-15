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

import java.awt.Color;
import java.awt.Graphics2D;

import it.FedeWar.Fractnet.fractals.Fractal;

/* Frattale di Sierpinski, è un frattale semplice */
public class SierpinskiSquares extends Fractal
{
	private Graphics2D g;	// GC, come campo diminuisce l'uso dello stack
	
	@Override
	public void Draw()
	{
		// Prepara il canvas
		g = (Graphics2D)Image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Width, Height);
		g.setColor(Color.RED);
		
		int lato = Math.min(Width, Height) / 3;
		
		Draw(lato, 0, 0);
	}
	
	public void Draw(int lato, int X, int Y)
	{
		// Itera sugli 8 quadrati più piccoli
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				// Se non è il quadrato centrale
				if(x != 1 || y != 1)
				{
					// Se il quadrato ha raggiunto la dimensione minima
					if(lato - 3 <= 0)
						g.drawRect(X + lato * x, Y + lato * y, lato, lato);
					
					// Se è troppo grande chiama quelli più piccoli
					else
						Draw(lato / 3, X + lato * x, Y + lato * y);
				}
			}
		}
	}
}
