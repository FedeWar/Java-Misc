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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_3D extends Simulation
{
	private Sim_Info_3D info;	// Informazioni sulla simulazione
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs;		// Il numero di oggetti attivi
	
	private float[] posBuffer;		// Tutte le posizioni degli oggetti, per il VBO
	private int vbo_id;				// Puntatore al VBO
	private RenderEngine pipeline;	// Pipeline OpenGL
	
	private long wndHandle;		// Handle per la finestra

	@Override
	public void initEngine()
	{
		// Crea l'array delle posizioni, verrà passato alla GPU
		posBuffer = new float[info.obj_count * 3];
		
		// Imposta le proprietà generali degli oggetti gravitazionali
		G_Obj.staticInit(this, posBuffer);

		// Crea un array di oggetti gravitazionali
		go = new G_Obj[pnum_objs = info.obj_count];

		// Crea degli alias per i nomi delle proprietà
		int dfltMass = info.standard_mass;
		int dfltRadius = info.standard_radius;
		int massVar = info.mass_variation;
		int radiusVar = info.radius_variation;

		// Imposta le propietà degli oggetti
		for(int i = 0; i < info.obj_count; i++)
		{
			posBuffer[i * 3] = (float)(Math.random() * info.spaceDim[0]);
			posBuffer[i * 3 + 1] = (float)(Math.random() * info.spaceDim[1]);
			posBuffer[i * 3 + 2] = -(float)(Math.random() * info.spaceDim[2]);

			go[i] = new G_Obj(
					dfltMass + (int)(Math.random() * massVar * 2 - massVar),
					dfltRadius + (int)(Math.random() * radiusVar * 2 - radiusVar),
					i * 3);
		}
		
		// Crea il vbo per i vertici
		pipeline.points.bind();
		vbo_id = pipeline.points.createVBO(posBuffer, 3);
	}
	
	public Sim_Info_3D getInfo() {
		return info;
	}

	@Override
	public void refresh()
	{
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updateAcc();				// Ne ricalcola l'accelerazione
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updatePos();				// Ne ricalcola la velocità

		// Copia i dati in un buffer
		FloatBuffer data = BufferUtils.createFloatBuffer(posBuffer.length);
		data.put(posBuffer);
		data.flip();

		// Copia il buffer nel vbo
		glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	protected void packInfo()
	{
		info = new Sim_Info_3D();

		info.deltaT = 0.1f;
		info.G = 0.1f;
		info.mass_variation = 0;
		info.standard_mass = 1;
		info.standard_radius = 1;
		info.radius_variation = 0;
		info.spaceDim = new double[] { 15, 15, 15 };
		info.obj_count = 128;
		info.winDim = new Dimension(500, 500);
	}

	@Override
	public void createSettingsGUI(JPanel father)
	{
		super.createSettingsGUI(father);
	}

	public void createSimulationGUI()
	{
		createWin();

		// Crea la pipeline
		pipeline = new RenderEngine("shaders/scene/vertex.glsl", "shaders/scene/fragment.glsl");
		pipeline.use();
		pipeline.setUniform("test", 1.0f);
		pipeline.createProjectionMatrix(info.winDim.width / info.winDim.height);
		pipeline.bindProjectionMatrix();

//		RenderEngine screen = new RenderEngine("shaders/screen/vertex.glsl", "shaders/screen/fragment.glsl");

		setCallbacks();

		initEngine();

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		pipeline.createFBO();

		GL11.glEnable(GL20.GL_POINT_SPRITE);
		GL11.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

		boolean postprocessing = false;

		// Render loop
		while (!glfwWindowShouldClose(wndHandle))
		{
			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			pipeline.use();

			if(postprocessing)
				pipeline.f.bind();

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnableClientState(GL_VERTEX_ARRAY);

			pipeline.points.bind();
			glDrawArrays(GL_POINTS, 0, info.obj_count);
			pipeline.points.unbind();

			// Particelle
			/*glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
			glVertexPointer(3, GL_FLOAT, 0, 0);
			glDrawArrays(GL_POINTS, 0, info.obj_count);*/

			// Floor
			/*glBindBuffer(GL_ARRAY_BUFFER, floor);
			glVertexPointer(3, GL_FLOAT, 0, 0);
			glDrawArrays(GL_TRIANGLES, 0, 6);*/

			//glBindBuffer(GL_ARRAY_BUFFER, 0);
			//glDisableClientState(GL_VERTEX_ARRAY);

			/*if(postprocessing)
			{
				pipeline.f.unbind();

				// Usa gli shader per lo schermo
				screen.use();
				//glEnableClientState(GL_VERTEX_ARRAY);

				// Framebuffer texture
				//glBindBuffer(GL_ARRAY_BUFFER, quad);
				//glVertexPointer(3, GL_FLOAT, 0, 0);
				//glActiveTexture(GL_TEXTURE0);
				//glBindTexture(GL_TEXTURE_2D, pipeline.f.getTex());
				//glDrawArrays(GL_TRIANGLES, 0, 6);

				//glBindBuffer(GL_ARRAY_BUFFER, 0);

				pipeline.screen.bind();
				glEnableVertexAttribArray(0);
				//glEnableVertexAttribArray(1);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, pipeline.f.getTex());
				glDrawArrays(GL_TRIANGLES, 0, 6);
				pipeline.screen.unbind();
			}*/

			glDisableClientState(GL_VERTEX_ARRAY);
			glfwSwapBuffers(wndHandle);
			glfwPollEvents();

			refresh();
		}

		// Libera le risorse
		glfwFreeCallbacks(wndHandle);
		glfwDestroyWindow(wndHandle);
		glfwTerminate();
	}

	private void setCallbacks()
	{
		// Callback per il resize della finestra
		glfwSetWindowSizeCallback(wndHandle, (window, width, height) -> {
			glViewport(0, 0, width, height);
			pipeline.createProjectionMatrix(width / height);
		});

		// Callback per la tastiera
		glfwSetKeyCallback(wndHandle, (window, key, scancode, action, mods) -> {
			switch(key)
			{
			case GLFW_KEY_UP:
				pipeline.pitch -= 0.01f;
				break;

			case GLFW_KEY_DOWN:
				pipeline.pitch += 0.01f;
				break;

			case GLFW_KEY_LEFT:
				pipeline.yaw -= 0.01f;
				break;

			case GLFW_KEY_RIGHT:
				pipeline.yaw += 0.01f;
				break;

			case GLFW_KEY_W:
				pipeline.cameraPos.add(
						(float) (Math.sin(pipeline.yaw) * -0.1),
						(float) (Math.sin(pipeline.pitch) * 0.1),
						(float) (Math.cos(pipeline.yaw) * 0.1));
				break;

			case GLFW_KEY_S:
				pipeline.cameraPos.sub(
						(float) (Math.sin(pipeline.yaw) * -0.1),
						(float) (Math.sin(pipeline.pitch) * 0.1),
						(float) (Math.cos(pipeline.yaw) * 0.1));
				break;

			}
			pipeline.bindProjectionMatrix();
		});
	}
	
	private void createWin()
	{
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Crea la finestra e verifica che sia valida
		wndHandle = glfwCreateWindow(info.winDim.width, info.winDim.height, "Hello World!", NULL, NULL);
		if (wndHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Crea il contesto grafico e prepara la finestra
		glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(wndHandle, 200, 200);
		glfwMakeContextCurrent(wndHandle);
		glfwSwapInterval(1);
		glfwShowWindow(wndHandle);

		GL.createCapabilities();
	}
}
