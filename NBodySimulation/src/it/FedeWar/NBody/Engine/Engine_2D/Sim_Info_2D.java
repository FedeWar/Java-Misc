package it.FedeWar.NBody.Engine.Engine_2D;

import java.awt.Dimension;

import com.sun.javafx.geom.Vec2d;

/* Collezione di informazioni sulla simulazione 2D. */
public class Sim_Info_2D
{
	/* La quantità di oggetti da creare e utilizzare. */
	public int obj_count;
	
	/* La costante di gravitazione con cui calcolare l'accelerazione. */
	public double G;
	
	/* Il passo temporale tra due frame. */
	public double deltaT;
	
	/* Le dimensioni della finestra su cui visualizzare la simulazione. */
	public Dimension winDim;
	
	/* Informazioni sulla massa degli oggetti. */
	public int standard_mass;
	public int mass_variation;
	
	/* Informazioni sul raggio degli oggetti. */
	public int standard_radius;
	public int radius_variation;
	
	/* Dimensioni dello spazio in cui possono spawnare oggetti. */
	public Vec2d spaceDim;
}
