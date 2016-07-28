package it.FedeWar.NBody2D.Engine.CUDA;

import javax.swing.JPanel;

import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_CUDA extends Simulation
{
	@Override
	public void genEngine()
	{
		engine = new CuEngine((Sim_Info_Acc) info);
	}
	
	@Override
	public void genGUI(JPanel father)
	{

	}

	@Override
	public void packInfo()
	{

	}
}
