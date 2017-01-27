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
import java.math.BigDecimal;

import it.FedeWar.Fractnet.math.BigComplex;
import it.FedeWar.Fractnet.math.Complex;

/* Classe base per frattali in due dimensioni */
public abstract class ComplexFract extends Fractal
{
	protected int MAX = 255;

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
	
	/* Costruttore, non deve avere parametri*/
	public ComplexFract() {}
	
	/* Inizializzazione oggetto, fa le veci del costruttore */
	@Override
	public void init(BufferedImage targetImage)
	{
		super.init(targetImage);
		c = new Complex(0, 0);
	}
	
	/* Converte le coordinate dello schermo in coordinate complesse */
	public void toFractalCoordinates(int x, int y, Complex out)
	{
		// La proiezione dalle coordinate dello schermo a quelle
		// del frattale avviene con la formula:
		// z = rot * (p * clipSize / screenSize - clipPos)
		double posX = x * clipSize[0] / width() - clipPos[0];
		double posY = y * clipSize[1] / height() - clipPos[1];
		out.r = rotation[0] * posX + rotation[1] * posY;
		out.i = rotation[2] * posX + rotation[3] * posY;
	}
	
	BigDecimal c0, c1, r0, r1, r2, r3;
	
	public void computeBigCache()
	{
		c0 = new BigDecimal(clipPos[0]);
		c1 = new BigDecimal(clipPos[1]);
		r0 = new BigDecimal(rotation[0]);
		r1 = new BigDecimal(rotation[1]);
		r2 = new BigDecimal(rotation[2]);
		r3 = new BigDecimal(rotation[3]);
	}
	
	/* Converte le coordinate dello schermo in coordinate complesse */
	public void toFractalCoordinates(int x, int y, BigComplex out)
	{
		// La proiezione dalle coordinate dello schermo a quelle
		// del frattale avviene con la formula:
		// z = rot * (p * clipSize / screenSize - clipPos)
		BigDecimal posX = new BigDecimal(x * clipSize[0] / width()).subtract(c0);
		BigDecimal posY = new BigDecimal(y * clipSize[1] / height()).subtract(c1);
		
		out.r = posX.multiply(r0).add(posY.multiply(r1));
		out.i = posX.multiply(r2).add(posY.multiply(r3));
	}
	
	public void setClipPos(double[] newTrasl)
	{
		clipPos[0] = newTrasl[0];
		clipPos[1] = newTrasl[1];
	}
	
	/* Getter per la posizione del clipping,
	 * usato principalmente per debug */
	public double[] getClipPos()
	{
		double[] trasl = new double[2];
		trasl[0] = clipPos[0];
		trasl[1] = clipPos[1];
		return trasl;
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
