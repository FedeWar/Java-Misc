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

import it.FedeWar.Fractnet.fractals.Frattale;

public class Sierpinski extends Frattale //Questo frattale non usa il piano complesso
{
	public void Draw()
	{
		Graphics2D g = (Graphics2D)Image.getGraphics();
		int lato = Image.getHeight() / 3;
		
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				if(x == 1 && y == 1)
				{
					g.setColor(Color.BLACK);
					g.drawRect(lato * x, lato * y, lato, lato);
					continue;
				}
				g.setColor(Color.RED);
				g.drawRect(lato * x, lato * y, lato, lato);
				Draw(lato / 3,lato * x, lato * y);
			}
		}
	}
	public void Draw(int lato, int X, int Y)
	{
		Graphics2D g = (Graphics2D)Image.getGraphics();
		if(lato==0)return;
		
		for(int x=0;x<3;x++)
		{
			for(int y=0;y<3;y++)
			{
				if(x==1 && y==1)
				{
					g.setColor(Color.BLACK);
					g.drawRect(X+lato*x, Y+lato*y, lato, lato);
					continue;
				}
				g.setColor(Color.RED);
				g.drawRect(X + lato * x, Y + lato * y, lato, lato);
				Draw(lato/3,X+lato*x,Y+lato*y);
			}
		}
	}
}
