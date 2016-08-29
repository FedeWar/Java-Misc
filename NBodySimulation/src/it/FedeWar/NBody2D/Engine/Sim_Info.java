package it.FedeWar.NBody2D.Engine;

import java.awt.Dimension;

/**
 * La quantità delle informazioni cambia a seconda del tipo
 * di simulazione, questa classe fornisce una base per
 * implementare ogni tipo di simulazione.
 * Alcune proprietà sono già implementate, sono quelle
 * base che ogni simulazione dovrebbe usare.
 * 
 * @author FedeWar
 * @version 1.0
 * @since 1.0
 * @date 24/07/2016
 * */
public class Sim_Info
{
	/**
	 * La quantità di oggetti da creare e utilizzare.
	 * 
	 * @since 1.0
	 */
	public int obj_count;
	
	/**
	 * La costante di gravitazione con cui calcolare
	 * l'accelerazione.
	 * 
	 * @since 1.0
	 */
	public double G;
	
	/**
	 * Il passo temporale tra due frame.
	 * 
	 * @since 1.0
	 */
	public double deltaT;
	
	/**
	 * Le dimensioni della finestra su cui visualizzare
	 * la simulazione.
	 * 
	 * @since 1.0
	 */
	public Dimension winDim;
	
	public int standard_mass;
	public int mass_variation;
	
	public int standard_radius;
	public int radius_variation;
	
	public double[] spaceDim;
}
