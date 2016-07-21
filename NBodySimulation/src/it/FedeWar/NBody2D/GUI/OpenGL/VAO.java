package it.FedeWar.NBody2D.GUI.OpenGL;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;

public class VAO
{
	int id;
	static GL3 gl;
	
	public VAO(GL3 gl)
	{
		VAO.gl = gl;
		
		IntBuffer vaos = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, vaos);
		id = vaos.get(0);
	}
	
	public void bind()
	{
		gl.glBindVertexArray(id);
	}
	
	public void unbind()
	{
		gl.glBindVertexArray(0);
	}
}
