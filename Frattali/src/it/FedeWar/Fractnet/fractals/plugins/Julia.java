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

import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.math.CMath;
import it.FedeWar.Fractnet.math.Complex;

public class Julia extends ComplexFract
{
	public Julia() {}
	
	public void Draw()
    {
    	for(int i = 0; i < Width; i++)
    	{
    		for(int n = 0; n < Height; n++)
    		{
    			Complex temp = new Complex((i - trasl.r) / zoom, -(n - trasl.i) / zoom);
    			z = new Complex(0, 0);
    			for(count = 0; count < MAX && temp.norm() < 2; count++){
    		          z = temp.pow(2);
    		          z = CMath.sum(z, c);
    		          temp = z;
    		    }
    			Image.setRGB(i, n,
    				new Color(count*1.0f / (2 * MAX),
    					(float)Math.abs(Math.sin(count*1.0f / MAX * 2 * Math.PI)),//(float) Math.sqrt((double)count/MAX),
    					count*1.0f / MAX).getRGB());//(float)(1-Math.sqrt((double)count/MAX))
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
			Draw();
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
