package it.FedeWar.TrigonometricSeries.Math;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

//Rendere questa classe e Wave imparentate

/* Un onda pi� complessa, generata da pi� onde */
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
