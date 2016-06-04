package it.TS.Math;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

//Rendere questa classe e Wave imparentate

/* Un onda più complessa, generata da più onde */
public class ComplexWave
{
	public ArrayList<Wave>	Waves;		//La lista delle onde da sommare
	public Color			color;		//Il colore dell'onda
	public boolean			isShown;	//Se deve essere disegnata
	public Dimension		Pos;		//Lo sfasamento sugli assi
	
	public ComplexWave()
	{
	}
}
