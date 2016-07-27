package it.FedeWar.TupperSelfRef;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Tupper extends JPanel
{
	private static final	long		serialVersionUID = 5679346075989541947L;
	private static final	int[]		COLOR;
	private static final	int			height = 17;
	private static final	int			width = 106;
	private static final	int			tile = 10;
	private static final	Dimension	Size;
	public	static final	BigInteger	BI17;
	public	static final	BigInteger	BI2;

	private	JFrame		frame;
	private	JTextArea	txtNum;
	private	Image		image;
	private	int			color;

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

	static {
		COLOR = new int[] { 0, Color.BLACK.getRGB(), 0, Color.WHITE.getRGB() };
		Size = new Dimension((width - 1) * tile, (height + 9) * tile + 10);
		BI17 = BigInteger.valueOf(17);
		BI2 = BigInteger.valueOf(2);
	}

	/* Disegna sullo schermo quando il mouse viene trascinato */
	private class PnlListener implements MouseMotionListener
	{
		private int counts = 0;
		private int leftbtn = MouseEvent.BUTTON1_DOWN_MASK;
		private int rightbtn = MouseEvent.BUTTON3_DOWN_MASK;

		public void mouseDragged(MouseEvent e)
		{
			if((e.getModifiersEx() & leftbtn) == leftbtn)
				color = COLOR[1];
			else if((e.getModifiersEx() & rightbtn) == rightbtn)
				color = COLOR[1];
			
			Point mouse = MouseInfo.getPointerInfo().getLocation();

			image.drawPoint(	// Allinea e disegna i punti su cui passa il mouse
				(mouse.x - (frame.getLocation().x + (frame.getWidth() - frame.getContentPane().getWidth()) / 2)) / image.Tile,
				(mouse.y - (frame.getLocation().y + frame.getHeight() - frame.getContentPane().getHeight() - 9)) / image.Tile,
				color);
			
			// Incrementa il contatore 10 volte, 
			// così evita di ridisegnare troppo spesso
			if(counts++ == 10)
			{
				counts = 0;			// Azzera
				frame.repaint();	// Ridisegna la GUI
			}
		}

		public void mouseMoved(MouseEvent arg0) { }
	}

	/* Cattura gli eventi dai bottoni */
	private class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String source = ((JButton) e.getSource()).getText();
			
			// Disegna il grafico dal numero generatore
			if(source.compareTo("Disegna il Grafico") == 0)
				draw();
			
			// Calcola il numero generatore del grafico
			else if(source.compareTo("Calcola il Numero") == 0)
				computeNumber();
		}
	}
	
	public static void main(String[] args)
	{
		Tupper A = new Tupper();
		A.draw(Self);
	}

	public Tupper()
	{
		super();

		image = new Image(width, height, Image.TYPE_BYTE_GRAY, tile);
		frame = new JFrame("Tupper's Self-Referential Formula");
		this.addMouseMotionListener(new PnlListener());

		txtNum = new JTextArea(Self.toString());
		txtNum.setSize(Size.width, 63);
		txtNum.setLocation(0, height * tile);
		txtNum.setBackground(Color.BLACK);
		txtNum.setForeground(Color.WHITE);
		txtNum.setLineWrap(true);
		txtNum.setVisible(true);
		frame.add(txtNum);

		BtnListener btnListener = new BtnListener();
		
		JButton btnGetNum = new JButton("Calcola il Numero");
		btnGetNum.setSize(200, 30);
		btnGetNum.setLocation(10, height * tile + 66);
		btnGetNum.addActionListener(btnListener);
		frame.add(btnGetNum);
		
		JButton btnPlot = new JButton("Disegna il Grafico");
		btnPlot.setSize(200, 30);
		btnPlot.setLocation(220, height * tile + 66);
		btnPlot.addActionListener(btnListener);
		frame.add(btnPlot);

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
		g2.drawImage(image, null, 0, 0);
	}

	/* Disegna usando il numero nella TextBox */
	public void draw()
	{
		// Ottiene il numero e calcola il resto della divisione con 17
		BigInteger Num = new BigInteger(txtNum.getText());
		int remainder = Num.mod(Tupper.BI17).intValue();

		// Il grafico può essere disegnato solo se Num % 17 == 0
		if(remainder == 0)
		{
			draw(Num);
		}
		else
		{
			JOptionPane.showMessageDialog(null,
				"Num % 17 deve essere uguale a 0, attuale: " + remainder,
				"Errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void draw(BigInteger k)
	{
		BigInteger Y;

		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				Y = BigInteger.valueOf(y).add(k);

				// Applica la formula e sceglie il colore di conseguenza
				if(Y.divide(BI17).divide(BI2.pow(17 * x + Y.mod(BI17).intValue())).mod(BI2).doubleValue() > 0.5f)
					color = COLOR[1];	// Nero
				else
					color = COLOR[3];	// Bianco
				
				// Disegna il punto
				image.drawPoint(width - x - 1, y, color);
			}
		}
		frame.repaint();	// Ridisegna l'interfaccia
	}

	/* Estrae il numero dal disegno */
	public String computeNumber()
	{
		// Il numero in base 2
		char[] binNum = new char[height * width];

		// Itera su tutti le tile dell'immagine e se è nera
		// aggiunge un 1, se è bianca uno 0, a binNum.
		for(int i = 0; i < height * width; i++)
		{
			binNum[i] = image.getElementColor(i / height, height - i % height - 1) == COLOR[1] ? '1' : '0';
		}
		
		// Ottiene la stringa dal numero binario
		String num = new BigInteger(new String(binNum), 2).multiply(BI17).toString();
		txtNum.setText(num);
		return num;
	}
}