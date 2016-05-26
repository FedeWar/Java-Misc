package it.fractals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;

import Frattali.*;
import it.fractals.listeners.Listener;
import it.fractals.listeners.MenuListener;

public class FractalApp extends JPanel
{
	private static final long serialVersionUID = -4084569459983256776L;
	public static FractalApp mainInstance;		// Riferimento alla classe principale
	
	private	DefaultListModel<String> lstNames;	// Lista nomi frattali disponibili
	private	ArrayList<Class<Frattale>> plugins;	// Lista classi dei frattali
	private	JList<String> lst_choose;			// Controllo con lista frattali
	
	private	JFrame frame;			// Il frame principale
	private Dimension Size;			// Dimensione finestra
	private	JLabel lblArg;			// Visualizza argomenti aggiuntivi
	public	Image canvas, bounds;	// Immagini da disegnare
	public	JSlider sld_cx,sld_cy;	// Slider per argomenti aggiuntivi, migliorare

	private Frattale Fra;	// Frattale istanziato

	public FractalApp()
	{
		mainInstance = this;	// Setta la classe come principale
		Size = new Dimension(	// Imposta le dimensioni
				Toolkit.getDefaultToolkit().getScreenSize().width - 1,
				Toolkit.getDefaultToolkit().getScreenSize().height - 1);
		canvas = new Image(Size.height, Size.height, Image.TYPE_INT_RGB);//Fa il canvas quadrato
		bounds = new Image(Size.height, Size.height, Image.TYPE_INT_ARGB);
		canvas.fill(Color.BLACK);
		
		LoadDefaultFractals();	// Carica la lista di frattali
		Init();					// Inizializza i controlli
		refresh();				// Disegna l'interfaccia
	}

