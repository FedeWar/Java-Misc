package it.FedeWar.NBody2D.Engine;

import javax.swing.JPanel;

public abstract class Simulation
{
	protected Sim_Info info;
	protected Engine engine;
	
	public abstract void genGUI(JPanel father);
	public abstract void packInfo();
	public abstract void genEngine();
	
	public Engine getEngine() {
		return engine;
	}
	
	public Sim_Info getInfo() {
		return info;
	}
	
	public void setInfo(Sim_Info info) {
		this.info = info;
	}
	
	public void refresh() {
		engine.refresh();
	}
}
