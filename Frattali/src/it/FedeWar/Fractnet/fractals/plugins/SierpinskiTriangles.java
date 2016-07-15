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
import java.awt.Polygon;

import it.FedeWar.Fractnet.fractals.Fractal;

public class SierpinskiTriangles extends Fractal
{
	private static final double MAX_DETAIL = 200;
	
	private Color foreground = Color.GREEN;
	private Color background = Color.BLACK;
	
	public void setPalette(Color foreground, Color background)
	{
		this.foreground = foreground;
		this.background = background;
	}
	
	@Override
	public void Draw()
	{
		// Prepara il canvas
		Graphics2D g2 = (Graphics2D)Image.getGraphics();
		g2.setColor(background);
		g2.fillRect(0, 0, Width, Height);
		
		// Crea il primo triangolo
		Polygon firstTriangle = new Polygon();
		firstTriangle.addPoint(Width / 2, 0);
		firstTriangle.addPoint(0, Height);
		firstTriangle.addPoint(Width, Height);
		
		// Disegna i triangoli più piccoli ricorsivamente
		Draw(firstTriangle, 0);
		Draw(firstTriangle, 1);
		Draw(firstTriangle, 2);
	}
	
	// Disegna i triangoli ricorsivamente
	private void Draw(Polygon father, int index)
	{
		// Ottiene ogni volta l'oggetto grafico, la VM metterà il
		// risultato di getGraphics in cache e ottimizzerà questa linea
		Graphics2D g2 = (Graphics2D) Image.getGraphics();
		
		// Crea il triangolo e i buffer per i vertici
		Polygon triangle = new Polygon();
		triangle.npoints = 3;
		triangle.xpoints = new int[3];
		triangle.ypoints = new int[3];
		
		// Calcola i vertici in relazione alla posizione del triangolo
		switch(index)
		{
		case 0:
			triangle.xpoints[0] = father.xpoints[0];
			triangle.ypoints[0] = father.ypoints[0];
			triangle.xpoints[1] = (father.xpoints[0] + father.xpoints[1]) / 2;
			triangle.ypoints[1] = (father.ypoints[0] + father.ypoints[1]) / 2;
			triangle.xpoints[2] = (father.xpoints[0] + father.xpoints[2]) / 2;
			triangle.ypoints[2] = (father.ypoints[0] + father.ypoints[2]) / 2;
			break;
			
		case 1:
			triangle.xpoints[0] = (father.xpoints[0] + father.xpoints[1]) / 2;
			triangle.ypoints[0] = (father.ypoints[0] + father.ypoints[1]) / 2;
			triangle.xpoints[1] = father.xpoints[1];
			triangle.ypoints[1] = father.ypoints[1];
			triangle.xpoints[2] = (father.xpoints[1] + father.xpoints[2]) / 2;
			triangle.ypoints[2] = (father.ypoints[1] + father.ypoints[2]) / 2;
			break;
			
		case 2:
			triangle.xpoints[0] = (father.xpoints[0] + father.xpoints[2]) / 2;
			triangle.ypoints[0] = (father.ypoints[0] + father.ypoints[2]) / 2;
			triangle.xpoints[1] = (father.xpoints[1] + father.xpoints[2]) / 2;
			triangle.ypoints[1] = (father.ypoints[1] + father.ypoints[2]) / 2;
			triangle.xpoints[2] = father.xpoints[2];
			triangle.ypoints[2] = father.ypoints[2];
			break;
		}

		// Se questo triangolo è piccolo a sufficienza viene disegnato
		if(Math.pow(triangle.xpoints[0] - triangle.xpoints[1], 2) +
			Math.pow(triangle.ypoints[0] - triangle.ypoints[1], 2) < MAX_DETAIL)
		{
			g2.setColor(foreground);
			g2.fillPolygon(triangle);
		}
		// Se il triangolo è troppo grande disegna quelli più piccoli
		else
		{
			Draw(triangle, 0);
			Draw(triangle, 1);
			Draw(triangle, 2);
		}
	}
}
