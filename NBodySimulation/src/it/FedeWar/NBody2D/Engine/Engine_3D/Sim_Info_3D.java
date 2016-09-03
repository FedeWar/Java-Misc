package it.FedeWar.NBody2D.Engine.Engine_3D;

import java.awt.Dimension;

public class Sim_Info_3D
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
	public float G;
	
	/**
	 * Il passo temporale tra due frame.
	 * 
	 * @since 1.0
	 */
	public float deltaT;
	
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
