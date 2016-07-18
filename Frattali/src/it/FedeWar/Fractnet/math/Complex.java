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
package it.FedeWar.Fractnet.math;

public class Complex
{
	public double r;
	public double i;
	public double mod;
	
	public Complex(double real, double imag)
	{
		r = real;
		i = imag;
	}
	
	/* La norma al quadrato */
	public double sqrdNorm()
	{
		return r * r + i * i;
	}
	
	public double norm()
	{
		mod = Math.sqrt(r*r + i*i);
		return mod;
	}
	
	public Complex pow(int n)
	{
		if(n == 2)
		{
			return new Complex((r*r)-(i*i), 2*r*i);
		}
		return null;
	}
	
	public static Complex Parse(String str)
	{
		Complex C = new Complex(0, 0);
		int commaIndex = str.indexOf(';');
		C.r = Double.parseDouble(str.substring(0, commaIndex));
		C.i = Double.parseDouble(str.substring(commaIndex + 1));
		return C;
	}
}
