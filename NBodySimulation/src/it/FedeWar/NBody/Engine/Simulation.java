package it.FedeWar.NBody.Engine;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Classe per l'implementazioni di simulazioni.
 * 
 * @author FedeWar
 * @version 1.0
 * @since 1.0
 */
public abstract class Simulation
{
	/**
	 * Sovraccaricarlo per disegnare l'interfaccia delle
	 * impostazioni della simulazione, chiamarlo per aggiungere
	 * il listener, che impacchetta le informazioni, al pannello.
	 * 
	 * @param father Il pannello su cui disegnare.
	 * @since 1.0
	 */
	public void createSettingsGUI(JPanel father) {
		father.addAncestorListener(new CloseListener());
	}
	
	/**
	 * Sovraccaricarlo per disegnare l'interfaccia della
	 * simulazione, si ha totale liberta sulla natura della
	 * interfaccia.
	 * 
	 * @since 1.0
	 */
	public abstract void createSimulationGUI();
	
	/**
	 * Chiamato ad ogni frame, deve essere sovraccaricato
	 * per svolgere tutte le attività della simulazione.
	 * 
	 * @since 1.0
	 */
	protected abstract void refresh();
	
	/**
	 * Inizializza gli oggetti, la grafica ed eventuali
	 * librerie esterne per far funzionare l'engine.
	 * 
	 * @since 1.0
	 */
	protected abstract void initEngine();
	
	/**
	 * Sovraccaricare per impacchettare le informazioni
	 * della simulazione per essere usate successivamente.
	 * 
	 * @since 1.0
	 */
	protected abstract void packInfo();
	
	/*
	 * Aspetta la chiusura del pannello delle impostazioni,
	 * quando viene chiuso impacchetta le informazioni.
	 */
	private class CloseListener implements AncestorListener
	{
		public void ancestorAdded(AncestorEvent arg0) {}
		public void ancestorMoved(AncestorEvent arg0) {}

		@Override
		public void ancestorRemoved(AncestorEvent arg0) {
			packInfo();
		}
		
	}
}
