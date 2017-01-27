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

import java.math.BigDecimal;

import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.fractals.Palette;
import it.FedeWar.Fractnet.math.*;

/* Disegna il classico frattale di Mandelbrot */
public class Mandelbrot extends ComplexFract
{
	private class StdPalette extends Palette
	{
		@Override
		public int getRed(int x) {
			return 0;
		}

		@Override
		public int getGreen(int x) {
			return 0;
		}

		@Override
		public int getBlue(int x) {
			return x;
		}
	}
	
	boolean useBigComplex = false;

	/* Calcola l'immagine */
	public void draw()
	{
		if(useBigComplex) {
			bigDraw();
			return;
		}
		
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int count;
		StdPalette palette = new StdPalette();
		Complex z = new Complex(0, 0);

		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				toFractalCoordinates(x, y, c);
				c.copy(z);

				for(count = 1; count < MAX && z.sqrdNorm() < 4.0; count++)
				{
					z.pow(2);
					z.add(c);
				}

				canvas.setRGB(x, y, palette.getRGB(count));
			}
		}
	}

	/*
	 * FIXME è troppo lento, ci vorrebbero giorni a renderizzare un frame,
	 * assolutamente non pronto all'uso e attivabile solo da debugger
	 */
	private void bigDraw()
	{
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int count;
		StdPalette palette = new StdPalette();
		BigComplex z = new BigComplex(0, 0);
		BigDecimal quattro = new BigDecimal(4.0);

		BigComplex c = new BigComplex(0.0, 0.0);
		
		computeBigCache();
		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				toFractalCoordinates(x, y, c);
				z.copyFrom(c);

				for(count = 1; count < MAX && z.sqrdNorm().compareTo(quattro) == -1; count++)
				{
					z.pow(2);
					z.add(c);
				}

				canvas.setRGB(x, y, palette.getRGB(count));
			}
		}
	}
	
	/* Mandelbrot non accetta argomenti aggiuntivi */
	public void setC(double r, double i) {}
}
