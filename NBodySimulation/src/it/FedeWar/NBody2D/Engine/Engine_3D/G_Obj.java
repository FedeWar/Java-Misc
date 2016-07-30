package it.FedeWar.NBody2D.Engine.Engine_3D;

import com.sun.javafx.geom.Vec3f;

public class G_Obj
{
	private static float G;				// Costante di gravitazione
	private static float t_gap;			// Passo temporale
	private static Simulation_3D E;		// Il motore a cui fare riferimento
	private static Vec3f dst;			// Evita di riallocarlo ogni volta
	private	static float[] positions;	// Tutte le posizioni degli oggetti
	
	private		float	radius; 		// Il raggio del corpo
	private		int		mass;			// La massa del corpo
	private		int		pos_id;			// Indice della posizione nell'array
	private		Vec3f	velocity;		// La velocità
	private 	Vec3f	acceleration;	// L' accelerazione
	
	/* Inizializza i campi statici */
	public static void staticInit(Simulation_3D e, float[] positions)
	{
		G = 0.001f;
		t_gap = 1.0f;
		dst = new Vec3f(0, 0, 0);
		E = e;
	}
	
	/* Costruttore, inizializza i campi */
	public G_Obj(int mass, int radius, int pos)
	{
		this.mass = mass;
		this.radius = radius;
		this.pos_id = pos;
		velocity = new Vec3f(0, 0, 0);
		acceleration = new Vec3f(0, 0, 0);
	}
	
	/* Calcola la nuova posizione dalla velocità */
	public void updatePos()
	{
		// Calcola la nuova posizione
		positions[pos_id] += (velocity.x * t_gap + 0.5f * acceleration.x * t_gap * t_gap);
		positions[pos_id + 1] += (velocity.y * t_gap + 0.5f * acceleration.y * t_gap * t_gap);
		positions[pos_id + 2] += (velocity.z * t_gap + 0.5f * acceleration.z * t_gap * t_gap);
		// Aggiorna la velocità
		velocity.x += (acceleration.x * t_gap);
		velocity.y += (acceleration.y * t_gap);
		velocity.z += (acceleration.z * t_gap);
	}
	
	public void updateAcc()
	{
		acceleration.set(0, 0, 0);
		
		// Itera su tutti gli oggetti
		for(int i = 0; i < E.pnum_objs; i++)
		{
			// Ottiene un riferimento all'altro oggetto
			G_Obj O1 = E.go[i];
			// Non deve eseguire calcoli con se stesso
			if(O1 == this) continue;
			
			// Calcola la distanza tra i due oggetti
			dst.set(positions[O1.pos_id] - positions[pos_id],
					positions[O1.pos_id + 1] - positions[pos_id + 1],
					positions[O1.pos_id + 2] - positions[pos_id + 2]);
			
			// Se la distanza è minore della somma dei raggi
			if(dst.length() <= radius + O1.radius)
			{
				// Se questo oggetto è più piccolo di O1
				if(radius < O1.radius)
				{
					positions[pos_id] = positions[O1.pos_id];
					positions[pos_id + 1] = positions[O1.pos_id + 1];
					positions[pos_id + 2] = positions[O1.pos_id + 2];
				}
				
				// Il nuovo raggio si calcola con il teorema di pitagora per mantere l'area costante
				radius = (int) Math.sqrt(radius * radius + E.go[i].radius * E.go[i].radius);
				
				// Per calcolare la nuova velocità fa conservare la quantità di moto
				velocity.x = ((mass * velocity.x) + (O1.mass * O1.velocity.x)) / (mass + O1.mass);
				velocity.y = ((mass * velocity.y) + (O1.mass * O1.velocity.y)) / (mass + O1.mass);
				velocity.z = ((mass * velocity.z) + (O1.mass * O1.velocity.z)) / (mass + O1.mass);
				
				// La nuova massa è la somma delle masse
				mass += O1.mass;
				
				// Sposta l'ultimo oggetto nel posto dell'oggetto cancellato
				E.go[i] = E.go[E.pnum_objs - 1];
				E.pnum_objs--;
			}
			else
			{
				// La distanza da O1 quadra
				float dstQuadra = dst.x * dst.x + dst.y * dst.y + dst.z * dst.z;
				float mod = (float) Math.sqrt(dstQuadra);		// La distanza da O1
				float acc = G * O1.mass / dstQuadra;			// L'accelerazione
				
				// Calcola la nuova accelerazione
				acceleration.x += acc * dst.x / mod;
				acceleration.y += acc * dst.y / mod;
				acceleration.z += acc * dst.z / mod;
			}

		}
	}
}