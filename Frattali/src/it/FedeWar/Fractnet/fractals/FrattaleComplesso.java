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

import it.FedeWar.Fractnet.math.Complex;

/* Classe base per frattali in due dimensioni */
public abstract class FrattaleComplesso extends Frattale
{
	protected int MAX = 255;
	protected int count = 0;

	public double zoom = 0;
	public Complex trasl;
	protected Complex c;
	public Complex z, temp;
	
	public FrattaleComplesso()
	{
		
	}
	
	@Override
	public void Init(int width, int height)
	{
		super.Init(width, height);
		z = new Complex(0,0);
		c = new Complex(0,0);
		temp = new Complex(0,0);
		trasl = new Complex(Width / 2, Height / 2);
		zoom = Height / 4;
	}
	
	@Override
	public void setC(double r, double i)
	{
		if(c == null)
			c = new Complex(r, i);
		else
		{
			c.r = r;
			c.i = i;
		}
	}
}
