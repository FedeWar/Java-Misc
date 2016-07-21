package it.FedeWar.NBody2D.GUI.OpenGL;

import java.nio.Buffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;

public class VBO
{
	int id;
	static GL3 gl;
	
	public VBO(GL3 gl)
	{
		VBO.gl = gl;
		
		IntBuffer vboid = IntBuffer.allocate(1);
		gl.glGenBuffers(1, vboid);
		id = vboid.get(0);
	}
	
	public void bufferData(Buffer data)
	{
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, data.capacity(), data, GL3.GL_STATIC_DRAW);
	}
	
	public void bind()
	{
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, id);
	}
	
	public void unbind()
	{
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	}
	
	public void addToVAO()
	{
		gl.glVertexAttribPointer(0, 4, GL3.GL_FLOAT, false, 0, 0);
	}
}
