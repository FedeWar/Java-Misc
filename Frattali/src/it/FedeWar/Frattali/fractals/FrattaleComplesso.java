package it.FedeWar.Frattali.fractals;

import it.FedeWar.Frattali.math.Complex;

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
