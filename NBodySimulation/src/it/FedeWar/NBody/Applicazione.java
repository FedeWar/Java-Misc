package it.FedeWar.NBody;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.FedeWar.NBody.Engine.Simulation;

/* Finestra aperta all'avvio del programma, permette
 * di scegliere la simulazione da avviare */
public class Applicazione extends JFrame
{
	private static final long serialVersionUID = 1581479179574006942L;
	
	private JList<String> lstSims;	// Lista delle simulazioni
	private Simulation sim;			// Simulazione selezionata
	private JPanel pnlSim;			// Pannello per le impostazioni
	private boolean avviaPressed;	// Distingue tra la pressione di avvia e della X

	/* Listener per i due pulsanti Avvia e Cambia Simulazione */
	private class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg)
		{
			// Testo del bottone premuto
			String src = ((JButton) arg.getSource()).getText();
			
			// Premuto "Avvia"
			if(src.compareTo("Avvia") == 0)
			{
				// Controlla che sia stata selezionata una simulazione
				if(sim == null)
					JOptionPane.showMessageDialog(null, "Nessuna simulazione selezionata.");
				else
				{
					setVisible(false);
					avviaPressed = true;
				}
			}
			// Premuto "Cambia simulazione", avvisa il
			// thread principale di caricare una simulazione
			else if(src.compareTo("Cambia Simulazione") == 0)
			{
				sim = PluginManager.create(lstSims.getSelectedIndex());
				pnlSim.removeAll();
				pnlSim.revalidate();
				pnlSim.setLayout(null);
				sim.createSettingsGUI(pnlSim);
				pnlSim.validate();
				pnlSim.repaint();
			}
		}
	}

	/* Punto di accesso per l'applicazione */
	public static void main(String[] args)
	{
		Applicazione app = new Applicazione();
		
		while(true)
		{
			// Avvia l'applicazione e aspetta che venga creata una simulazione
			Simulation simulation = app.open();

			// Se la simulazione è valida la avvia
			if(simulation != null)
				simulation.createSimulationGUI();
			
			// Non è valida = finestra chiusa dalla X
			else
				// Interrompe il programma
				break;
		}
	}
	
	/* Crea la finestra con tutti i controlli, non la mostra. */
	public Applicazione()
	{
		super("Simulatore N-Corpi");
		setSize(640, 480);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		BtnListener bl = new BtnListener();
		
		JLabel lineSeparator = new JLabel("");
		lineSeparator.setBounds(321, 0, 1, 402);
		this.add(lineSeparator);
		
		JLabel lblSettings = new JLabel("Impostazioni");
		lblSettings.setHorizontalAlignment(JLabel.CENTER);
		lblSettings.setBounds(107, 10, 98, 20);
		this.add(lblSettings);
		
		JButton btnRun = new JButton("Avvia");
		btnRun.setBounds(264, 408, 98, 33);
		btnRun.addActionListener(bl);
		this.add(btnRun);
		
		lstSims = new JList<String>();
		lstSims.setModel(PluginManager.getNames());
		lstSims.setBounds(328, 58, 296, 305);
		this.add(lstSims);
		
		JLabel lblSimList = new JLabel("Lista Simulazioni");
		lblSimList.setHorizontalAlignment(JLabel.CENTER);
		lblSimList.setBounds(328, 10, 296, 42);
		this.add(lblSimList);
		
		JButton btnChangeSim = new JButton("Cambia Simulazione");
		btnChangeSim.setBounds(376, 369, 193, 33);
		btnChangeSim.addActionListener(bl);
		this.add(btnChangeSim);
		
		pnlSim = new JPanel();
		pnlSim.setBounds(10, 35, 312, 367);
		this.add(pnlSim);
	}
	
	/*
	 * Rende visibile la finestra, rimane aperta fino a che
	 * l'utente non preme "Avvia" poi restituisce una simulazione.
	 */
	public Simulation open()
	{
		// Rende visibile la finestra
		setVisible(true);
		avviaPressed = false;
		
		// Finché la finestra è visibile non fa nulla
		while(isVisible())
			try { Thread.sleep(500); }
			catch(InterruptedException e) {}

		// Se è stata premuta la X chiude.
		if(!avviaPressed)
			return null;
		
		// Se è stata chiusa restituisce sim
		return sim;
	}
}
