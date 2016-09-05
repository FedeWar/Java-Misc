package it.FedeWar.AStar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.atlas.containers.BitMask;
import com.atlas.containers.BitMatrix;
import com.atlas.containers.Vector;

/**
 * Interfaccia per rappresentare graficamente gli oggetti AStar.
 * 
 * @version 1.0
 * @since 1.0
 * @author FedeWar
 * 
 * Informazioni importanti sulla nomenclatura:
 * <p>
 * La "griglia" è il sistema di riferimento dei tile, non è un oggetto in
 * memoria, è solo una rappresentazione matematica della tecnica usata
 * per disegnare i tile sull'immagine.
 * </p><p>
 * Con "tile" si indica l'elemento fondamentale e indivisible della griglia,
 * è rappresentato come un rettangolo di dimensioni (tile[0], tile[1]).
 * Tutti i tile del Canvas devono avere dimensioni uguali, se l'array tile
 * venisse modificato tutti i tile ne verrebbero influenzati.
 * In un Canvas ci sono <i>(Canvas.width / tile[0]) * (Canvas.height / tile[1])</i/>
 * tile.
 * I tile non sono oggetti in memoria quindi non possono essere creati singolarmente,
 * copiati o eliminati, l'unico modo per manipolare la loro unica proprietà (il colore)
 * è chiamare il metodo drawTile o drawPoint.
 * </p><p>
 * Si indica con "punto sullo schermo" un punto le cui coordinate appartengono
 * al sistema di riferimento dello schermo.
 * p ∈ [0, Screen.width) x [0, Screen.height)
 * </p><p>
 * Si indica con "punto sul Canvas" un punto le cui coordinate appartengono
 * al sisteme di riferimento del Canvas.
 * p ∈ [0, Canvas.width) x [0, Canavas.height)
 * </p><p>
 * Si indica con "punto sulla griglia" un punto appartente al sistema di riferimento
 * della griglia su cui sono allineati i tile.
 * p ∈ [0, Canvas.width / tile[0]) x [0, Canvas.height / tile[1])
 * </p>
 */
public class Canvas extends JPanel
{
	private static final long serialVersionUID = 8948448069045440350L;
	
	/* Indici per le proprietà in commands */
	
	public static final int START = 1;
	public static final int END = 2;
	public static final int RUN = 3;
	public static final int SAVE = 4;
	private BitMask commands = new BitMask();
	
	/* Imposta un comando da eseguire */
	public void setCmd(int cmd)
	{
		// Attiva la proprietà voluta
		commands.set(cmd, true);
		// Disattiva tutte le altre
		for(int i = 0; i < 4; i++)
			if(i != cmd)
				commands.set(i, false);
	}
	
	/* Listener per i click sul Canvas */
	private class ClickListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			// Ottiene il punto sul Canvas del mouse,
			// se p è nullo non fa nulla.
			Point mouse = getMousePosition();
			if(mouse == null)
				return;
			
			// Posiziona il punto di inizio
			if(commands.get(START))
			{
				commands.set(START, false);
				astar.setStart(
						CtoGX(mouse.x),
						CtoGY(mouse.y));
			}
			// Posiziona il punto di arrivo
			else if(commands.get(END))
			{
				commands.set(END, false);
				astar.setEnd(
						CtoGX(mouse.x),
						CtoGY(mouse.y));
			}
			// Colora il punto premuto
			else
			{
				// Il tasto sinistro colora l'altezza
				if(SwingUtilities.isLeftMouseButton(e))
				{
					astar.setHeight(
							CtoGX(mouse.x),
							CtoGY(mouse.y),
							(byte) (Application.terrainHeight & 255));
				}
				// Il tasto destro colora gli ostacoli
				else if(SwingUtilities.isRightMouseButton(e))
				{
					astar.setObstacle(
							CtoGX(mouse.x),
							CtoGY(mouse.y));
				}
			}
			repaint();
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	/* Listener per disegnare sul Canvas */
	private class DrawListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			// Ottiene la posizione del mouse, se il mouse
			// è fuori dal canvas non fa nulla
			Point mouse = getMousePosition();
			if(mouse == null)
				return;
			
