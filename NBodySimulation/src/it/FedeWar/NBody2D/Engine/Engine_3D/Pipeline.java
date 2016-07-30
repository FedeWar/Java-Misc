package it.FedeWar.NBody2D.Engine.Engine_3D;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pipeline
{
	private int pipeline;
	private int vertex, fragment;
	
	public Pipeline(String vxPath, String fsPath)
	{
		pipeline = glCreateProgram();
		
		vertex = compileShader(vxPath, GL_VERTEX_SHADER);
		fragment = compileShader(fsPath, GL_FRAGMENT_SHADER);
		
		glAttachShader(pipeline, vertex);
		glAttachShader(pipeline, fragment);
		
		glBindAttribLocation(pipeline, 0, "vertices");
		
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
	}
	
	public void setUniform(String name, float value)
	{
		int ptr = glGetUniformLocation(pipeline, name);
		if(ptr != -1)
			glUniform1f(ptr, value);
	}
	
	public void use() {
		glUseProgram(pipeline);
	}

	private int compileShader(String file, int type)
	{
		int shader = glCreateShader(type);
		String source = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = br.readLine()) != null)
				source += line + '\n';
			
			br.close();
		} catch(IOException e) {
			
		}
		
		glShaderSource(shader, source);
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE)
		{
			System.err.println(glGetShaderInfoLog(shader));
			System.exit(-1);
		}
		
		return shader;
	}
}