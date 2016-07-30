package it.FedeWar.NBody2D;

import it.FedeWar.NBody2D.Engine.Simulation;
import it.FedeWar.NBody2D.Engine.CUDA.Simulation_CUDA;
import it.FedeWar.NBody2D.Engine.Engine_2D.*;
import it.FedeWar.NBody2D.Engine.Engine_3D.Simulation_3D;
import it.FedeWar.NBody2D.GUI.*;

/* Punto di accesso per l'applicazione */
public class Applicazione
{

	public static void main(String[] args) throws InterruptedException
	{
		MainWin MW = new MainWin();		// Crea una finestra
		Simulation SI = MW.open();		// La avvia e aspetta i dati indietro
		
		if(SI == null)
			return;
		
		// TODO, questo sistema non è compatibile con PluginManager
		if(SI instanceof Simulation_2D)
		{
			SimulationWin SW = new SimulationWin((Simulation_2D)SI);
			SW.open();
		}
		else if(SI instanceof Simulation_CUDA)
		{
			GLWin GW = new GLWin((Simulation_CUDA)SI);
			GW.open();
		}
		else if(SI instanceof Simulation_3D)
		{
			Simulation_3D S3 = new Simulation_3D();
			S3.packInfo();
			S3.createSimulationGUI();
		}
	}
}
