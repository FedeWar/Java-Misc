package it.FedeWar.NBody2D.Engine.CUDA;

import it.FedeWar.NBody2D.Engine.Sim_Info;

public class Sim_Info_Acc extends Sim_Info
{
	public int standard_mass;
	public int mass_variation;
	
	public int standard_radius;
	public int radius_variation;
	
	public int spaceDimX, spaceDimY, spaceDimZ;
	
	public int vbo_id;
	// TODO, impostazioni GPU
}
