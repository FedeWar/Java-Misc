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

package it.FedeWar.Fractnet.fractals;

import java.awt.image.BufferedImage;

import it.FedeWar.Fractnet.math.Complex;

/* Classe base per frattali in due dimensioni */
public abstract class ComplexFract extends Fractal
{
	protected final int MAX = 255;

	protected double zoom = 0;
	protected Complex trasl;
	protected Complex c;
	
	/* Costruttore, deve essere vuoto */
	public ComplexFract() {}
	
	/* Inizializzazione oggetto, fa le veci del costruttore */
	@Override
	public void init(int width, int height, BufferedImage targetImage)
	{
		super.init(width, height, targetImage);
		c = new Complex(0,0);
		trasl = new Complex(Width / 2, Height / 2);
		zoom = Height / 2;
	}
	
	/* Setter per zoom */
	public void setZoom(double newZoom)
	{
		zoom = newZoom * Height / 2.0;
	}
	
	/* Getter per zoom */
	public double getZoom()
	{
		return zoom * 2.0 / Height;
	}
	
	public void setC(double r, double i)
	{
		c.r = r;
		c.i = i;
	}
	
	/* Setter per c */
	public void setC(Complex C)
	{
		c = C;
	}
}
