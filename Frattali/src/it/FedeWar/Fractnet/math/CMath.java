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

public class CMath 
{
	public static Complex Somma(Complex a1, Complex a2)
	{
		return new Complex(a1.r+a2.r, a1.i+a2.i);
	}
	public static Complex Sottrazione(Complex a1, Complex a2)
	{
		return new Complex(a1.r-a2.r, a1.i-a2.i);
	}
	public static Complex Prodotto(Complex a1, Complex a2)
	{
		return new Complex((a1.r*a2.r)-(a1.i*a2.i), (a1.i*a2.r)+(a1.r*a2.i));
	}
	public static Complex Divisione(Complex a1, Complex a2)
	{
		return new Complex(((a1.r*a2.r)+(a1.i*a2.i))/((a2.r*a2.r)+(a2.i*a2.i)),
				((a2.r*a1.i)-(a2.i*a1.r))/((a2.r*a2.r)+(a2.i*a2.i)));
	}
	public static double Modulo(Complex c)
	{
		return Math.sqrt(c.r*c.r + c.i*c.i);
	}
	public static Complex Potenza(Complex c, int n)
	{
		if(n == 2)
		{
			return new Complex((c.r*c.r)-(c.i*c.i), 2*c.r*c.i);
		}
		return null;
	}
}
