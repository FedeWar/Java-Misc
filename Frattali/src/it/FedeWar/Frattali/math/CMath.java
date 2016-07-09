package it.FedeWar.Frattali.math;

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
