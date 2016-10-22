package it.FedeWar.NBody.Engine.Engine_2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.fedewar.fog.media.VideoWriter;

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
			
			// Il canvas viene disegnato sul componente
			((Graphics2D) g).drawImage(img, 0, 0, null);

			lblObjsCount.setText("Numero Oggetti: " + pnum_objs);
		}
	}
	
	private class setGui
	{
		private int count = 0;
		
		public JLabel setLbl(JLabel lbl)
		{
			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setBounds(10, count * (32 + 5) + 30, 188, 32);
			return lbl;
		};

		public JTextField setTxt(JTextField txt)
		{
			txt.setBounds(189, count * (32 + 5) + 30, 98, 33);
			++count;
			return txt;
		};
	}
	
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs = 0;	// Il numero di oggetti attivi
	
	private Sim_Info_2D info;	// Informazioni della simulazione
	
	/* Oggetti per il rendering */
	
	private Color background;	// Colore di sfondo
	private Color foreground;	// Colore con cui colorare gli oggetti
	private Vec2d camera;		// Posizione della camera per rendering
	private Graphics2D gc;		// Contesto grafico con cui disegnare
	private BufferedImage img;	// Immagine su cui disegnare
	
	/* Oggetti della GUI */
	
	private JLabel lblFPS;
	private JLabel lblObjsCount;
	private JTextField txtMassVariation;
	private JTextField txtObjCount;
	private JTextField txtRadiusVariation;
	private JTextField txtSpaceDims;
	private JTextField txtStandardMass;
	private JTextField txtStandardRadius;
	private JTextField txtExportTo;
	private Canvas pnlDraw;
	
	@Override
	public void createSettingsGUI(JPanel father)
	{
		super.createSettingsGUI(father);
		
		setGui f = new setGui();
		
		father.add(f.setLbl(new JLabel("Dimensioni Spazio")));
		txtSpaceDims = new JTextField("1280;720");
		father.add(f.setTxt(txtSpaceDims));
		
		father.add(f.setLbl(new JLabel("Numero Oggetti")));
		txtObjCount = new JTextField("256");
		father.add(f.setTxt(txtObjCount));
		
		father.add(f.setLbl(new JLabel("Massa Standard")));
		txtStandardMass = new JTextField("1");
		father.add(f.setTxt(txtStandardMass));

		father.add(f.setLbl(new JLabel("Variazione Massa")));
		txtMassVariation = new JTextField("0");
		father.add(f.setTxt(txtMassVariation));

		father.add(f.setLbl(new JLabel("Variazione Raggio")));
		txtRadiusVariation = new JTextField("0");
		father.add(f.setTxt(txtRadiusVariation));

		father.add(f.setLbl(new JLabel("Raggio Standard")));
		txtStandardRadius = new JTextField("3");
		father.add(f.setTxt(txtStandardRadius));
		
		father.add(f.setLbl(new JLabel("Esportazione verso:")));
		txtExportTo = new JTextField("");
		father.add(f.setTxt(txtExportTo));
	}
	
	@Override
	public void createSimulationGUI()
	{
		JFrame frame = new JFrame("Simulazione 2D");
		
		initEngine();
		background = Color.BLACK;
		foreground = Color.LIGHT_GRAY;
		camera = new Vec2d(0, 0);
		
		Dimension display = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(display.width, display.height);
		frame.setLayout(null);
		
		pnlDraw = new Canvas();
		int lato = Math.min(frame.getWidth(), frame.getHeight());
		pnlDraw.setBounds(0, 0, lato, lato);
		frame.add(pnlDraw);
		
		lblObjsCount = new JLabel("Numero Oggetti: 0");
		lblObjsCount.setHorizontalAlignment(JLabel.RIGHT);
		lblObjsCount.setBounds(0, 10, frame.getWidth() - 10, 20);
		lblObjsCount.setForeground(Color.LIGHT_GRAY);
		frame.add(lblObjsCount);
		
		lblFPS = new JLabel("Frame al Secondo: 0");
		lblFPS.setForeground(Color.LIGHT_GRAY);
		lblFPS.setHorizontalAlignment(JLabel.RIGHT);
		lblFPS.setBounds(0, 62, frame.getWidth() - 10, 20);
		frame.add(lblFPS);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		// Crea un framebuffer, le dimensioni devono
		// essere tali da coprire l'intera superficie
		img = new BufferedImage((int)info.spaceDim.x, (int)info.spaceDim.y, BufferedImage.TYPE_3BYTE_BGR);
		
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
		
		info.export = txtExportTo.getText();
		
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
		
		VideoWriter video = null;
		
		if(info.export.length() > 0)
		{
			try {
				video = new VideoWriter(info.export, new Dimension(img.getWidth(), img.getHeight()), 25);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		while (frame.isVisible())
		{
			// Benchmark
			if(System.currentTimeMillis() - benchStart >= 1000)
			{
				lblFPS.setText("Frame al Secondo: " + frames);
				frames = 0;
				benchStart = System.currentTimeMillis();
			}
			
			// Tutto il disegno avviene sul canvas
			Graphics2D g2 = (Graphics2D) img.getGraphics();

			// Disegna lo sfondo
			g2.setColor(background);
			g2.fillRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);

			// Disegna tutti gli oggetti, uno per uno
			g2.setColor(foreground);

			// Non si possono passare parametri a refresh,
			// quindi bisogna passare per un altro metodo.
			gc = g2;
			refresh();
			
			frame.repaint();
			
			// Solo se l'esportazione è attiva copia l'immagine nel video
			if(video != null)
			{
				video.copyBuffer(img);
				video.pushFrame();
			}
			
			// Max 60 frames al secondo
			try { Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {}
			// Nuovo frame
			++frames;
		}
		// La finestra è stata chiusa, libera le risorse
		frame.dispose();
		
		// Se l'esportazione è attiva chiude il video
		if(video != null)
			video.dispose();
	}
}
