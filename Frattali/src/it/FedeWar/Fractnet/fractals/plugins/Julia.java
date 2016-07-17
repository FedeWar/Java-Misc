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
import it.FedeWar.Fractnet.math.CMath;
import it.FedeWar.Fractnet.math.Complex;

public class Julia extends ComplexFract
{
	public Julia() {}
	
	public void draw()
    {
		int count;
		Complex z = new Complex(0, 0);
		
    	for(int x = 0; x < Width; x++)
    	{
    		for(int y = 0; y < Height; y++)
    		{
    			z.r = (double)(1.0 * x * clipWidth / Width - clipPos[0]);
    			z.i = (double)(1.0 * y * clipWidth / Height - clipPos[1]);
    			
    			for(count = 0; count < MAX && z.norm() < 2; count++)
    			{
    		          z = z.pow(2);
    		          z = CMath.sum(z, c);
    		    }
    			
    			Image.setRGB(x, y, (255 << 24) |
    				(int)(count*1.0f / (2 * MAX)) << 16 |
    				(int)((float)Math.abs(Math.sin(count*1.0f / MAX * 2 * Math.PI)) * 256) << 8);
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
