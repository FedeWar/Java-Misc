package it.FedeWar.NBody2D.Engine.Engine_2D;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.geom.Vec2d;

import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_2D extends Simulation
{
	private JTextField txtWinDims;
	private JTextField txtObjCount;
	private JTextField txtMassVariation;
	private JTextField txtRadiusVariation;
	private JTextField txtStandardMass;
	private JTextField txtStandardRadius;
	private JTextField txtSpaceDims;
	private Sim_Info_2D info;
	
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs = 0;	// Il numero di oggetti attivi
	
	private Graphics2D gc;	// Contesto grafico con cui disegnare
	private Vec2d camera;	// Posizione della camera, appartiene all GUI
	
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
		double dimX = info.spaceDim[0];
		double dimY = info.spaceDim[1];
		
		for(int i = 0; i < go.length; i++)
		{
			go[i] = new G_Obj(
				defaultMass + (int)(Math.random() * massVariation * 2 - massVariation),
				defaultRadius + (int)(Math.random() * radiusVariation * 2 - radiusVariation),
				new Vec2d(Math.random() * dimX, Math.random() * dimY));
		}
	}
	
	public Sim_Info_2D getInfo() {
		return info;
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
		
		info.spaceDim = new double[]{ spcDimW, spcDimH };

		info.G = 0.001;
		info.deltaT = 1;
		
		this.info = info;
	}

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
	
	/* Deve essere chiamato prima di refresh per impostare
	 * le informazioni per il disegno degli oggetti */
	public void setGC(Graphics2D g2, Vec2d camera)
	{
		this.gc = g2;
		this.camera = camera;
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
	public void createSimulationGUI() {
		new SimulationWin(this).open();
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
}
