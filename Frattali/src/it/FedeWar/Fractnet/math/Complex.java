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
	
	public Complex(double real, double imag) {
		r = real;
		i = imag;
	}
	
	/* La norma al quadrato */
	public double sqrdNorm() {
		return r * r + i * i;
	}
	
	/* Norma del vettore */
	public double norm() {
		return Math.sqrt(sqrdNorm());
	}
	
	public void copy(Complex to) {
		to.r = r;
		to.i = i;
	}
	
	public String toString() {
		return r + " + " + i + " * i";
	}
	
	public void add(Complex a) {
		r += a.r;
		i += a.i;
	}
	
	public void sub(Complex a) {
		r -= a.r;
		i -= a.i;
	}
	
	public void mul(Complex a) {
		double tr = r * a.r - i * a.i;
		double ti = r * a.i + i * a.r;
		r = tr;
		i = ti;
	}
	
	public void pow(int n) {
		for(int i = 1; i < n; i++)
			mul(this);
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
