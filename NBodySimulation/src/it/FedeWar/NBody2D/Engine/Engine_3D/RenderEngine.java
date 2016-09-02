package it.FedeWar.NBody2D.Engine.Engine_3D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import com.sun.prism.impl.BufferUtil;

public class RenderEngine
{
	// Pipeline info
	private int pipeline;
	private int vertex, fragment;
	
	// Camera info
	Vector3f cameraPos;
	float pitch = 0, yaw = 0;
	Matrix4f projection;
	
	Framebuffer f;
	
	VAO points;
	VAO screen;
	
	public class Framebuffer
	{
		private int fbo;
		private int tex;
		private int rbo;
		
		public Framebuffer()
		{
			// Framebuffer
			fbo = glGenFramebuffers();
			glBindFramebuffer(GL_FRAMEBUFFER, fbo);
			
			// Texture
			tex = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, tex);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB,
					800, 600, 0, GL_RGB,
					GL_UNSIGNED_BYTE, (ByteBuffer)null);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex, 0);
			
			// Renderbuffer
			rbo = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, rbo);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, 800, 600);
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);
			
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
		}
		
		public void bind() {
			glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		}
		
		public void unbind() {
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
		}
		
		public void dispose() {
			glDeleteFramebuffers(fbo);
			fbo = 0;
			
			glDeleteRenderbuffers(rbo);
			rbo = 0;
		}

		public int getTex() {
			return tex;
		}
	}
	
	public class VAO
	{
		int vao;
		int index_used = 0;
		
		public VAO() {
			vao = glGenVertexArrays();
		}
		
		public void bind() {
			glBindVertexArray(vao);
		}
		
		public void unbind() {
			glBindVertexArray(0);
		}
		
		public int createVBO(float[] data, int element_size)
		{
			int vbo_id = glGenBuffers();
			FloatBuffer databuffer = BufferUtils.createFloatBuffer(data.length);
			databuffer.put(data);
			databuffer.flip();
			
			glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
			glBufferData(GL_ARRAY_BUFFER, databuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(index_used, element_size, GL_FLOAT, false, 0, 0);
			glEnableVertexAttribArray(index_used++);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			return vbo_id;
		}
	}
	
	public int getProg() {
		return pipeline;
	}
	
	/* Crea una pipeline con due shaders */
	public RenderEngine(String vxPath, String fsPath)
	{
		pipeline = glCreateProgram();
		
		vertex = compileShader(vxPath, GL_VERTEX_SHADER);
		fragment = compileShader(fsPath, GL_FRAGMENT_SHADER);
		
		glAttachShader(pipeline, vertex);
		glAttachShader(pipeline, fragment);
		
		//glBindAttribLocation(pipeline, 0, "vertices");
		glBindAttribLocation(pipeline, 0, "vertex");
		glBindAttribLocation(pipeline, 1, "texCoordIn");
		
		// Link e controllo errori
		glLinkProgram(pipeline);
		if(glGetProgrami(pipeline, GL_LINK_STATUS) != GL_TRUE)
		{
			System.err.println(glGetProgramInfoLog(pipeline));
			System.exit(-1);
		}
		
		// Validate e controllo errori
		glValidateProgram(pipeline);
		if(glGetProgrami(pipeline, GL_VALIDATE_STATUS) != GL_TRUE)
		{
			System.err.println(glGetProgramInfoLog(pipeline));
			System.exit(-1);
		}
		
		cameraPos = new Vector3f(0, 0, 0);
		
		points = new VAO();
		screen = new VAO();
	}
	
	public void setUniform(String name, float value)
	{
		int ptr = glGetUniformLocation(pipeline, name);
		if(ptr != -1)
			glUniform1f(ptr, value);
	}
	
	public void setUniform(String name, Matrix4f value)
	{
		int ptr = glGetUniformLocation(pipeline, name);
		if(ptr != -1)
		{
			FloatBuffer mat = BufferUtil.newFloatBuffer(16);
			value.get(mat);
			glUniformMatrix4fv(ptr, false, mat);
		}
	}
	
	public void use() {
		glUseProgram(pipeline);
	}

	/* Carica e compila lo shader contenuto in 'file' */
	private int compileShader(String file, int type)
	{
		// Dice a OpengìGL di creare uno shader vuoto
		int shader = glCreateShader(type);
		String source = "";
		
		try {
			// Apre il file per la lettura
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			// Carica tutto il file in 'source'
			while((line = br.readLine()) != null)
				source += line + '\n';
			
			br.close();
		} catch(IOException e) {
			System.err.println("Errore " + e.getMessage());
			System.exit(-1);
		}
		
		// Compila lo shader e controlla che gli errori
		glShaderSource(shader, source);
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE)
		{
			System.err.println(glGetShaderInfoLog(shader));
			System.exit(-1);
		}
		
		return shader;
	}
	
	/* Crea la matrice per la proiezione */
	public void createProjectionMatrix(float ratio)
	{
		projection = new Matrix4f().setPerspective(
				(float) (Math.PI / 2),
				ratio, 0.1f, 100);
	}
	
	/* Tiene conto della camera e invia alla GPU */
	public void bindProjectionMatrix()
	{
		use();
		Matrix4f cam = new Matrix4f(projection);
		cam.rotate(pitch, 1, 0, 0);
		cam.rotate(yaw, 0, 1, 0);
		cam.translate(cameraPos, cam);
		setUniform("projection", cam);
	}

	public void createFBO()
	{
		f = new Framebuffer();
	}
}