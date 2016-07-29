package it.FedeWar.NBody2D.GUI;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import it.FedeWar.NBody2D.Engine.CUDA.Simulation_CUDA;

public class GLWin
{
	private long window;
	int WIDTH = 480;
	int HEIGHT = 360;
	Simulation_CUDA sim;

	public GLWin(Simulation_CUDA cuda)
	{
		sim = cuda;
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
		});

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
				window,
				(vidmode.width() - WIDTH) / 2,
				(vidmode.height() - HEIGHT) / 2
				);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public void open()
	{
		GL.createCapabilities();				// Crea un glCtx
		sim.initEngine();						// Crea l'engine
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);	// Colore di sfondo
		
		while (!glfwWindowShouldClose(window))
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			sim.render();
//			glBegin(GL_TRIANGLES);
//			glColor3f(1.0f, 0.0f, 0.0f);
//			glVertex2f(0.200f, 0.100f);
//			glVertex2f(0.300f, 0.400f);
//			glVertex2f(0.100f, 0.400f);
//			glEnd();

			glfwSwapBuffers(window);	// Swap buffers
			glfwPollEvents();			// Controlla gli eventi
		}
	}

	public void dispose()
	{
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}