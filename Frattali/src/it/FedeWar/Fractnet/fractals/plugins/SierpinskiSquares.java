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

/* Tappeto di Sierpinski, è un frattale semplice */
public class SierpinskiSquares extends Fractal
{
	private Graphics2D g;	// GC, come campo diminuisce l'uso dello stack
	
	/* Disegna il frattale */
	@Override
	public void draw()
	{
		// Dimensioni del frattale
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		
		// Prepara il canvas
		g = (Graphics2D)canvas.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.RED);
		
		// Disegna ricorsivamente
		int lato = Math.min(width, height) / 3;
		Draw(lato, 0, 0);
	}
	
	/* Disegna ricorsivamente 8 quadrati */
	public void Draw(int lato, int X, int Y)
	{
		// Se il quadrato ha raggiunto la dimensione minima
		if(lato - 3 <= 0)
		{
			g.drawRect(X + lato * 0, Y + lato * 0, lato, lato);
			g.drawRect(X + lato * 0, Y + lato * 1, lato, lato);
			g.drawRect(X + lato * 0, Y + lato * 2, lato, lato);
			g.drawRect(X + lato * 1, Y + lato * 0, lato, lato);
			g.drawRect(X + lato * 1, Y + lato * 2, lato, lato);
			g.drawRect(X + lato * 2, Y + lato * 0, lato, lato);
			g.drawRect(X + lato * 2, Y + lato * 1, lato, lato);
			g.drawRect(X + lato * 2, Y + lato * 2, lato, lato);
		}
		// Se è troppo grande chiama quelli più piccoli
		else
		{
			Draw(lato / 3, X + lato * 0, Y + lato * 0);
			Draw(lato / 3, X + lato * 0, Y + lato * 1);
			Draw(lato / 3, X + lato * 0, Y + lato * 2);
			Draw(lato / 3, X + lato * 1, Y + lato * 0);
			Draw(lato / 3, X + lato * 1, Y + lato * 2);
			Draw(lato / 3, X + lato * 2, Y + lato * 0);
			Draw(lato / 3, X + lato * 2, Y + lato * 1);
			Draw(lato / 3, X + lato * 2, Y + lato * 2);
		}
	}
}
