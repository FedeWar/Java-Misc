package it.FedeWar.NBody2D;

import it.FedeWar.NBody2D.Engine.Simulation;
import it.FedeWar.NBody2D.Engine.Engine_2D.*;
import it.FedeWar.NBody2D.GUI.*;
import it.FedeWar.NBody2D.GUI.OpenGL.*;

/* Punto di accesso per l'applicazione */
public class Applicazione
{

	public static void main(String[] args) throws InterruptedException
	{
		MainWin MW = new MainWin();		// Crea una finestra
		Simulation SI = MW.open();		// La avvia e aspetta i dati indietro
		
		if(SI == null)
			return;
		else if(SI instanceof Simulation_2D)
		{
			SI.genEngine();
			SimulationWin SW = new SimulationWin((Simulation_2D)SI);
			SW.open();
		}
		/*else if(SI instanceof Sim_Info_Acc)
		{
			SI.genEngine();
			GLWin GW = new GLWin();
			GW.open(SI);
		}*/
	}
}
