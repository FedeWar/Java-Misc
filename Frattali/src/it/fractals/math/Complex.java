package it.fractals.math;

public class Complex
{
	public double r;
	public double i;
	public double mod;
	
	public Complex(double real, double immag)//Costruttore
	{
		r = real;
		i = immag;
	}
	public double Modulo()
	{
		mod = Math.sqrt(r*r + i*i);
		return mod;
	}
	public Complex Potenza(int n)
	{
		if(n == 2)
		{
			return new Complex((r*r)-(i*i), 2*r*i);
		}
		return null;
	}
}
