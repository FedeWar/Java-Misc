package it.FedeWar.NBody2D;

import it.FedeWar.NBody2D.Engine.Engine;
import it.FedeWar.NBody2D.Engine.Sim_Info;
import it.FedeWar.NBody2D.GUI.MainWin;
import it.FedeWar.NBody2D.GUI.SimulationWin;

/* Punto di accesso per l'applicazione */
public class Applicazione
{
	
	public static void main(String[] args) throws InterruptedException
	{
		MainWin MW = new MainWin();		// Crea una finestra
		Sim_Info SI = MW.open();		// La avvia e aspetta i dati indietro
		SimulationWin SW = new SimulationWin(SI, new Engine(SI));
		SW.open();
	}
}