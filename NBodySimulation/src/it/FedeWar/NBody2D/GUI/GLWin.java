package it.FedeWar.NBody2D.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.swt.GLCanvas;

public class GLWin implements GLEventListener
{
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open()
	{
		GLProfile profile = GLProfile.get(GLProfile.GL3);
		GLCapabilities capabilities = new GLCapabilities(profile);
		
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		GLCanvas canvas = new GLCanvas(shell, SWT.NO_BACKGROUND, capabilities, null);
		canvas.setBounds(0, 0, 200, 200);
		canvas.addGLEventListener(this);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	@Override
	public void display(GLAutoDrawable arg)
	{
		GL3 gl = arg.getGL().getGL3();
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		
	}

}