			// Sceglie il colore in base all'altezza del terreno
			// e al pulsante premuto, il pulsante destro disegna ostacoli
			// mentre il sinistro disegna altezza.
			// Notifica anche i cambiamenti all'oggetto AStar.
			if(SwingUtilities.isLeftMouseButton(e))
			{
				astar.setHeight(
						CtoGX(mouse.x),
						CtoGY(mouse.y),
						(byte)(Application.terrainHeight & 255));
			}
			else if(SwingUtilities.isRightMouseButton(e))
			{
				astar.setObstacle(
						CtoGX(mouse.x),
						CtoGY(mouse.y));
			}
			// Altri tasti non sono presi in considerazione
			else return;
			
			repaint();
		}

		public void mouseMoved(MouseEvent arg0) {}
	}
	
	private BufferedImage img;	// L'immagine su cui disegnare
	private int[] tile;			// La dimensione della tile
	public AStar astar;

	/* Creates a new canvas able to store tile_count tiles,
	 * with dimensions tile_in_px. */
	public Canvas(Dimension tile_count, Dimension tile_in_px)
	{
		addMouseListener(new ClickListener());
		addMouseMotionListener(new DrawListener());
		
		tile = new int[] { tile_in_px.width, tile_in_px.height };
		
		// Canvas.size = tile_count * tile_in_px
		setSize(tile_count.width * tile[0], tile_count.height * tile[1]);
		
		// Crea un'immagine delle dimensioni del canvas
		img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		// Sfondo nero
		fill(Color.BLACK);
		
		// Crea l'oggetto AStar
		astar = new AStar(tile_count.width, tile_count.height);
	}
	
	public void run()
	{
		// Aspetta la chiamata al run
		while(!commands.get(RUN))
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}

		// Setta l'euristica
		Node.setHeuristic(
				(Node current, Node goal) -> {
//					return 0;
//					return Math.pow(current.x - goal.x, 2) + Math.pow(current.y - goal.y, 2);
					return Math.sqrt(Math.pow(current.x - goal.x, 2) + Math.pow(current.y - goal.y, 2));
//					return 5 * (Math.abs(current.x - goal.x) + Math.abs(current.y - goal.y));
				});

		// Prepara l'algoritmo
		astar.init();
		
		// Esegue l'algoritmo passo passo
		int i = 0;
		while(astar.step())
		{
			drawAStar();
			repaint();

			if(commands.get(SAVE))	//FIXME
				saveTo("/" + (i++));

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		
		astar.recostruct_path();
		drawAStar();
		repaint();
	}

	// FIXME
	/*public void changeTile(Dimension new_tile)
	{
		BufferedImage tmp = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

		Dimension tiles = getTileCount();
		for(int x = 0; x < tiles.width; x++)
		{
			for(int y = 0; y < tiles.height; y++)
			{
				int color = getTileColor(x, y);
				
				int X = x * tile[0];
				int Y = y * tile[1];
				
				// Calcola le coordinate finali
				int endX = tile[0] + X - (X % tile[0]);
				int endY = tile[1] + Y - (Y % tile[1]);

				// Itera sul tile disegnado tutti i punti
				for(int xx = X - (X % tile[0]); xx < endX; xx++)
					for(int yy = Y - (Y % tile[1]); yy < endY; yy++)
						img.setRGB(xx, yy, color);
			}
		}
			
		this.tile = new int[] { new_tile.width, new_tile.height };
		img = tmp;
	}*/
	
	public int CtoGX(int x)
	{
		int X = x * img.getWidth() / getWidth();
		X -= X % tile[0];
		X /= tile[0];
		return X;
	}

	public int CtoGY(int y)
	{
		int Y = y * img.getHeight() / getHeight();
		Y -= Y % tile[1];
		Y /= tile[1];
		return Y;
	}

	/* Disegna la tile che contiene il punto (x,Y) appartienente a Grid */
	public void drawTile(int X, int Y, int color)
	{
		// Calcola le coordinate iniziali
		int startX = GtoCX(X);
		int startY = GtoCY(Y);
		
		// Calcola le coordinate finali
		int endX = startX + tile[0];
		int endY = startY + tile[1];
	
		// Itera sul tile disegnado tutti i punti
		for(int x = startX; x < endX; x++)
		{
			for(int y = startY; y < endY; y++)
			{
				img.setRGB(x, y, color);
			}
		}
	}

	/* Riempie l'immagine di un colore uniforme */
	public void fill(Color color)
	{
		for(int x = 0; x < getWidth(); x++)
			for(int y = 0; y < getHeight(); y++)
				img.setRGB(x, y, color.getRGB());
	}

	/* Restituisce il colore del tile in posizione (x, y). */
	public int getTileColor(int x, int y) {
		return img.getRGB(GtoCX(x),  GtoCY(y));
	}

	public int GtoCX(int x) {
		return x * tile[0];
	}

	public int GtoCY(int y) {
		return y * tile[1];
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(!commands.get(RUN))
		{
			drawtHeight(astar.getHeightMap());
			drawObstacles(astar.getObstaclesMap(), Color.RED.getRGB());
			
			if(astar.getStart() != null)
				drawTile(astar.getStart().x, astar.getStart().y, Color.GREEN.getRGB());
			
			if(astar.getEnd() != null)
				drawTile(astar.getEnd().x, astar.getEnd().y, Color.BLUE.getRGB());
		}
		
		g2.drawImage(img, 0, 0, getSize().width, getSize().height, null);
	}

	public void saveTo(String path)
	{
		try {
			if(!ImageIO.write( img, "PNG", new File(path + ".png")))
				System.err.println("ERROR");
		} catch (IOException e) {}
	}
	
	private void compatible_dimensions(int width, int height) {
		if(width * tile[0] != img.getWidth() || 
				height * tile[1] != img.getHeight())
			throw new ArrayIndexOutOfBoundsException();
	}
	
	private void drawAStar()
	{
		drawtHeight(astar.getHeightMap());
		drawObstacles(astar.getObstaclesMap(), Color.RED.getRGB());
		
		if(astar.getClosed_set() != null)
			drawVector(astar.getClosed_set(), Color.CYAN.getRGB());
		
		if(astar.getOpen_set() != null)
			drawVector(astar.getOpen_set(), Color.YELLOW.getRGB());
		
		if(astar.getPath() != null)
			drawVector(astar.getPath(), Color.ORANGE.getRGB());
		
		if(astar.getStart() != null)
			drawTile(astar.getStart().x, astar.getStart().y, Color.GREEN.getRGB());
		
		if(astar.getEnd() != null)
			drawTile(astar.getEnd().x, astar.getEnd().y, Color.BLUE.getRGB());
	}

	/**
	 * Colora i tile del Canvas secondo il contenuto di map,
	 * il colore è in formato ARGB.
	 * 
	 * @param map Ogni elemento è il colore di un tile.
	 * @since 1.0
	 */
	private void drawtHeight(byte[][] map)
	{
		compatible_dimensions(map.length, map[0].length);

		// Colora i tile uno per uno
		for(int x = 0; x < map.length; x++)
			for(int y = 0; y < map[0].length; y++)
			{
				int height = map[x][y];
				drawTile(x, y, (255 << 24) | (height << 16) | (height << 8) | height);
			}
	}
	
	/* Disegna la mappa */
	private void drawObstacles(BitMatrix A, int color)
	{
		compatible_dimensions(A.width(), A.height());
	
		for(int x = 0; x < A.width(); x++)
		{
			for(int y = 0; y < A.height(); y++)
			{
				if(A.get(x, y))
					drawTile(x, y, color);
			}
		}
	}

	/* Colora il contenuto di un vettore di Node */
	private void drawVector(Vector<Node> A, int color) {
		for(int i = 0; i < A.size(); i++)
			drawTile(A.get(i).x, A.get(i).y, color);
	}
}
