package it.FedeWar.NBody.Engine.Engine_CUDA;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import javax.swing.JPanel;
import javax.swing.JTextField;

import it.FedeWar.NBody.Engine.Simulation;
import it.FedeWar.NBody.Engine.Engine_CUDA.RenderEngine;

public class Simulation_CUDA extends Simulation
{
	public static final String name = "Simulazione CUDA";
	
	private long wndHandle;
	private RenderEngine pipeline;	// Pipeline OpenGL
	
	// Interfaccia impostazioni
	JTextField txtG, txtDt, txtObj;
	
	// Informazioni sulla simulazione
	private float g_const, dt_const;
	private int obj_count;
	
	public Simulation_CUDA()
	{
		System.load("/home/FedeWar/Programmazione/Cuda/NBody API/Debug/libcuda_jni.so");
	}
	
	@Override
	public void createSettingsGUI(JPanel parent)
	{
		super.createSettingsGUI(parent);
		final int half_way = parent.getWidth() / 2;
		
		txtG = new JTextField("0.001");
		txtG.setBounds(half_way, 50, half_way - 10, 25);
		parent.add(txtG);
		
		txtDt = new JTextField("0.05");
		txtDt.setBounds(half_way, 85, half_way - 10, 25);
		parent.add(txtDt);
		
		txtObj = new JTextField("256");
		txtObj.setBounds(half_way, 120, half_way - 10, 25);
		parent.add(txtObj);
	}
	
	@Override
	public void createSimulationGUI()
	{
		// Crea la finestra
		createWin();
		
		// Crea la pipeline
		pipeline = new RenderEngine(
				"/it/FedeWar/NBody/res/vertex.glsl",
				"/it/FedeWar/NBody/res/fragment.glsl");
		pipeline.use();
		pipeline.createProjectionMatrix(500 / 500);
		pipeline.bindProjectionMatrix();

		setCallbacks();

		initEngine();

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		glEnable(GL_POINT_SPRITE);
		glEnable(GL_VERTEX_PROGRAM_POINT_SIZE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

		// Render loop
		while (!glfwWindowShouldClose(wndHandle))
		{
			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnableClientState(GL_VERTEX_ARRAY);

			pipeline.points.bind();
			refresh();
			glDrawArrays(GL_POINTS, 0, obj_count);
			pipeline.points.unbind();

			glDisableClientState(GL_VERTEX_ARRAY);
			glfwSwapBuffers(wndHandle);
			glfwPollEvents();
		}

		// Libera le risorse
		glfwFreeCallbacks(wndHandle);
		glfwDestroyWindow(wndHandle);
		glfwTerminate();
	}

	@Override
	public void refresh()
	{
		refresh_cuda();
	}

	@Override
	public void initEngine()
	{
		// Crea l'array delle posizioni, verrà passato alla GPU
		float[] posBuffer = new float[obj_count * 4];

		// Imposta le propietà degli oggetti
		for(int i = 0; i < obj_count; i++)
		{
			posBuffer[i * 4] = (float)(Math.random() * 10);
			posBuffer[i * 4 + 1] = (float)(Math.random() * 10);
			posBuffer[i * 4 + 2] = -(float)(Math.random() * 10);
			posBuffer[i * 4 + 3] = 1.0f;
		}

		// Crea il vbo per i vertici
		pipeline.points.bind();
		int vbo_id = pipeline.points.createVBO(posBuffer, 4);
		init_cuda(g_const, dt_const, obj_count, vbo_id);
	}

	@Override
	protected void packInfo()
	{
		g_const = Float.parseFloat(txtG.getText());
		dt_const = Float.parseFloat(txtDt.getText());
		obj_count = Integer.parseInt(txtObj.getText());
	}
	
	private native void init_cuda(float g, float dt, int obj_count, int vbo);
	private native void refresh_cuda();
	
	private void createWin()
	{
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Crea la finestra e verifica che sia valida
		wndHandle = glfwCreateWindow(500, 500, name, NULL, NULL);
		if (wndHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Crea il contesto grafico e prepara la finestra
		glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(wndHandle, 200, 200);
		glfwMakeContextCurrent(wndHandle);
		glfwSwapInterval(1);
		glfwShowWindow(wndHandle);

		createCapabilities();
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
}
