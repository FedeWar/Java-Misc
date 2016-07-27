package it.FedeWar.TrigonometricSeries.Utils;

import it.FedeWar.TrigonometricSeries.Math.Wave;

/* Esegue il parsing della stringa function */
public class Parser
{
	private int counter;
	private String function;
	
	public Parser(String str)
	{
		function = str;
		counter = 0;
	}
	
	/* Legge il prossimo operatore */
	public int nextOperator()
	{
		char c = function.charAt(counter++);
		
		if(c == '+')
			return 1;
		else if(c == '*')
			return 2;
		else if(c == '(' || c == ')')
			return 3;
		else if(c == 'x')
			return 4;
		else
		{
			counter--;
			return -1;
		}
	}
	
	/* Legge il prossimo numero */
	public double parseDouble()
	{
		String str = "";
		
		while(counter < function.length())
		{
			char c = function.charAt(counter);
			if(c >= '0' && c <= '9' || c == '-' || c == '+')
				str += c;
			else break;
			counter++;
		}
		if(str == "")
			return Double.NaN;
		
		return Double.valueOf(str);
	}

	/* Determina il tipo di funzione */
	public byte parseFunction()
	{
		String f = "";
		
		for(; counter < function.length(); counter++)
		{
			char c = function.charAt(counter);
			if(!(c >= 'a' && c <= 'z'))
				break;
			f += c;
		}
		
		if(f.compareTo("sin") == 0)
			return 0;
		if(f.compareTo("cos") == 0)
			return 1;
		return -1;
	}

	/* Legge un intera onda */
	public Wave parseWave()
	{
		Wave W = new Wave();

		W.Amp = parseDouble();
		if(Double.isNaN(W.Amp))
			W.Amp = 1.0;
		nextOperator();	// *

		W.type = parseFunction();
		nextOperator(); // (

		W.Hz = parseDouble();
		if(Double.isNaN(W.Hz))
			W.Hz = 1.0;

		if(nextOperator() == 2 && nextOperator() == 4)	// *x
		{
			if(nextOperator() == 1)	// +
			{
				W.Pos.width = (int) parseDouble();
				if(Double.isNaN(W.Pos.width))
					W.Pos.width = 0;
			}
		}

		return W;
	}
}
