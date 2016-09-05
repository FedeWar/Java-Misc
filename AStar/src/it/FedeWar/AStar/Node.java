package it.FedeWar.AStar;

import java.awt.Point;
import java.util.Comparator;

public class Node extends Point
{
	private static final long serialVersionUID = 2997445011812713029L;

	/* Comparatore per ordinare i vettori. */
	public static final Comparator<Node> comparator =
	(Node n1, Node n2) -> 
	{
		if(n1.f_cost > n2.f_cost)
			return 1;
		if(n2.f_cost > n1.f_cost)
			return -1;
		return 0;
	};
	
	/* Interfaccia per implementare funzioni euristiche. */
	@FunctionalInterface
    public static interface Heuristic {
        public double apply (Node current, Node goal);
    }
	private static Heuristic heuristic;	// Functor per l'euristica
	
	private Node	father;				// Padre del nodo
	private double	g_cost = 0;			// Costo del punto
	private double	f_cost = 0;			// Costo totale
	
	/* Crea un nodo in posizione (x,y),
	 * il cui padre è father. */
	public Node(int x, int y, Node father)
	{
		super(x, y);
		this.father = father;
	}
	
	/* Calcola il costo del punto. */
	public void compute_cost(double movement_cost, Node goal)
	{
		// Il costo del punto viene calcolato ricorsivamente
		// dal genitore e dal costo del movimento.
		g_cost = father.g_cost + movement_cost;
		
		// Il costo totale è dato dal costo del punto
		// sommato al valore dell'euristica.
		f_cost = g_cost + heuristic.apply(this, goal);
	}
	
	/* Setter per l'euristica */
	public static void setHeuristic(Heuristic h) {
		heuristic = h;
	}

	/* Getter per f_cost */
	public double get_f_cost() {
		return f_cost;
	}
	
	/* Getter per father */
	public Node getFather() {
		return father;
	}
}
