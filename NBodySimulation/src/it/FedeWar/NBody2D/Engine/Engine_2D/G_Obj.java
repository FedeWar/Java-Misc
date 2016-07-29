package it.FedeWar.NBody2D.Engine.Engine_2D;

import java.awt.Graphics2D;

public class G_Obj
{
	private static float G;			// Costante di gravitazione
	private static float t_gap;		// Passo temporale
	private static Simulation_2D E;	// Il motore a cui fare riferimento
	private static Vector2f dst;	// Evita di riallocarlo ogni volta
	
	private		float		Radius; // Il raggio del corpo
	private		int			Mass;	// La massa del corpo
	private		Vector2f	Pos;	// La posizione
	private		Vector2f	Vel;	// La velocità
	private 	Vector2f	Acc;	// L' accelerazione
	
	/* Inizializza i campi statici */
	public static void staticInit(Simulation_2D e)
	{
		G = 0.001f;
		t_gap = 1.0f;
		dst = new Vector2f(0, 0);
		E = e;
	}
	
	/* Costruttore, inizializza i campi */
	public G_Obj(int mass, int radius, Vector2f pos)
	{
		Mass	= mass;
		Radius	= radius;
		Pos		= pos;
		Vel		= new Vector2f(0.0f, 0.0f);
		Acc		= new Vector2f(0.0f, 0.0f);
	}
	
	/* Calcola la nuova posizione dalla velocità */
	public void updatePos()
	{
		// Calcola la nuova posizione
		Pos.x += (Vel.x * t_gap + 0.5f * Acc.x * t_gap * t_gap);
		Pos.y += (Vel.y * t_gap + 0.5f * Acc.y * t_gap * t_gap);
		// Aggiorna la velocità
		Vel.x += (Acc.x * t_gap);
		Vel.y += (Acc.y * t_gap);
	}
	
	public void updateAcc()
	{
		Acc.x = 0;	// Azzera l'accelerazione
		Acc.y = 0;
		
		// Itera su tutti gli oggetti
		for(int i = 0; i < E.pnum_objs; i++)
		{
			G_Obj O1 = E.go[i];						// Ottiene un riferimento all'altro oggetto
			if(O1 == this) continue;				// Non deve eseguire calcoli con se stesso
			
			dst.x = (float)(O1.Pos.x - Pos.x);		// Calcola la distanza tra i due oggetti
			dst.y = (float)(O1.Pos.y - Pos.y);
			
			if(dst.norm() <= Radius + O1.Radius)	// Se la distanza è minore della somma dei raggi
			{
				if(Radius < O1.Radius)				// Se questo oggetto è più piccolo
				{
					Pos.x = O1.Pos.x;				// Ne cambia la posizione con l'altro
					Pos.y = O1.Pos.y;
				}
				
				// Il nuovo raggio si calcola con il teorema di pitagora per mantere l'area costante
				Radius = (int) Math.sqrt(Radius * Radius + E.go[i].Radius * E.go[i].Radius);
				
				// Per calcolare la nuova velocità fa canservare la quantità di moto
				Vel.x = ((Mass * Vel.x) + (O1.Mass * O1.Vel.x)) / (Mass + O1.Mass);
				Vel.y = ((Mass * Vel.y) + (O1.Mass * O1.Vel.y)) / (Mass + O1.Mass);
				
				Mass += O1.Mass;				// La nuova massa è la somma delle masse
				E.go[i] = E.go[E.pnum_objs - 1];// Cancella l'altro oggetto
				E.pnum_objs--;					// Decrementa il numero di oggetti attivi
			}
			else
			{
				float dstQuadra = dst.x * dst.x + dst.y * dst.y;// La distanza da O1 quadra
				float mod = (float) Math.sqrt(dstQuadra);		// La distanza da O1
				float acc = G * O1.Mass / dstQuadra;			// L'accelerazione
				
				Acc.x += acc * dst.x / mod;		// Calcola la nuova accelerazione
				Acc.y += acc * dst.y / mod;
			}

		}
	}

	/* Disegna l'oggetto su un Contesto Grafico */
	public void draw(Graphics2D g2, int posX, int posY)
	{
		g2.drawOval(
			(int)(Pos.x - Radius - posX),
			(int)(Pos.y - Radius - posY),
			(int)(Radius * 2),
			(int)(Radius * 2));
	}
}