	public Dimension getPreferredSize() { return Size; }

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, null, null);
		//g2.drawImage(bounds, null, null);
	}

	public static void main(String[] args)
	{
		new FractalApp();
	}

	/* Ridisegna tutta la GUI */
	public void refresh()
	{
		if(Fra != null)		// Controlla che esista
		{
			changedArg();	// Aggiorna l'argomento
			Fra.Draw();		// Lo ridisegna
		}
		frame.repaint();	// Ridisegna l'interfaccia
	}

	public void DrawBounds()	// Non finito
	{
		/*if(Fra == null)return;
		canvas.drawRect(Color.RED,
				(int)(Fra.trasl.r),
				(int)(Fra.trasl.i),
				(int)(canvas.getHeight() - Fra.zoom),
				(int)(canvas.getHeight() - Fra.zoom), 3);
		refresh();*/
	}

	/* Le sliders sono cambiate, aggiorna lblArg e il frattale corrente */
	public void changedArg()
	{
		lblArg.setText("Argomento: " + sld_cx.getValue() + ", " + sld_cy.getValue());
		Fra.setC((double)(sld_cx.getValue()) / 100, (double)(sld_cy.getValue()) / 100);
	}

	/* Restituisce l'id del frattale selezionato in lst_choose */
	public int getSelectedFract()
	{
		/**/
		for(int i = 0; i < lstNames.size(); i++)
			if(lst_choose.getSelectedValue() == lstNames.getElementAt(i))
				return i;
		return -1;
	}

	/* Aggiunge un frattale alla lista */
	@SuppressWarnings("unchecked")
	public void addPlugin(String name)
	{
		Class<?> plugin;
		try
		{
			plugin = ClassLoader.getSystemClassLoader().loadClass(name);
			plugins.add((Class<Frattale>) plugin);
			lstNames.addElement(plugin.getName());
			plugin = null;
		}
		catch (ClassNotFoundException e) {}
	}
	
	/* Alloca un frattale per essere disegnato */
	public void createFractal(int selected)
	{
		try {
			Fra = plugins.get(selected).newInstance();			// Alloca un oggetto
			Fra.Init(canvas.getHeight(), canvas.getHeight());	// Inizializza i dati
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Errore allocazione oggetto, è un frattale valido?");
		}
	}
	
	private void Init()
	{
		Listener msListener = new Listener();	// Crea i listener
		MenuListener mnuListener = new MenuListener();
		
		frame = new JFrame("Frattali");		// Crea un frame
		
		JLabel lbl_move = new JLabel("Muovi");
		lbl_move.setLocation(1000, 0);
		lbl_move.setSize(150, 50);
		lbl_move.setHorizontalAlignment(JLabel.CENTER);
		lbl_move.setVerticalAlignment(JLabel.CENTER);
		lbl_move.setFont(new Font("Serif", Font.PLAIN, 20));
		lbl_move.setForeground(new Color(0, 0, 0));
		lbl_move.validate();
		lbl_move.setVisible(true);

		lst_choose = new JList<String>(lstNames);
		lst_choose.setLocation(canvas.getHeight(),50);
		lst_choose.setSize(150,300);
		lst_choose.validate();
		lst_choose.setVisible(true);

		JButton btnMoreZoom = new JButton("+");
		btnMoreZoom.setLocation(1250, 50);
		btnMoreZoom.setSize(50,50);
		btnMoreZoom.validate();
		btnMoreZoom.setVisible(true);
		btnMoreZoom.addActionListener(msListener);

		JButton btnLessZoom = new JButton("-");
		btnLessZoom.setLocation(1250, 150);
		btnLessZoom.setSize(50,50);
		btnLessZoom.validate();
		btnLessZoom.setVisible(true);
		btnLessZoom.addActionListener(msListener);

		JButton btnMoveUp = new JButton("^");
		btnMoveUp.setLocation(1050, 50);
		btnMoveUp.setSize(50,50);
		btnMoveUp.validate();
		btnMoveUp.setVisible(true);
		btnMoveUp.addActionListener(msListener);

		JButton btnMoveDown = new JButton("V");
		btnMoveDown.setLocation(1050, 150);
		btnMoveDown.setSize(50,50);
		btnMoveDown.validate();
		btnMoveDown.setVisible(true);
		btnMoveDown.addActionListener(msListener);

		JButton btnMoveLeft = new JButton("<");
		btnMoveLeft.setLocation(1000, 100);
		btnMoveLeft.setSize(50,50);
		btnMoveLeft.validate();
		btnMoveLeft.setVisible(true);
		btnMoveLeft.addActionListener(msListener);

		JButton btnMoveRight = new JButton(">");
		btnMoveRight.setLocation(1100, 100);
		btnMoveRight.setSize(50,50);
		btnMoveRight.validate();
		btnMoveRight.setVisible(true);
		btnMoveRight.addActionListener(msListener);

		JButton btnRedraw = new JButton("Aggiorna");
		btnRedraw.setLocation(Size.height, 0);
		btnRedraw.setSize(150,50);
		btnRedraw.validate();
		btnRedraw.setVisible(true);
		btnRedraw.addActionListener(msListener);

		JLabel lblZoom = new JLabel("Zoom");
		lblZoom.setLocation(1250, 100);
		lblZoom.setSize(50,50);
		lblZoom.setHorizontalAlignment(JLabel.CENTER);
		lblZoom.setVerticalAlignment(JLabel.CENTER);
		lblZoom.setFont(new Font("Serif", Font.PLAIN, 20));
		lblZoom.setForeground(new Color(0, 0, 0));
		lblZoom.validate();
		lblZoom.setVisible(true);

		JLabel lblCredits = new JLabel("Coded & Compiled by Federico 'F' Guerra");
		lblCredits.setLocation(Size.width - 250, Size.height - 100);
		lblCredits.setSize(300, 50);
		lblCredits.validate();
		lblCredits.setVisible(true);

		JButton btnAnim = new JButton("Animazione");
		btnAnim.setSize(150, 50);
		btnAnim.setLocation(Size.height, 300);
		btnAnim.addActionListener(msListener);

		sld_cx = new JSlider(JSlider.HORIZONTAL, -100, 100, -52);
		sld_cx.setLocation(1000, 300);
		sld_cx.setSize(150, 30);
		sld_cx.validate();
		sld_cx.setVisible(true);

		sld_cy = new JSlider(JSlider.HORIZONTAL, -100, 100, -52);
		sld_cy.setLocation(1000, 330);
		sld_cy.setSize(150, 30);
		sld_cy.validate();
		sld_cy.setVisible(true);

		lblArg = new JLabel("Argomento: -0.52, -0.52");
		lblArg.setLocation(1000, 250);
		lblArg.setSize(150, 50);
		lblArg.setHorizontalAlignment(JLabel.CENTER);
		lblArg.validate();
		lblArg.setVisible(true);
		frame.add(lblArg, "Center");

		JMenuBar mnbMenu = new JMenuBar();
		frame.setJMenuBar(mnbMenu);
		
		JMenu mnuFile = new JMenu("File");
		mnbMenu.add(mnuFile);
		
		JMenuItem mniLoadScript = new JMenuItem("Load Script");
		mniLoadScript.addActionListener(mnuListener);
		mnuFile.add(mniLoadScript);

		frame.add(btnAnim, "Center");
		frame.add(sld_cx, "Center");
		frame.add(sld_cy, "Center");
		frame.add(lbl_move, "Center");
		frame.add(lst_choose, "Center");
		frame.add(btnMoveUp, "Center");
		frame.add(btnMoveDown, "Center");
		frame.add(btnMoveLeft, "Center");
		frame.add(btnMoveRight, "Center");
		frame.add(btnRedraw, "Center");
		frame.add(lblZoom, "Center");
		frame.add(btnMoreZoom, "Center");
		frame.add(btnLessZoom, "Center");
		frame.add(lblCredits, "Center");
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@SuppressWarnings("unchecked")
	private void LoadDefaultFractals()
	{
		try {
			plugins = new ArrayList<Class<Frattale>>();
			lstNames = new DefaultListModel<String>();
			
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass("Frattali.Mandelbrot"));
			lstNames.addElement("Mandelbrot");
			
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass("Frattali.Julia"));
			lstNames.addElement("Julia");
			
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass("Frattali.Sierpinski"));
			lstNames.addElement("Sierpinski");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
