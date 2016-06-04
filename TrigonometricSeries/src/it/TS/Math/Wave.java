package it.TS.Math;

import java.awt.Color;
import java.awt.Dimension;

public class Wave
{
	public double	Hz;		//La frequenza dell'onda
	public double	Amp;	//L'altezza dell'onda
	public Color	color;	//Il colore con cui deve essere disegnata
	public boolean	isShown;//Se deve essere disegnata indipendentemente
	public Dimension Pos;	//Lo sfasamento lungo gli assi
	public byte		Type;	//Il tipo di onda da generare		-NOT IMPLEMENTED YET-
	
	public Wave()
	{
		Hz = 1;
		Amp = 1;
		color = Color.black;
		isShown = false;
		Pos = new Dimension(0, 0);
		Type = 0;
	}
	
	/* Restituisce il valore della funzione nel punto X */
	public float getValueIn(float X)
	{
		/* Calcola sin((X + xOffset) * frew) * amp + yOffset e ne restituisce il valore*/
		return (float) (Math.sin((X + Pos.width) * Hz) * Amp) + Pos.height;
	}
	
	public String toString()
	{
		return new String("sin((x + " + Pos.width + ") * " + Hz + ") * " + Amp + " + " + Pos.height);
	}
}
