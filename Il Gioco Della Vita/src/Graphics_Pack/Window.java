package Graphics_Pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Main_Pack.Applicazione;
import Main_Pack.DrawingListener;
import Main_Pack.Listener;

//Bisogna commentare tutto il codice
public class Window extends JPanel
{
	// Proprietà del pannello
	private static final long serialVersionUID = -3327143243211825258L;
	private Dimension Size;
	
	// Proprietà della simulazione
	private boolean drawingMode = false;	// Se è in modalità disegno
	
	//Controlli
	private JButton btnDraw, btnStart;				// Avvia la modalità di disegno
	private JTextArea txtWidth, txtHeight, txtTile;	// Le dimensioni del canvas
	private JLabel lblWidth, lblHeight, lblTile;	// Label
	public Image canvas;							// Canvas su cui disegnare
	private Applicazione App;
	
	public Window(Applicazione app)
	{
		App = app;
		Size = new Dimension(200, 200);
        setup(app);
	}
	
	public Dimension getPreferredSize()
    {
        return Size;
    }
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if(drawingMode)
        	g2.drawImage(canvas, null, null);
    }
	
	private void setup(Applicazione arg)
	{
		txtWidth = new JTextArea("32");
		txtWidth.setSize(70, 16);
		txtWidth.setLocation(100, 10);
		txtWidth.setVisible(true);
		arg.add(txtWidth);
		
		lblWidth = new JLabel("Width:");
		lblWidth.setSize(100, 20);
		lblWidth.setLocation(20, 10);
		lblWidth.setVisible(true);
		arg.add(lblWidth);
		
		txtHeight = new JTextArea("32");
		txtHeight.setSize(70, 16);
		txtHeight.setLocation(100, 40);
		txtHeight.setVisible(true);
		arg.add(txtHeight);
		
		lblHeight = new JLabel("Height:");
		lblHeight.setSize(100, 20);
		lblHeight.setLocation(20, 40);
		lblHeight.setVisible(true);
		arg.add(lblHeight);
		
		btnDraw = new JButton("Start drawing");
		btnDraw.setSize(150, 50);
		btnDraw.setLocation(25, 110);
		btnDraw.setVisible(true);
		btnDraw.addActionListener(new Listener(arg));
		arg.add(btnDraw);
		
		txtTile = new JTextArea("10");
		txtTile.setLocation(100, 70);
		txtTile.setSize(70, 16);
		txtTile.setVisible(true);
		arg.add(txtTile);
		
		lblTile = new JLabel("Unit dimension:");
		lblTile.setLocation(10, 70);
		lblTile.setSize(100, 16);
		lblTile.setVisible(true);
		arg.add(lblTile);
		
		btnStart = new JButton("Start game");
		btnStart.setSize(150, 50);
		btnStart.addActionListener(new Listener(arg));
		btnStart.setVisible(false);
		arg.add(btnStart);
	}

	public void runDrawing()
	{
		drawingMode = true;
		
		btnDraw.setVisible(false);
		txtWidth.setVisible(false);
		txtHeight.setVisible(false);
		lblWidth.setVisible(false);
		lblHeight.setVisible(false);
		txtTile.setVisible(false);
		lblTile.setVisible(false);
		
		int W = Integer.parseInt(txtWidth.getText());
		int H = Integer.parseInt(txtHeight.getText());
		int T = Integer.parseInt(txtTile.getText());
		
		canvas = new Image(W * T, H * T, Image.TYPE_INT_RGB, T);
		canvas.fill(Color.black);
		addMouseListener(new DrawingListener(App, canvas));
		
		btnStart.setVisible(true);
		btnStart.setLocation(25, H * T + 10);
		Size = new Dimension(W * T, H * T + 70);
		setSize(Size);
		
		App.pack();
		App.getContentPane().setPreferredSize(Size);
		
		App.repaint();
	}

	public void runSimulation()
	{
		btnStart.setText("Stop game");
	}

	public void stopSimulation()
	{
		btnStart.setText("Start game");
	}
}
