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

	/* La distanza tra i bordi del rettangolo da visualizzare,
	 * se viene visualizzata una porzione di frattale da -1 a 1
	 * la clipSize è 2 (1-(-1)).
	 * Questa è la rappresentazione in forma di array della matrice:
	 * +-------+-------+
	 * | XSize |   0   |
	 * +-------+-------+
	 * |   0   | YSize |
	 * +-------+-------+ */
	protected double[] clipSize = { 2.0, 2.0 };
	protected double[] clipPos = { 1.0, 1.0 };
	
	/* Rappresentazione in forma di array della matrice di rotazione:
	 * +------+-------+
	 * |cos(a)|-sin(a)|
	 * +------+-------+
	 * |sin(a)|cos(a) |
	 * +------+-------+
	 */
	protected double[] rotation = {
			Math.cos(0), -Math.sin(0),
			Math.sin(0), Math.cos(0) };
	
	protected Complex c;
	
	/* Costruttore, deve essere vuoto */
	public ComplexFract() {}
	
	/* Inizializzazione oggetto, fa le veci del costruttore */
	@Override
	public void init(BufferedImage targetImage)
	{
		super.init(targetImage);
		c = new Complex(0, 0);
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
	
	/* Setter per lo zoom */
	public void setClipWidth(double newZoom)
	{
		// Compensa lo spostamento per mantenere centrata l'immagine
		clipPos[0] -= (clipSize[0] - newZoom) / 2;
		clipPos[1] -= (clipSize[1] - newZoom) / 2;
		clipSize[0] = clipSize[1] = newZoom;
	}
	
	/* Getter per lo zoom */
	public double getClipWidth()
	{
		return clipSize[0];
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
	
	public void rotate(double angle)
	{
		rotation[0] = Math.cos(angle);
		rotation[2] = Math.sin(angle);
		rotation[1] = -rotation[2];
		rotation[3] = rotation[0];
	}
}
