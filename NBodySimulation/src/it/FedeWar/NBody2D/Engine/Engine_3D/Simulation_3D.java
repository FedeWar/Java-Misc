package it.FedeWar.NBody2D.Engine.Engine_3D;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Dimension;
import java.nio.FloatBuffer;

import javax.swing.JPanel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import it.FedeWar.NBody2D.Engine.Sim_Info;
import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_3D extends Simulation
{
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs = 0;	// Il numero di oggetti attivi
	private long wndHandle;		// Handle per la finestra
	private float[] posBuffer;	// Tutte le posizioni degli oggetti, per il VBO
	int vbo_id;					// Puntatore al VBO
	
	@Override
	public void initEngine()
	{
		posBuffer = new float[info.obj_count * 3];
		G_Obj.staticInit(this, posBuffer);
		
		go = new G_Obj[info.obj_count];
		pnum_objs = info.obj_count;
		
		int defaultMass = info.standard_mass;
		int defaultRadius = info.standard_radius;
		int massVariation = info.mass_variation;
		int radiusVariation = info.radius_variation;
		
		for(int i = 0; i < info.obj_count; i++)
		{
			posBuffer[i * 3] = (float)(Math.random() * info.spaceDim[0]);
			posBuffer[i * 3 + 1] = (float)(Math.random() * info.spaceDim[1]);
			posBuffer[i * 3 + 2] = (float)(Math.random() * info.spaceDim[2]);
			
			go[i] = new G_Obj(
				defaultMass + (int)(Math.random() * massVariation * 2 - massVariation),
				defaultRadius + (int)(Math.random() * radiusVariation * 2 - radiusVariation),
				i * 3);
		}
		
		vbo_id = createVBO(posBuffer);
	}
	
	@Override
	public void refresh()
	{
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updateAcc();				// Ne ricalcola l'accelerazione
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updatePos();				// Ne ricalcola la velocità
	}

	@Override
	public void packInfo()
	{
		info = new Sim_Info();
		
		info.deltaT = 1;
		info.G = 1;
		info.mass_variation = 0;
		info.standard_mass = 1;
		info.standard_radius = 3;
		info.radius_variation = 0;
		info.spaceDim = new double[] { 1, 1, 1 };
		info.obj_count = 6;
		info.winDim = new Dimension(500, 500);
	}

	@Override
	public void createSettingsGUI(JPanel father)
	{
		
	}
	
	public void createSimulationGUI()
	{
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		// Crea la finestra e verifica che sia valida
		wndHandle = glfwCreateWindow(1000, 700, "Hello World!", NULL, NULL);
		if (wndHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Crea il contesto grafico e prepara la finestra
		glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(wndHandle, 200, 200);
		glfwMakeContextCurrent(wndHandle);
		glfwSwapInterval(1);
		glfwShowWindow(wndHandle);
		
		GL.createCapabilities();
		
		// Crea la pipeline
		Pipeline pipeline = new Pipeline("shaders/vertex.glsl", "shaders/fragment.glsl");
		pipeline.use();
		pipeline.setUniform("test", 0.5f);
		
		initEngine();
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		// Render loop
		while (!glfwWindowShouldClose(wndHandle))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			glEnableClientState(GL_VERTEX_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
			glVertexPointer(3, GL_FLOAT, 0, 0);
			
			glDrawArrays(GL_TRIANGLES, 0, info.obj_count);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glDisableClientState(GL_VERTEX_ARRAY);

			glfwSwapBuffers(wndHandle);
			glfwPollEvents();
		}
		
		// Libera le risorse
		glfwFreeCallbacks(wndHandle);
		glfwDestroyWindow(wndHandle);
		glfwTerminate();
	}
	
	private static int createVBO(float[] vertexes)
	{
		int vbo_id = glGenBuffers();
		FloatBuffer data = BufferUtils.createFloatBuffer(vertexes.length);
		data.put(vertexes);
		data.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return vbo_id;
	}
}
