package it.FedeWar.NBody2D.Engine;

public class Engine
{
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs = 0;	// Il numero di oggetti attivi
	
	/* Costruttore, inizializza le variabili e distribuisce gli oggetti */
	public Engine(Sim_Info si)
	{
		G_Obj.staticInit(this);
		go = new G_Obj[si.obj_count];
		pnum_objs = go.length;
		
		int defaultMass = si.standard_mass;
		int defaultRadius = si.standard_radius;
		int massVariation = si.mass_variation;
		int radiusVariation = si.radius_variation;
		int dimX = si.dim_x;
		int dimY = si.dim_y;
		
		for(int i = 0; i < go.length; i++)
		{
			go[i] = new G_Obj(
				defaultMass + (int)(Math.random() * massVariation * 2 - massVariation),
				defaultRadius + (int)(Math.random() * radiusVariation * 2 - radiusVariation),
				new Vector2f((float)Math.random() * dimX, (float)Math.random() * dimY));
		}
	}

	/* La funzione ricalcola tutti gli oggetti */
	public void refresh()
	{
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updateAcc();				// Ne ricalcola l'accelerazione
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updatePos();				// Ne ricalcola la velocità
	}
	
	/* TODO Le simulazioni già pronte vanno lette da un file */
	/*public void Sim_Terra_Luna(int zoom)//questa funziona simula il sistema terra-luna
	{									//in scala, con un fattore zoom, per l'ingrandimento
		float v = -0.184482f;
		float d = 238*zoom;
		go[0].Mass = 81;
        go[0].Radius = 4*zoom;
        G = (v*v*d)/go[0].Mass;
        go[0].Pos = new Vector2f(Win.canvas.getWidth()/2, Win.canvas.getHeight()/2);
        go[1].Mass = 1;
        go[1].Radius = zoom;
        go[1].Pos = new Vector2f(Win.canvas.getWidth()/2-d, Win.canvas.getHeight()/2);
        go[1].Vel = new Vector2f(0,v);
	}*/
}
