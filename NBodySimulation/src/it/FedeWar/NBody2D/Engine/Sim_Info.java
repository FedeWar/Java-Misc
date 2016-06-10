package it.FedeWar.NBody2D.Engine;

/* L'insieme dei delle impostazioni per la simulazione */
public interface Sim_Info
{
	int WIDTH = 0;
	int HEIGHT = 1;
	int OBJCOUNT = 2;
	
	public int getProperty(int index);
	public void setProperty(int index, int val);
}
