package it.FedeWar.NBody2D.Engine.Engine_3D;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.*;

import com.sun.prism.impl.BufferUtil;

public class Pipeline
{
	private int pipeline;
	private int vertex, fragment;
	Vector3f cameraPos;
	Quaternionf cameraRot;
	
	Matrix4f target = new Matrix4f();
	Matrix4f projection;
	
	/* Crea una pipeline con due shaders */
	public Pipeline(String vxPath, String fsPath)
	{
		pipeline = glCreateProgram();
		
		vertex = compileShader(vxPath, GL_VERTEX_SHADER);
		fragment = compileShader(fsPath, GL_FRAGMENT_SHADER);
		
		glAttachShader(pipeline, vertex);
		glAttachShader(pipeline, fragment);
		
		//glBindAttribLocation(pipeline, 0, "vertices");
		
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
		cameraRot = new Quaternionf(0, 0, 0);
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
				(float) (Math.PI / 1.5),
				ratio, 1, 100);
	}
	
	/* Tiene conto della camera e invia alla GPU */
	public void bindProjectionMatrix()
	{
		Matrix4f cam = new Matrix4f().translate(cameraPos).rotate(cameraRot);
		projection.mul(cam, target);
		setUniform("projection", target);
	}
}