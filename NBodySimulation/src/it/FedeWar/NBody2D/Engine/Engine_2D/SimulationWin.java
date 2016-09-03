package it.FedeWar.NBody2D.Engine.Engine_2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.javafx.geom.Vec2d;

import it.FedeWar.NBody2D.Engine.Engine_2D.*;

public class SimulationWin extends JFrame
{
	private static final long serialVersionUID = -8171820409697626939L;
	
	private Color[] colorPalette;
	private Simulation_2D sim;
	private JLabel lblObjsCount, lblFPS;
	private Vec2d camera;
	
	/* Pannello per disegnare gli oggetti */
	private class Canvas extends JPanel
	{
		private static final long serialVersionUID = 2647550373102965024L;

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// Disegna lo sfondo
			g2.setColor(colorPalette[0]);
			g2.fillRect(0, 0, getSize().width - 1, getSize().height - 1);

			// Disegna tutti gli oggetti, uno per uno
			g2.setColor(colorPalette[1]);
			sim.setGC(g2, camera);
			sim.refresh();
			//for(int i = 0; i < sim.pnum_objs; i++)
			//	sim.go[i].draw(g2, posX, posY);

			lblObjsCount.setText("Numero Oggetti: " + sim.pnum_objs);
		}
	}
	
	/* Costruttore, inizializzazione variabili */
	public SimulationWin(Simulation_2D sim)
	{
		super("Simulazione 2D");
		
		Sim_Info_2D info = sim.getInfo();
		this.sim = sim;
		sim.initEngine();
		colorPalette = new Color[]{ Color.BLACK, Color.LIGHT_GRAY };
		camera = new Vec2d(0, 0);
		
		setSize(info.winDim);
		setLayout(null);
		//addKeyListener(new Keyboard());
		//addPaintListener(new Painter());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas pnlDraw = new Canvas();
		int lato = Math.min(info.winDim.width, info.winDim.height);
		pnlDraw.setBounds(0, 0, lato, lato);
		this.add(pnlDraw);
		
		lblObjsCount = new JLabel("Numero Oggetti: 0");
		lblObjsCount.setHorizontalAlignment(JLabel.RIGHT);
		lblObjsCount.setBounds(0, 10, info.winDim.width - 10, 20);
		lblObjsCount.setForeground(Color.LIGHT_GRAY);
		this.add(lblObjsCount);
		
		/*Label lblTime = new Label(shell, SWT.NONE);
		lblTime.setAlignment(SWT.RIGHT);
		lblTime.setBounds(shell.getSize().x - 260 - 16, 36, 260, 20);
		lblTime.setText("Tempo Passato: 0s");
		if(!SI.opengl)
			lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		*/
		lblFPS = new JLabel("Frame al Secondo: 0");
		lblFPS.setForeground(Color.LIGHT_GRAY);
		lblFPS.setHorizontalAlignment(JLabel.RIGHT);
		lblFPS.setBounds(0, 62, info.winDim.width - 10, 20);
		this.add(lblFPS);
		
		setVisible(true);
	}
	
	public Dimension getPreferredSize()
	{
		return getSize();
	}

	/* Listener per i comandi da tastiera della shell */
	/*private class Keyboard implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			switch(arg0.keyCode)
			{
			case SWT.ARROW_UP:
				posY++;
				break;
			case SWT.ARROW_DOWN:
				posY--;
				break;
			case SWT.ARROW_LEFT:
				posX++;
				break;
			case SWT.ARROW_RIGHT:
				posX--;
				break;
			default:
			}
		}

		@Override public void keyReleased(KeyEvent arg0) {}
	}*/
	
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open()
	{
		long benchStart = 0;	// Estremi per il benchmarking
		int frames = 0;			// Frame calcolati in un secondo
		
		while (isVisible())
		{
			// Benchmark
			if(System.currentTimeMillis() - benchStart >= 1000)
			{
				lblFPS.setText("Frame al Secondo: " + frames);
				frames = 0;
				benchStart = System.currentTimeMillis();
			}
			
			// Nuovo frame
			frames++;
			repaint();
			
			try { Thread.sleep(1000 / 120);	// 120 = max fps
			} catch (InterruptedException e) {}
		}
	}
}
