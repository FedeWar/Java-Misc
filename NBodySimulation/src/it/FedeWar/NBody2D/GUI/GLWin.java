package it.FedeWar.NBody2D.GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import it.FedeWar.NBody2D.Engine.CUDA.Simulation_CUDA;
import it.FedeWar.NBody2D.GUI.OpenGL.VAO;

public class GLWin implements GLEventListener
{
	GLCapabilities glCapabilities;
	GL3 gl;
	int glProgram;
	VAO vao;
	VBO vbo;

	//Shell shell;

	public GLWin(Simulation_CUDA sI)
	{
		
	}

	/**
	 * Avvia la finestra che si occupa delle simulazioni con
	 * accelerazione hardware della GPU.
	 * 
	 * @author FedeWar
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open()
	{
		// Crea il frame
		/*Display display = Display.getDefault();
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
		}*/
	}

	private FloatBuffer vertices;
	private ShortBuffer indices;
	private int VBOVertices;
	private int VBOIndices;

	/* Il contesto è stato creato, quindi si può inizializzare */
	@Override
	public void init(GLAutoDrawable arg)
	{
		/*gl = arg.getGL().getGL3();

		// Carica e comppila gli shaders
		int vertex = loadShader("shaders/vertex.glsl", GL3.GL_VERTEX_SHADER);
		int fragment = loadShader("shaders/fragment.glsl", GL3.GL_FRAGMENT_SHADER);
		
		// Linka e crea il programma
		glProgram = linkShaders(new int[] { vertex, fragment });
		gl.glUseProgram(glProgram);
		
		/*vao = new VAO(gl);
		vao.bind();
		vbo = new VBO(gl);
		vbo.bind();
		FloatBuffer f = FloatBuffer.allocate(9);
		f.array()[0] = 0.0f;
		f.array()[1] = 0.0f;
		f.array()[2] = 0.0f;
		f.array()[3] = 1.0f;
		f.array()[4] = 1.0f;
		f.array()[5] = 1.0f;
		f.array()[6] = 2.0f;
		f.array()[7] = 0.0f;
		f.array()[8] = 0.0f;
		f.flip();
		vbo.bufferData(f);
		vbo.addToVAO();
		vbo.unbind();
		vao.unbind();*/
		/*float[] vertexArray = {-0.5f,  0.5f, 0,
				0.5f,  0.5f, 0,
				0.5f, -0.5f, 0,
				-0.5f, -0.5f, 0};
		vertices = FloatBuffer.allocate(vertexArray.length);
		vertices.put(vertexArray);
		vertices.flip();

		short[] indexArray = {0, 1, 2, 0, 2, 3};
		indices = ShortBuffer.allocate(indexArray.length);
		indices.put(indexArray);
		indices.flip();

		GL gl = arg.getGL();
		int[] temp = new int[2];
		gl.glGenBuffers(2, temp, 0);

		VBOVertices = temp[0];
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, VBOVertices);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT,
				vertices, GL.GL_STATIC_DRAW);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

		VBOIndices = temp[1];
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, VBOIndices);
		gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indices.capacity() * Buffers.SIZEOF_SHORT,
				indices, GL.GL_STATIC_DRAW);
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, 0);
		System.out.println("DEBUG: inizializzazione completa.");*/
	}

	@Override
	public void display(GLAutoDrawable arg)
	{
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(1.0f, 0.0f, 0.7f, 0.7f);

		//gl.glEnableClientState(GL3.GL_VERTEX_ARRAY);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, VBOVertices);
		//gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, VBOIndices);
		gl.glDrawElements(GL.GL_TRIANGLES, indices.capacity(), GL.GL_UNSIGNED_SHORT, 0);

		//gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
		gl.glFlush();
		
		System.out.println("draw");
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
