package it.FedeWar.TrigonometricSeries.Math;

import java.awt.Color;
import java.awt.Dimension;

public class Wave
{
	public double		Hz;			//La frequenza dell'onda
	public double		Amp;		//L'altezza dell'onda
	public Color		color;		//Il colore con cui deve essere disegnata
	public boolean		isShown;	//Se deve essere disegnata indipendentemente
	public Dimension	Pos;		//Lo sfasamento lungo gli assi
	public byte			type;		//Il tipo di onda da generare		-NOT IMPLEMENTED YET-
	
	public Wave()
	{
		Hz = 1;
		Amp = 1;
		color = Color.black;
		isShown = false;
		Pos = new Dimension(0, 0);
		type = 0;
	}
	
	/* Restituisce il valore della funzione nel punto X */
	public float getValueIn(float X)
	{
		/* Calcola sin((X + xOffset) * freq) * amp + yOffset e ne restituisce il valore*/
		return (float) (Math.sin((X + Pos.width) * Hz) * Amp) + Pos.height;
	}

	/* Restituisce una rappresentazione testuale dell'onda */
	public String toString()
	{
		String function = "";;
		
		if(type == 0)
			function = "sin(";
		else if(type == 1)
			function = "cos(";
		
		if(Amp == 0.0 || Hz == 0.0)
			return "0";
		
		if(Amp != 1.0)
			function = Amp + " * " + function;
		if(Hz != 1.0)
			function += Hz;
		function += "x";
		if(Pos.width != 0.0)
			function += " + " + Pos.width;
		
		function += ")";
		if(Pos.height != 0.0)
			function += " + " + Pos.height;
		
		return function;
	}
}
