package it.FedeWar.NBody2D.Engine.Engine_2D;

import java.awt.Graphics2D;

import com.sun.javafx.geom.Vec2f;

public class G_Obj
{
	private static float G;			// Costante di gravitazione
	private static float t_gap;		// Passo temporale
	private static Simulation_2D E;	// Il motore a cui fare riferimento
	private static Vec2f dst;		// Evita di riallocarlo ogni volta
	
	private		float	radius;			// Il raggio del corpo
	private		int		mass;			// La massa del corpo
	private		Vec2f	position;		// La posizione
	private		Vec2f	velocity;		// La velocità
	private 	Vec2f	acceleration;	// L' accelerazione
	
	/* Inizializza i campi statici */
	public static void staticInit(Simulation_2D e)
	{
		G = (float)e.getInfo().G;
		t_gap = (float)e.getInfo().deltaT;
		dst = new Vec2f(0, 0);
		E = e;
	}
	
	/* Costruttore, inizializza i campi */
	public G_Obj(int mass, int radius, Vec2f pos)
	{
		this.mass	= mass;
		this.radius	= radius;
		this.position		= pos;
		this.velocity		= new Vec2f(0.0f, 0.0f);
		this.acceleration		= new Vec2f(0.0f, 0.0f);
	}
	
	/* Calcola la nuova posizione dalla velocità */
	public void updatePos(Graphics2D g2, Vec2f camera)
	{
		// Calcola la nuova posizione
		position.x += (velocity.x * t_gap + 0.5f * acceleration.x * t_gap * t_gap);
		position.y += (velocity.y * t_gap + 0.5f * acceleration.y * t_gap * t_gap);
		
		// Aggiorna la velocità
		velocity.x += (acceleration.x * t_gap);
		velocity.y += (acceleration.y * t_gap);
		
		// Disegna l'oggetto
		g2.drawOval(
			(int)(position.x - radius - camera.x),
			(int)(position.y - radius - camera.y),
			(int)(radius * 2),
			(int)(radius * 2));
	}
	
	public void updateAcc()
	{
		acceleration.x = 0;	// Azzera l'accelerazione
		acceleration.y = 0;
		
		// Itera su tutti gli oggetti
		for(int i = 0; i < E.pnum_objs; i++)
		{
			G_Obj O1 = E.go[i];						// Ottiene un riferimento all'altro oggetto
			if(O1 == this) continue;				// Non deve eseguire calcoli con se stesso
			
			dst.x = (float)(O1.position.x - position.x);		// Calcola la distanza tra i due oggetti
			dst.y = (float)(O1.position.y - position.y);
			
			if(norm(dst) <= radius + O1.radius)	// Se la distanza è minore della somma dei raggi
			{
				if(radius < O1.radius)				// Se questo oggetto è più piccolo
				{
					position.x = O1.position.x;				// Ne cambia la posizione con l'altro
					position.y = O1.position.y;
				}
				
				// Il nuovo raggio si calcola con il teorema di pitagora per mantere l'area costante
				radius = (int) Math.sqrt(radius * radius + E.go[i].radius * E.go[i].radius);
				
				// Per calcolare la nuova velocità fa conservare la quantità di moto
				velocity.x = ((mass * velocity.x) + (O1.mass * O1.velocity.x)) / (mass + O1.mass);
				velocity.y = ((mass * velocity.y) + (O1.mass * O1.velocity.y)) / (mass + O1.mass);
				
				mass += O1.mass;				// La nuova massa è la somma delle masse
				E.go[i] = E.go[E.pnum_objs - 1];// Cancella l'altro oggetto
				E.pnum_objs--;					// Decrementa il numero di oggetti attivi
			}
			else
			{
				float dstQuadra = dst.x * dst.x + dst.y * dst.y;// La distanza da O1 quadra
				float mod = (float) Math.sqrt(dstQuadra);		// La distanza da O1
				float acc = G * O1.mass / dstQuadra;			// L'accelerazione
				
				acceleration.x += acc * dst.x / mod;		// Calcola la nuova accelerazione
				acceleration.y += acc * dst.y / mod;
			}

		}
	}

	private float norm(Vec2f v)
	{
		return (float) Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	/* Disegna l'oggetto su un Contesto Grafico */
	/*public void draw(Graphics2D g2, int posX, int posY)
	{
		g2.drawOval(
			(int)(Pos.x - Radius - posX),
			(int)(Pos.y - Radius - posY),
			(int)(Radius * 2),
			(int)(Radius * 2));
	}*/
}
