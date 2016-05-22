package it.tupper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import it.tupper.Listeners.BtnListener;
import it.tupper.Listeners.PnlListener;
import it.tupper.Listeners.TmrListener;

public class Tupper extends JPanel
{
	private static final	int[]		COLOR = new int[]{0, Color.BLACK.getRGB(), 0, Color.WHITE.getRGB()};
	private static final	long		serialVersionUID = 5679346075989541947L;
	private static final	int			height = 17, width = 106, tile = 10;
	private static final	Dimension	Size = new Dimension((width - 1) * tile, (height + 9) * tile);
	
	public	static final	BigInteger	BI17 = BigInteger.valueOf(17);
	public	static final	BigInteger	BI2 = BigInteger.valueOf(2);
	
	private					boolean		drawing = false;
	private					Timer		drwTimer;
	private					JTextArea	txtNum;
	
	public					int			color;
	public					Image		I;
	public					JFrame		frame;
	
	static BigInteger Self = new BigInteger(
			"96093937991895888497167296212785275471500433966012930665150551927170" +
			"28023952664246896428421743507181212671537827706233559932372808741443" +
			"07891325963941337723487857735749823926629715517173716995165232890538" +
			"22161240323885586618401323558513604882869333790249145422928866708109" +
			"61844960917051834540678277315517054053816273809676025656250169814820" +
			"83418783163849115590225610003652351370343874461848378737238198224849" +
			"86346503315941005497470059313833922649724946175154572836670236974546" +
			"1014655997933798537483143786841806593422227898388722980000748404719"),
	Federico = new BigInteger(
			"61912584820283296656101716143401975449757573414322493728916665495113409" +
			"52449761162337574708385356433497261147271682291424191424662250848170884" +
			"07019092856980982067492869851375408079359256162467989065588386658232504" +
			"78519554206028163484346364428198947062289815027878147788944867753207714" +
			"98773619583451230664606261412860629387540393281354030519551330571771804" +
			"31037760384440540249024229419938250152095426178490552496149974778218257" +
			"1251975467590724333293699314244654387516306966058271296716800");
	
	public static void main(String[] args)
	{
		Tupper A = new Tupper();
		A.Draw(Self);
		A.frame.repaint();
	}
	
	public Tupper()
	{
		super();
		
		I = new Image(width, height, Image.TYPE_BYTE_GRAY, tile);
		frame = new JFrame("Tupper's Self-Referential Formula");
		setSize(Size);
		addMouseListener(new PnlListener(this));
		
		txtNum = new JTextArea(Self.toString());
		txtNum.setSize(Size.width, 63);
		txtNum.setLocation(0, height * tile);
		txtNum.setBackground(Color.BLACK);
		txtNum.setForeground(Color.WHITE);
		txtNum.setLineWrap(true);
		txtNum.setVisible(true);
		frame.add(txtNum);
		
		JButton btnDraw = new JButton("Disegna");
		btnDraw.setSize(100, 30);
		btnDraw.setLocation(10, height * tile + 66);
		btnDraw.addMouseListener(new BtnListener(this));
		frame.add(btnDraw);
		
		JButton btnCompute = new JButton("Calcola");
		btnCompute.setSize(100, 30);
		btnCompute.setLocation(120, height * tile + 66);
		btnCompute.addMouseListener(new BtnListener(this));
		frame.add(btnCompute);
		
		drwTimer = new Timer(10, new TmrListener(this));
		
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Dimension getPreferredSize()
    {
        return Size;
    }
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(I, null, 0, 0);
    }
	
	public void Draw(BigInteger k)
	{
		BigInteger Y;
		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				Y = BigInteger.valueOf(y).add(k);
				
				/* Applica la formula e sceglie il colore di conseguenza */
				if(Y.divide(BI17).divide(BI2.pow(17 * x + Y.mod(BI17).intValue())).mod(BI2).doubleValue() > 0.5f)
					color = COLOR[1];	// Nero
				else
					color = COLOR[3];	// Bianco
				
				I.drawPoint(width - x - 1, y, color);	// Disegna il punto
			}
		}
		frame.repaint();	// Ridisegna l'interfaccia
	}
	
	/* Estrae il numero dal disegno */
	public void drawNumber()
	{
		char[] binNum = new char[height * width];
		
		for(int i = 0; i < height * width; i++)
			binNum[i] = I.getElementColor(i / height, height - i % height - 1) == COLOR[1] ? '1' : '0';
		
		txtNum.setText(new BigInteger(new String(binNum), 2).multiply(BI17).toString());
	}
	
	public void invertDrawMode()
	{
		drawing = !drawing;	// Inverte la modalità
		if(!drawing)		// Se è stata disattivata
			drawNumber();	// Calcola il numero
	}

	public String getNum()
	{
		return txtNum.getText();
	}
	
	/* Chiamata dai listener quando viene cliccato il JPanel */
	public void clickOnPanel(int btn, boolean pressed)
	{
		if(drawing)					// La modalità di disegno deve essere attiva
		{
			color = COLOR[btn];		// Seleziona il colore secondo il tasto
			
			if(pressed)				// Sta venendo premuto
				drwTimer.start();	// Attiva il timer
			else if(!pressed)		// Viene rilasciato
				drwTimer.stop();	// Disattiva il timer
		}
	}
}