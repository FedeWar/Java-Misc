package it.FedeWar.NBody2D.Engine;

public class Sim_Info_v1 implements Sim_Info
{
	private int[] properties;
	
	public Sim_Info_v1()
	{
		properties = new int[9];
	}
	
	@Override
	public int getProperty(int index)
	{
		return properties[index];
	}

	@Override
	public void setProperty(int index, int val)
	{
		properties[index] = val;
	}

}
