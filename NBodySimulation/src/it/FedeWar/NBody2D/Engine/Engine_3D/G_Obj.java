package it.FedeWar.NBody2D.Engine.Engine_3D;

import com.sun.javafx.geom.Vec3f;

public class G_Obj
{
	private static float G;				// Costante di gravitazione
	private static float t_gap;			// Passo temporale
	private static Simulation_3D E;		// Il motore a cui fare riferimento
	private static Vec3f dst;			// Evita di riallocarlo ogni volta
	private	static float[] positions;	// Tutte le posizioni degli oggetti
	
	private	int		mass;			// La massa del corpo
	private	int		pos_id;			// Indice della posizione nell'array
	private	Vec3f	velocity;		// La velocità
	private Vec3f	acceleration;	// L' accelerazione
	
	/* Inizializza i campi statici */
	public static void staticInit(Simulation_3D e, float[] pos_arr)
	{
		G			= e.getInfo().G;
		t_gap		= e.getInfo().deltaT;
		dst			= new Vec3f(0.0f, 0.0f, 0.0f);
		E			= e;
		positions	= pos_arr;
	}
	
	/* Costruttore, inizializza i campi */
	public G_Obj(int mass, int radius, int pos)
	{
		this.mass		= mass;
		this.pos_id		= pos;
		velocity		= new Vec3f(0.0f, 0.0f, 0.0f);
		acceleration	= new Vec3f(0.0f, 0.0f, 0.0f);
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
		acceleration.set(0.0f, 0.0f, 0.0f);
		
		// Itera su tutti gli oggetti
		for(int i = 0; i < E.pnum_objs; i++)
		{
			// Ottiene un riferimento all'altro oggetto
			G_Obj O1 = E.go[i];
			// Non deve eseguire calcoli con se stesso
			if(O1 == this) continue;
			
			// Calcola la distanza tra i due oggetti
			dst.x = positions[O1.pos_id	   ] - positions[pos_id    ];
			dst.y = positions[O1.pos_id + 1] - positions[pos_id + 1];
			dst.z = positions[O1.pos_id + 2] - positions[pos_id + 2];

			// Calcola la distanza da O1 e la direzione
			float dstQuadra = dst.x * dst.x + dst.y * dst.y + dst.z * dst.z;
			float mod = (float) Math.sqrt(dstQuadra + 0.1f);
			float acc = G * O1.mass / dstQuadra;

			// Calcola la nuova accelerazione
			acceleration.x += acc * dst.x / mod;
			acceleration.y += acc * dst.y / mod;
			acceleration.z += acc * dst.z / mod;
		}
	}
}