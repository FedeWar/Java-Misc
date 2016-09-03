package it.FedeWar.NBody.Engine.Engine_2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.geom.Vec2d;

import it.FedeWar.NBody.Engine.Simulation;

public class Simulation_2D extends Simulation
{
	public static final String name = "Simulazione 2D - No GPU";
	
	/* Pannello per disegnare gli oggetti */
	private class Canvas extends JPanel
	{
		private static final long serialVersionUID = 2647550373102965024L;

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// Disegna lo sfondo
			g2.setColor(background);
			g2.fillRect(0, 0, getSize().width - 1, getSize().height - 1);

			// Disegna tutti gli oggetti, uno per uno
			g2.setColor(foreground);
			
			// Non si possono passare parametri a refresh,
			// quindi bisogna passare per un altro metodo.
			gc = g2;
			refresh();

			lblObjsCount.setText("Numero Oggetti: " + pnum_objs);
		}
	}
	
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs = 0;	// Il numero di oggetti attivi
	private Color background;
	private Vec2d camera;		// Posizione della camera per rendering
	private Color foreground;
	private Graphics2D gc;		// Contesto grafico con cui disegnare
	private Sim_Info_2D info;	// Informazioni della simulazione
	private JLabel lblFPS;
	private JLabel lblObjsCount;
	private JTextField txtMassVariation;
	private JTextField txtObjCount;
	private JTextField txtRadiusVariation;
	private JTextField txtSpaceDims;
	private JTextField txtStandardMass;
	private JTextField txtStandardRadius;
	private JTextField txtWinDims;
	
	@Override
	public void createSettingsGUI(JPanel father)
	{
		super.createSettingsGUI(father);
		Dimension display = Toolkit.getDefaultToolkit().getScreenSize();

		JLabel lblWinDims = new JLabel("Dimensioni Finestra");
		lblWinDims.setHorizontalAlignment(JLabel.CENTER);
		lblWinDims.setBounds(10, 30, 188, 33);
		father.add(lblWinDims);
		
		txtWinDims = new JTextField();
		txtWinDims.setText("" + display.width + ";" + display.height);
		txtWinDims.setBounds(189, 30, 98, 33);
		father.add(txtWinDims);

		txtObjCount = new JTextField("256");
		txtObjCount.setBounds(204, 108, 83, 33);
		father.add(txtObjCount);

		JLabel lblObjCount = new JLabel("Numero Oggetti");
		lblObjCount.setHorizontalAlignment(JLabel.CENTER);
		lblObjCount.setBounds(10, 108, 188, 33);
		father.add(lblObjCount);

		txtMassVariation = new JTextField("0");
		txtMassVariation.setBounds(204, 186, 83, 33);
		father.add(txtMassVariation);

		JLabel lblMassVariation = new JLabel();
		lblMassVariation.setHorizontalAlignment(JLabel.CENTER);
		lblMassVariation.setBounds(10, 186, 188, 33);
		lblMassVariation.setText("Variazione Massa");
		father.add(lblMassVariation);

		txtRadiusVariation = new JTextField();
		txtRadiusVariation.setText("0");
		txtRadiusVariation.setBounds(204, 264, 83, 33);
		father.add(txtRadiusVariation);

		JLabel lblRadVariation = new JLabel();
		lblRadVariation.setHorizontalAlignment(JLabel.CENTER);
		lblRadVariation.setBounds(10, 264, 188, 33);
		lblRadVariation.setText("Variazione Raggio");
		father.add(lblRadVariation);

		txtStandardMass = new JTextField();
		txtStandardMass.setText("1");
		txtStandardMass.setBounds(204, 147, 83, 33);
		father.add(txtStandardMass);

		txtStandardRadius = new JTextField();
		txtStandardRadius.setText("3");
		txtStandardRadius.setBounds(204, 225, 83, 33);
		father.add(txtStandardRadius);
		
		JLabel lblStandardRadius = new JLabel();
		lblStandardRadius.setHorizontalAlignment(JLabel.CENTER);
		lblStandardRadius.setBounds(10, 225, 188, 33);
		lblStandardRadius.setText("Raggio Standard");
		father.add(lblStandardRadius);

		JLabel lblStandardMass = new JLabel("Massa Standard");
		lblStandardMass.setHorizontalAlignment(JLabel.CENTER);
		lblStandardMass.setBounds(10, 147, 188, 33);
		father.add(lblStandardMass);

		txtSpaceDims = new JTextField();
		txtSpaceDims.setBounds(189, 69, 98, 33);
		txtSpaceDims.setText("500;500");
		father.add(txtSpaceDims);

		JLabel lblDimentions = new JLabel("Dimensioni Spazio");
		lblDimentions.setHorizontalAlignment(JLabel.CENTER);
		lblDimentions.setBounds(10, 69, 188, 33);
		father.add(lblDimentions);
		
		father.repaint();
	}
	
	@Override
	public void createSimulationGUI()
	{
		JFrame frame = new JFrame("Simulazione 2D");
		
		initEngine();
		background = Color.BLACK;
		foreground = Color.LIGHT_GRAY;
		camera = new Vec2d(0, 0);
		
		frame.setSize(info.winDim);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas pnlDraw = new Canvas();
		int lato = Math.min(info.winDim.width, info.winDim.height);
		pnlDraw.setBounds(0, 0, lato, lato);
		frame.add(pnlDraw);
		
		lblObjsCount = new JLabel("Numero Oggetti: 0");
		lblObjsCount.setHorizontalAlignment(JLabel.RIGHT);
		lblObjsCount.setBounds(0, 10, info.winDim.width - 10, 20);
		lblObjsCount.setForeground(Color.LIGHT_GRAY);
		frame.add(lblObjsCount);
		
		lblFPS = new JLabel("Frame al Secondo: 0");
		lblFPS.setForeground(Color.LIGHT_GRAY);
		lblFPS.setHorizontalAlignment(JLabel.RIGHT);
		lblFPS.setBounds(0, 62, info.winDim.width - 10, 20);
		frame.add(lblFPS);
		
		frame.setVisible(true);
		
		open(frame);
	}
	
	public Sim_Info_2D getInfo() {
		return info;
	}
	
	/* Costruttore, inizializza le variabili e distribuisce gli oggetti */
	public void initEngine()
	{
		G_Obj.staticInit(this);
		go = new G_Obj[info.obj_count];
		pnum_objs = go.length;
		
		int defaultMass = info.standard_mass;
		int defaultRadius = info.standard_radius;
		int massVariation = info.mass_variation;
		int radiusVariation = info.radius_variation;
		double dimX = info.spaceDim.x;
		double dimY = info.spaceDim.y;
		
		for(int i = 0; i < go.length; i++)
		{
			go[i] = new G_Obj(
				defaultMass + (int)(Math.random() * massVariation * 2 - massVariation),
				defaultRadius + (int)(Math.random() * radiusVariation * 2 - radiusVariation),
				new Vec2d(Math.random() * dimX, Math.random() * dimY));
		}
	}

	/* Ricalcola la posizione degli oggetti e li ridisegna */
	public void refresh()
	{
		// Ricalcola l'accelerazione
		for(int i = 0; i < pnum_objs; i++)
			go[i].updateAcc();
		
		// Ricalcola la velocità, la posizione e disegna l'oggetto
		for(int i = 0; i < pnum_objs; i++)
			go[i].updatePos(gc, camera);
	}

	@Override
	protected void packInfo()
	{
		Sim_Info_2D info = new Sim_Info_2D();
	
		String wDim = txtWinDims.getText();
		int winDimW = Integer.parseInt(wDim.substring(0, wDim.indexOf(';')));
		int winDimH = Integer.parseInt(wDim.substring(wDim.indexOf(';') + 1, wDim.length()));
		
		info.winDim = new Dimension(winDimW, winDimH);
	
		info.obj_count = Integer.parseInt(txtObjCount.getText());
		info.standard_mass = Integer.parseInt(txtStandardMass.getText());
		info.standard_radius = Integer.parseInt(txtStandardRadius.getText());
		info.mass_variation = Integer.parseInt(txtMassVariation.getText());
		info.radius_variation = Integer.parseInt(txtRadiusVariation.getText());
	
		String sDim = txtSpaceDims.getText();
		int spcDimW = Integer.parseInt(sDim.substring(0, sDim.indexOf(';')));
		int spcDimH = Integer.parseInt(sDim.substring(sDim.indexOf(';') + 1, sDim.length()));
		
		info.spaceDim = new Vec2d(spcDimW, spcDimH);
	
		info.G = 0.001;
		info.deltaT = 1;
		
		this.info = info;
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
	
	private void open(JFrame frame)
	{
		long benchStart = 0;	// Per determinare quando è passato un secondo
		int frames = 0;			// Frame calcolati in un secondo
		
		while (frame.isVisible())
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
			frame.repaint();
			
			// Max 60 frames
			try { Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {}
		}
	}
}
