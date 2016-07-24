package it.FedeWar.NBody2D.GUI.OpenGL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.swt.GLCanvas;

import it.FedeWar.NBody2D.Engine.Sim_Info;

public class GLWin implements GLEventListener
{
	GLCapabilities glCapabilities;
	GL3 gl;
	int glProgram;

	Shell shell;

	/**
	 * Avvia la finestra che si occupa delle simulazioni con
	 * accelerazione hardware della GPU.
	 * 
	 * @author FedeWar
	 * 
	 * @param SI Le informazioni sulla simulazione da usare
	 * @wbp.parser.entryPoint
	 */
	public void open(Sim_Info SI)
	{
		// Crea il frame
		Display display = Display.getDefault();
		shell = new Shell();
		//shell.setSize(SI.width, SI.height);
		shell.setText("Simulazione N-Corpi");

		// Crea il contesto OpenGL
		glCapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
		GLCanvas canvas = new GLCanvas(shell, SWT.NO_BACKGROUND, glCapabilities, null);
		canvas.setBounds(0, 0, shell.getSize().x, shell.getSize().y);
		canvas.addGLEventListener(this);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/* Il contesto è stato creato, quindi si può inizializzare */
	@Override
	public void init(GLAutoDrawable arg)
	{
		gl = arg.getGL().getGL3();

		// Carica e comppila gli shaders
		int vertex = loadShader("shaders/vertex.glsl", GL3.GL_VERTEX_SHADER);
		int fragment = loadShader("shaders/fragment.glsl", GL3.GL_FRAGMENT_SHADER);
		
		// Linka e crea il programma
		glProgram = linkShaders(new int[] { vertex, fragment });
		gl.glUseProgram(glProgram);
		
		VAO vao = new VAO(gl);
		vao.bind();
		VBO vbo = new VBO(gl);
		vbo.bind();
		vbo.bufferData(FloatBuffer.allocate(10));
		vbo.addToVAO();
		vbo.unbind();
		vao.unbind();
	}

	@Override
	public void display(GLAutoDrawable arg)
	{
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0)
	{

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

	/* Carica e compila il Vertex Shader */
	private int loadShader(String file, int type)
	{
		int shd = -1;	// ID del vertex shader

		try {
			shd = gl.glCreateShader(type);


			// Carica il codice sorgente
			BufferedReader br = new BufferedReader(new FileReader(file));
			String src = "";
			String line = "";

			while ((line = br.readLine()) != null)
				src += line + "\n";

			gl.glShaderSource(
					shd, 1,
					new String[] { src },
					new int[] { src.length() }, 0);

			gl.glCompileShader(shd);

			// Controlla che la compilazione sia riuscita
			IntBuffer error = IntBuffer.allocate(1);
			gl.glGetShaderiv(shd, GL3.GL_COMPILE_STATUS, error);

			if(error.get(0) == GL3.GL_FALSE)
			{
				System.err.println("Compilazione shader fallita!");
				shd = -1;
			}

			br.close();
		}
		catch(java.io.IOException e)
		{
			e.printStackTrace();
		}

		return shd;	// Restituisce l'ID dello shader
	}

	/* Crea e compila un programma dagli shader */
	private int linkShaders(int[] shds)
	{
		int program = gl.glCreateProgram();

		// Linking degli shader
		for(int i = 0; i < shds.length; i++)
			gl.glAttachShader(program, shds[i]);
		gl.glLinkProgram(program);
		gl.glValidateProgram(program);

		// Controlla che il linking abbia funzionato
		IntBuffer error = IntBuffer.allocate(1);
		gl.glGetProgramiv(program, GL3.GL_LINK_STATUS, error);
		if(error.get(0) == GL3.GL_FALSE)
		{
			System.err.println("Linking programma fallito");
			program = -1;
		}
		
		return program;
	}
}
