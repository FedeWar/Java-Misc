package it.FedeWar.NBody2D.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import it.FedeWar.NBody2D.Engine.PluginManager;
import it.FedeWar.NBody2D.Engine.Simulation;

/* Finestra aperta all'avvio del programma, permette
 * di scegliere la simulazione da avviare */
public class MainWin extends JFrame
{
	private static final long serialVersionUID = -847133995748685491L;
	
	private JList<String> lstSims;	// Lista delle simulazioni
	private Simulation sim;			// Simulazione selezionata
	private JPanel pnlSim;			// Pannello per le impostazioni

	private class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg)
		{
			String src = ((JButton) arg.getSource()).getText();
			
			// Premuto "Avvia"
			if(src.compareTo("Avvia") == 0 && sim != null) {
				sim.packInfo();
				dispose();
			}
			// Premuto "Cambia simulazione"
			else if(src.compareTo("Cambia Simulazione") == 0) {
				sim = PluginManager.create(lstSims.getSelectedIndex());
				sim.genGUI(pnlSim);
			}
		}
	}

	public MainWin()
	{
		super("Simulatore N-Corpi");
		this.setSize(640, 480);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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
	
	public Dimension getPreferredSize()
	{
		return getSize();
	}
	
	public Simulation open()
	{
		setVisible(true);
		
		while(isVisible())
			try { Thread.sleep(1000);
			} catch(InterruptedException e) {}
		dispose();

		return sim;
	}
}
