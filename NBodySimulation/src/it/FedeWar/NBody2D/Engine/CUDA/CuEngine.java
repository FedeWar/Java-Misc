package it.FedeWar.NBody2D.Engine.CUDA;

import it.FedeWar.NBody2D.Engine.Engine;

public class CuEngine extends Engine
{
	static {
		try{
			System.load("/home/FedeWar/Programmazione/Cuda/NBody API/Debug/libNBody API.so");
		} catch(UnsatisfiedLinkError e) {
			System.err.println("Impossibile caricare la libreria: " + e.getMessage());
		}
	}
	
	public CuEngine(Sim_Info_Acc si)
	{
		//init(si);
	}
	
	@Override public native void refresh();
	private native void init(Sim_Info_Acc si);
	public native void destroy();
}
