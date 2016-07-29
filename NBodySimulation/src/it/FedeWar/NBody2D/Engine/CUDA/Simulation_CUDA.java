package it.FedeWar.NBody2D.Engine.CUDA;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import javax.swing.JPanel;

import org.lwjgl.BufferUtils;

import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_CUDA extends Simulation
{
	int vbo_id;
	
	static {
		try{
			System.load("/home/FedeWar/Programmazione/Cuda/NBody API/Debug/libNBody API.so");
		} catch(UnsatisfiedLinkError e) {
			System.err.println("Impossibile caricare la libreria: " + e.getMessage());
		}
	}
	
	@Override
	public void initEngine()
	{
		info = new Sim_Info_Acc();
		info.obj_count = 3 * 3;
		float[] vertexs = new float[] {
				-1, -1, 0,
				0, 1, -4,
				1, -1, 0
		};
		Sim_Info_Acc acc_info = (Sim_Info_Acc) info;
		vbo_id = createVBO(info.obj_count, vertexs);
		acc_info.vbo_id = vbo_id;
		//engine = new CuEngine(acc_info);
	}
	
	public int createVBO(int objs_count, float[] vertexs)
	{
		int vbo_id = glGenBuffers();
		FloatBuffer data = BufferUtils.createFloatBuffer(objs_count);
		data.put(vertexs);
		data.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return vbo_id;
	}
	
	public void render()
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, info.obj_count / 3);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	@Override
	public void createSettingsGUI(JPanel father)
	{

	}

	@Override
	public void packInfo()
	{

	}

	@Override
	public native void refresh();
	private native void init(Sim_Info_Acc si);
	public native void destroy();
}
