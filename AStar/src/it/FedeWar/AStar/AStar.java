package it.FedeWar.AStar;

import java.awt.Point;

import com.atlas.containers.BitMatrix;
import com.atlas.containers.PriorityQueue;
import com.atlas.containers.Vector;

public class AStar
{
	private static double[] direction = {
			1.5, 1, 1.5,
			1,    1,
			1.5, 1, 1.5
	};
	
	private static Point[] neighbours = {
			new Point(-1, -1), new Point(0, -1), new Point(1, -1),
			new Point(-1, 0),				  new Point(1, 0),
			new Point(-1, 1), new Point(0, 1), new Point(1, 1)
	};
	
	private Vector<Node>	closed_set;		// I nodi già controllati
	private byte[][]		heightMap;		// L'altezza della mappa in ogni punto
	private BitMatrix		obstaclesMap;	// I punti su cui non è possibile camminare
	private PriorityQueue<Node>	open_set;	// I nodi da controllare, ordinati per priorità
	private Vector<Node>	path;			// Il percorso, generato da recostruct_path
	private Node			start, end;		// Il punto di inizio e di fine del percorso

	public AStar(int w, int h) {
		heightMap = new byte[w][h];
		obstaclesMap = new BitMatrix(w, h);
	}

	public Vector<Node> getClosed_set() {
		return closed_set;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public byte[][] getHeightMap() {
		return heightMap;
	}
	
	public BitMatrix getObstaclesMap() {
		return obstaclesMap;
	}

	public PriorityQueue<Node> getOpen_set() {
		return open_set;
	}
	
	public Vector<Node> getPath() {
		return path;
	}

	public Point getStart() {
		return start;
	}

	/* Prepara l'algoritmo per essere avviato */
	public void init()
	{
		open_set = new PriorityQueue<Node>(Node.comparator);
		open_set.push_back(start);
		closed_set = new Vector<Node>();
	}

	public void recostruct_path()
	{
		path = new Vector<Node>(10);
		path.push_back(end);
		Node P = end;
		while(P.getFather() != null)
		{
			P = P.getFather();
			path.push_back(P);
		}
	}

	public void setEnd(int x, int y) {
		end = new Node(x, y, null);
	}

	public void setHeight(int x, int y, byte val) {
		heightMap[x][y] = val;
	}

	public void setObstacle(int x, int y) {
		obstaclesMap.set(x, y, true);
	}

	public void setStart(int x, int y) {
		start = new Node(x, y, null);
	}

	/* Esegue un singolo passo dell'algoritmo */
	public boolean step()
	{
		// Prende il prossimo nodo
		Node current = open_set.pop_front();

		// Ha raggiunto la fine
		if(current.equals(end))
		{
			end = current;
			return false;
		}

		// Evaluato, quindi va nel closed set
		closed_set.push_back(current);
		
		for(int i = 0; i < neighbours.length; i++)
		{
			// Le coordinate del nodo vicino, no heap overhead
			int x = current.x + neighbours[i].x;
			int y = current.y + neighbours[i].y;
			
			if(is_node_bad(x, y))
				continue;

			// Crea il nodo e lo mette nella lista
			Node neighbour = new Node(x, y, current);
			open_set.push_back(neighbour);

			// Calcola il costo del movimento
			neighbour.compute_cost(direction[i] + get_gradient(current, neighbour), end);
		}
		
		return open_set.size() > 0;
	}
	
	/* Ottiene la pendenza del terreno */
	private double get_gradient(Node parent, Node child)
	{
		int hgt_parent = heightMap[parent.x][parent.y];
		int hgt_child = heightMap[child.x][child.y];
		return (hgt_child - hgt_parent) / 128;
	}

	/* Controlla che le condinate siano valide */
	private boolean in_bounds(int x, int y) {
		return	x >= 0 && x < heightMap.length &&
				y >= 0 && y < heightMap[0].length;
	}

	/* Cerca un punto in un vettore */
	private boolean isInVector(int ax, int ay, Vector<Node> A)
	{
		for(int i = 0; i < A.size(); i++)
			if(A.get(i).x == ax && A.get(i).y == ay)
				return true;
		return false;
	}
	
	/* Controlla se il nodo deve essere elaborato. */
	private boolean is_node_bad(int x, int y) {
		return !in_bounds(x, y) ||
				obstaclesMap.get(x, y) ||
				isInVector(x, y, open_set) ||
				isInVector(x, y, closed_set);
	}
}
