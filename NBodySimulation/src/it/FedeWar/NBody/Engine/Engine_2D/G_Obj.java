package it.FedeWar.NBody.Engine.Engine_2D;

import java.awt.Graphics2D;

import com.sun.javafx.geom.Vec2d;

public class G_Obj
{
	private static double G;		// Costante di gravitazione
	private static double t_gap;	// Passo temporale
	private static Simulation_2D E;	// Il motore a cui fare riferimento
	private static Vec2d dst;		// Evita di riallocarlo ogni volta
	
	private		double	radius;			// Il raggio del corpo
	private		int		mass;			// La massa del corpo
	private		Vec2d	position;		// La posizione
	private		Vec2d	velocity;		// La velocità
	private 	Vec2d	acceleration;	// L'accelerazione
	
	/* Inizializza i campi statici */
	public static void staticInit(Simulation_2D e)
	{
		G = e.getInfo().G;
		t_gap = e.getInfo().deltaT;
		dst = new Vec2d(0, 0);
		E = e;
	}
	
	/* Costruttore, inizializza i campi */
	public G_Obj(int mass, int radius, Vec2d pos)
	{
		this.mass			= mass;
		this.radius			= radius;
		this.position		= pos;
		this.velocity		= new Vec2d(0.0, 0.0);
		this.acceleration	= new Vec2d(0.0, 0.0);
	}
	
	/* Calcola la nuova posizione dalla velocità */
	public void updatePos(Graphics2D g2, Vec2d camera)
	{
		// Calcola la nuova posizione
		position.x += (velocity.x * t_gap + 0.5 * acceleration.x * t_gap * t_gap);
		position.y += (velocity.y * t_gap + 0.5 * acceleration.y * t_gap * t_gap);
		
		// Aggiorna la velocità
		velocity.x += (acceleration.x * t_gap);
		velocity.y += (acceleration.y * t_gap);
		
		// Disegna l'oggetto
		g2.drawOval(
			(int)(position.x - radius - camera.x),
			(int)(position.y - radius - camera.y),
			(int)(radius * 2.0),
			(int)(radius * 2.0));
	}
	
	public void updateAcc()
	{
		// Azzera l'accelerazione
		acceleration.x = 0;
		acceleration.y = 0;
		
		// Itera su tutti gli oggetti
		for(int i = 0; i < E.pnum_objs; i++)
		{
			// Ottiene un riferimento all'altro oggetto
			G_Obj O1 = E.go[i];
			
			// Non deve eseguire calcoli con se stesso
			if(O1 == this) continue;
			
			// Calcola la distanza tra i due oggetti
			dst.x = O1.position.x - position.x;
			dst.y = O1.position.y - position.y;
			
			// Se la distanza è minore della somma dei raggi
			if(norm(dst) <= radius + O1.radius)
			{
				// Se questo oggetto è più piccolo
				if(radius < O1.radius)
				{
					// Ne cambia la posizione con l'altro
					position.x = O1.position.x;
					position.y = O1.position.y;
				}
				
				// Il nuovo raggio si calcola con il teorema di pitagora per mantere l'area costante
				radius = (int) Math.sqrt(radius * radius + E.go[i].radius * E.go[i].radius);
				
				// Per calcolare la nuova velocità fa conservare la quantità di moto
				velocity.x = ((mass * velocity.x) + (O1.mass * O1.velocity.x)) / (mass + O1.mass);
				velocity.y = ((mass * velocity.y) + (O1.mass * O1.velocity.y)) / (mass + O1.mass);
				
				// La nuova massa è la somma delle masse
				mass += O1.mass;
				
				// Sposta l'oggetto cancellato
				E.go[i] = E.go[--E.pnum_objs];
			}
			else
			{
				// Calcola la distanza dei corpi e la direzione
				double dstQuadra = dst.x * dst.x + dst.y * dst.y;
				double mod = Math.sqrt(dstQuadra);
				double acc = G * O1.mass / dstQuadra;
				
				// Calcola l'accelerazione dell'oggetto
				acceleration.x += acc * dst.x / mod;
				acceleration.y += acc * dst.y / mod;
			}

		}
	}

	/* Calcola la lunghezza del vettore v. */
	private double norm(Vec2d v) {
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
}
