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

import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.fractals.Palette;
import it.FedeWar.Fractnet.math.*;

public class Julia extends ComplexFract
{
	private class StdPalette extends Palette
	{
		@Override
		public int getRed(int x)
		{
			return (int)(x * 1.0f / (2 * MAX));
		}
		
		@Override
		public int getGreen(int x)
		{
			return (int)((float)Math.abs(Math.sin(x * 1.0f / MAX * 2 * Math.PI)) * MAX);
		}
		
		@Override
		public int getBlue(int x)
		{
			return 0;
		}
	}
	
	public void draw()
    {
		StdPalette palette = new StdPalette();
		int count;
		Complex z = new Complex(0, 0);
		
    	for(int x = 0; x < width(); x++)
    	{
    		for(int y = 0; y < height(); y++)
    		{
    			// La proiezione dalle coordinate dello schermo a quelle
    			// del frattale avviene con la formula:
    			// z = rot * (p * clipSize / screenSize - clipPos)
    			z.r = rotation[0] * (x * clipSize[0] / width() - clipPos[0]);
    			z.i = rotation[1] * (y * clipSize[1] / height() - clipPos[1]);
    			
    			for(count = 0; count < MAX && z.sqrdNorm() < 4; count++)
    			{
    		          z = z.pow(2);
    		          z = CMath.sum(z, c);
    		    }
    			
    			canvas.setRGB(x, y, palette.getRGB(count));
    		}
    	}
    }
	
	@Override
	public void animation(String path)
	{
		for(float i = 0; i < 2 * Math.PI; i += 0.005 * Math.PI)
		{
			c.r = 0.7885f * Math.cos(i);
			c.i =  0.7885f * Math.sin(i);
			draw();
			//Image.saveTo(path + (int)(i * 1000) + ".bmp");
		}
	}
	
	/*public void saveTo(String path)
	{
		try {
			File out = new File(path);
			ImageIO.write(this, "bmp", out);
		} catch (IOException e) { e.printStackTrace(); }
	}*/
}
