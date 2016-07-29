package it.FedeWar.NBody2D.Engine;

import javax.swing.JPanel;

public abstract class Simulation
{
	protected Sim_Info info;
	
	/* Crea l'iterfaccia per scegliere le 
	 * impostazioni della simulazione */
	public abstract void createSettingsGUI(JPanel father);
	
	/* Ricalcola la posizione di tutti gli oggetti */
	public abstract void refresh();
	
	/* L'utente ha scelto le impostazioni quindi le
	 * si possono impacchettare in un oggetto Sim_Info */
	public abstract void packInfo();
	
	/* Inizializza gli oggetti, la grafica ed eventuali
	 * librerie esterne per far funzionare l'engine */
	public abstract void initEngine();
	
	public Sim_Info getInfo() {
		return info;
	}
	
	public void setInfo(Sim_Info info) {
		this.info = info;
	}
}
