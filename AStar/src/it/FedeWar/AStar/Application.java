package it.FedeWar.AStar;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Application extends JFrame
{
	private static final long serialVersionUID = -6353104441039321873L;

	/* Altezza del terreno selezionata */
	public static int terrainHeight = 128;

	/* Gestore per la grafica e per AStar */
	private Canvas pnlCanvas;

	/* Ascolta i pulsanti e setta i comandi correlati. */
	private class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String src = ((JButton)e.getSource()).getText();

			if(src.compareTo("Avvia") == 0)
				pnlCanvas.setCmd(Canvas.RUN);

			else if(src.compareTo("Inizio") == 0)
				pnlCanvas.setCmd(Canvas.START);

			else if(src.compareTo("Fine") == 0)
				pnlCanvas.setCmd(Canvas.END);

			// FIXME
			else if(src.compareTo("Ridimensiona tile") == 0)
			{
			}
		}
	}

	/* Entry point */
	public static void main(String[] args) {
		new Application();
	}

	/* Costruttore */
	public Application()
	{
		super("AStar");
		initialize();

		// Avvia l'applicazione
		setVisible(true);
		pnlCanvas.run();
	}

	/* Inizializza l'interfaccia grafica */
	private void initialize()
	{
		// Inizializza le proprietà di JFrame
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, w, h);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				pnlCanvas.setSize(
						getSize().width - pnlCanvas.getX(),
						getSize().height - pnlCanvas.getY());
			}
		});

		// Button listener condiviso tra più controlli
		BtnListener BL = new BtnListener();

		JButton btnAvvia = new JButton("Avvia");
		btnAvvia.setBounds(12, 0, 117, 25);
		btnAvvia.addActionListener(BL);
		add(btnAvvia);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(12, 31, 117, 25);
		btnReset.addActionListener(BL);
		add(btnReset);

		JButton btnCasuale = new JButton("Casuale");
		btnCasuale.setBounds(12, 58, 117, 25);
		btnCasuale.addActionListener(BL);
		add(btnCasuale);

		JButton btnTile = new JButton("Ridimensiona tile");
		btnTile.setBounds(12, 200, 117, 25);
		btnTile.addActionListener(BL);
		add(btnTile);

		pnlCanvas = new Canvas(
				new Dimension((w - 150) / 10, h / 10),
				new Dimension(10, 10));
		pnlCanvas.setBounds(150, 0, 300, 250);
		add(pnlCanvas);

		JButton btnInizio = new JButton("Inizio");
		btnInizio.setBounds(12, 101, 117, 25);
		btnInizio.addActionListener(BL);
		add(btnInizio);

		JButton btnFine = new JButton("Fine");
		btnFine.setBounds(12, 138, 117, 25);
		btnFine.addActionListener(BL);
		add(btnFine);

		JButton btnHelp = new JButton("?");
		btnHelp.setBounds(12, 290, 117, 25);
		btnHelp.addActionListener(BL);
		add(btnHelp);

		JSpinner spnHeight = new JSpinner();
		spnHeight.setBounds(12, 165, 100, 25);
		spnHeight.setValue(terrainHeight);
		spnHeight.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				terrainHeight = (int) spnHeight.getValue();
			}
		});
		add(spnHeight);
	}
}
