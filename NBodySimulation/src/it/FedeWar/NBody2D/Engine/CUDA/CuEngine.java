package it.FedeWar.NBody2D.Engine.CUDA;

import it.FedeWar.NBody2D.Engine.Engine;

public class CuEngine extends Engine
{
	public CuEngine(Sim_Info_Acc si)
	{
		super(si);
		
		try{
			System.loadLibrary("");
		} catch(UnsatisfiedLinkError e) {
			
		}
		
		init(si);
	}
	
	private native void init(Sim_Info_Acc si);
	@Override
	public native void refresh();
	public native void destroy();
}
