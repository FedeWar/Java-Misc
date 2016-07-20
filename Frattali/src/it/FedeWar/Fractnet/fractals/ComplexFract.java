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

	protected double clipWidth = 2.0;
	protected double[] clipPos;
	protected Complex c;
	
	/* Costruttore, deve essere vuoto */
	public ComplexFract() {}
	
	/* Inizializzazione oggetto, fa le veci del costruttore */
	@Override
	public void init(BufferedImage targetImage)
	{
		super.init(targetImage);
		c = new Complex(0, 0);
		clipPos = new double[] {1, 1};
	}
	
	public void setClipPos(double[] newTrasl)
	{
		clipPos[0] = newTrasl[0];
		clipPos[1] = newTrasl[1];
	}
	
	public double[] getClipPos()
	{
		return new double[] { clipPos[0], clipPos[1]};
	}
	
	/* Setter per zoom */
	public void setClipWidth(double newZoom)
	{
		// Compensa lo spostamento per mantenere centrata l'immagine
		clipPos[0] -= (clipWidth - newZoom) / 2;
		clipPos[1] -= (clipWidth - newZoom) / 2;
		clipWidth = newZoom;
	}
	
	/* Getter per zoom */
	public double getClipWidth()
	{
		return clipWidth;
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

	public void addTrasl(double[] newTrasl)
	{
		clipPos[0] += newTrasl[0];
		clipPos[1] += newTrasl[1];
	}
	
	public void subTrasl(double[] newTrasl)
	{
		clipPos[0] -= newTrasl[0];
		clipPos[1] -= newTrasl[1];
	}
}
