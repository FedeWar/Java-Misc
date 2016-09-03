package it.FedeWar.NBody.Engine.Engine_3D;

import java.awt.Dimension;

import com.sun.javafx.geom.Vec3d;

public class Sim_Info_3D
{
	/* La quantità di oggetti da creare e utilizzare. */
	public int obj_count;
	
	/* La costante di gravitazione con cui calcolare
	 * l'accelerazione.*/
	public float G;
	
	/* Il passo temporale tra due frame.*/
	public float deltaT;
	
	/* Le dimensioni della finestra */
	public Dimension winDim;
	
	/* Informazioni sulla massa degli oggetti. */
	public int standard_mass;
	public int mass_variation;
	
	/* Dimensioni spazio in cui far spawnare oggetti. */
	public Vec3d spaceDim;
}
