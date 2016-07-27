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
	/* Graphics Context, è un campo poiché bisogna ridurre
	 * l'uso dello stack, che viene usato dal metodo ricorsivo */
	private Graphics2D g;
	
	/* Disegna il frattale */
	@Override
	public void draw()
	{	
		// Prepara il canvas
		g = (Graphics2D)canvas.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width(), height());
		g.setColor(Color.RED);
		
		// Disegna ricorsivamente
		Draw(Math.min(width(), height()) / 3, 0, 0);
	}
	
	/* Disegna ricorsivamente 8 quadrati, si sarebbe
	 * potuto scrivere con due for annidati, ma essendo
	 * il numero di iterazioni costante ho optato per
	 * unroll. */
	public void Draw(int lato, int X, int Y)
	{
		// Se il quadrato ha raggiunto la dimensione minima
		if(lato - 3 <= 0)
		{
			g.drawRect(X				, Y					, lato, lato);
			g.drawRect(X				, Y + lato			, lato, lato);
			g.drawRect(X				, Y + lato + lato	, lato, lato);
			g.drawRect(X + lato			, Y					, lato, lato);
			g.drawRect(X + lato			, Y + lato + lato	, lato, lato);
			g.drawRect(X + lato + lato	, Y					, lato, lato);
			g.drawRect(X + lato + lato	, Y + lato			, lato, lato);
			g.drawRect(X + lato + lato	, Y + lato + lato	, lato, lato);
		}
		// Se è troppo grande chiama quelli più piccoli
		else
		{
			Draw(lato / 3, X				, Y					);
			Draw(lato / 3, X				, Y + lato			);
			Draw(lato / 3, X				, Y + lato + lato	);
			Draw(lato / 3, X + lato			, Y					);
			Draw(lato / 3, X + lato			, Y + lato + lato	);
			Draw(lato / 3, X + lato + lato	, Y					);
			Draw(lato / 3, X + lato + lato	, Y + lato			);
			Draw(lato / 3, X + lato + lato	, Y + lato + lato	);
		}
	}
}
