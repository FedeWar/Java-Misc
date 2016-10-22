package it.FedeWar.NBody.Engine.Engine_3D;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Dimension;
import java.io.InputStream;

import javax.swing.JPanel;

import org.fedewar.fog.gpu.Shader;
import org.fedewar.fog.gpu.Shader.shader_t;
import org.fedewar.fog.gui.GLFrame;
import org.fedewar.fog.objects.Camera;
import org.fedewar.fog.objects.VAO;
import org.fedewar.fog.pipeline.Program;

import com.sun.javafx.geom.Vec3d;

import it.FedeWar.NBody.Engine.Simulation;

public class Simulation_3D extends Simulation
{
	public static final String name = "Simulazione 3D - OpenGL";
	
	private Sim_Info_3D info;	// Informazioni sulla simulazione
	public G_Obj go[];			// Gli oggetti gravitazionali
	public int pnum_objs;		// Il numero di oggetti attivi
	
	private float[] posBuffer;		// Tutte le posizioni degli oggetti, per il VBO
	SimGUI g;

	private class SimGUI extends GLFrame
	{
		private Program pipeline;
		private Camera camera;
		private VAO objects;
		
		public SimGUI(String name) {
			super(name);
		}

		@Override
		public void drawCallback() {
			pipeline.setProjection();
			objects.draw();
			refresh();
		}

		@Override
		public void resizeCallback(int width, int height)
		{
			camera.setRatio(1.0f * width / height);
		}

		@Override
		public void initGLCallback()
		{
			pipeline = new Program();
			InputStream in = Shader.class.getResourceAsStream("res/vertex-particles.glsl");
			Shader vrtx = new Shader(shader_t.VERTEX, in);
			
			in = Shader.class.getResourceAsStream("res/fragment-particles.glsl");
			Shader frgm = new Shader(shader_t.FRAGMENT, in);
			
			pipeline.attach(vrtx);
			pipeline.attach(frgm);
			pipeline.link();
			pipeline.bind();
			
			camera = new Camera(1.0f, (float) (Math.PI / 2), 1, 50);
			pipeline.attach(camera);
			
			glEnable(GL_POINT_SPRITE);
			glEnable(GL_VERTEX_PROGRAM_POINT_SIZE);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
			
			objects = new VAO(VAO.POINTS);
		}
		
		public void setVBOs(float[] pos, float[] color)
		{
			objects.set_vbo(VAO.VERTEX_VBO, pos, 3);
			objects.set_vbo(VAO.COLOR_VBO, color, 3);
		}

		@Override
		public void keypressCallback(int key)
		{
			switch(key)
			{
			case GLFW_KEY_UP:
				camera.incRot(-.01f, 0);
				break;

			case GLFW_KEY_DOWN:
				camera.incRot(.01f, 0);
				break;

			case GLFW_KEY_LEFT:
				camera.incRot(0, -.01f);
				break;

			case GLFW_KEY_RIGHT:
				camera.incRot(0, .01f);
				break;

			case GLFW_KEY_W:
				camera.moveFP(.1f, .1f, 0);
				break;

			case GLFW_KEY_S:
				camera.moveFP(-.1f, -.1f, 0);
				break;
				
			case GLFW_KEY_A:
				camera.moveFP(0, 0, .1f);
				break;

			case GLFW_KEY_D:
				camera.moveFP(0, 0, -.1f);
				break;

			case GLFW_KEY_Q:
				camera.incPos(0, -.1f, 0);
				break;

			case GLFW_KEY_Z:
				camera.incPos(0, .1f, 0);
				break;
			}
		}
	}
	
	@Override
	public void initEngine()
	{
		// Crea l'array delle posizioni, verrà passato alla GPU
		posBuffer = new float[info.obj_count * 3];
		
		// Imposta le proprietà generali degli oggetti gravitazionali
		G_Obj.staticInit(this, posBuffer);

		// Crea un array di oggetti gravitazionali
		go = new G_Obj[pnum_objs = info.obj_count];

		// Crea degli alias per i nomi delle proprietà
		int dfltMass = info.standard_mass;
		int massVar = info.mass_variation;

		// Imposta le propietà degli oggetti
		for(int i = 0; i < info.obj_count; i++)
		{
			posBuffer[i * 3] = (float)(Math.random() * info.spaceDim.x);
			posBuffer[i * 3 + 1] = (float)(Math.random() * info.spaceDim.y);
			posBuffer[i * 3 + 2] = -(float)(Math.random() * info.spaceDim.z);

			go[i] = new G_Obj(
					dfltMass + (int)(Math.random() * massVar * 2 - massVar),
					i * 3);
		}
		
		// Copia i dati nel VBO.
		g.setVBOs(posBuffer, posBuffer);
	}

	@Override
	public void refresh()
	{
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updateAcc();				// Ne ricalcola l'accelerazione
		for(int i = 0; i < pnum_objs; i++)	// Per ogni oggetto
			go[i].updatePos();				// Ne ricalcola la velocità

		// Aggiorna il VBO.
		g.setVBOs(posBuffer, posBuffer);
	}

	public Sim_Info_3D getInfo() {
		return info;
	}
	
	@Override
	protected void packInfo()
	{
		info = new Sim_Info_3D();

		info.deltaT = 0.1f;
		info.G = 0.1f;
		info.mass_variation = 0;
		info.standard_mass = 1;
		info.spaceDim = new Vec3d(15.0, 15.0, 15.0);
		info.obj_count = 128;
		info.winDim = new Dimension(500, 500);
	}

	@Override
	public void createSettingsGUI(JPanel father)
	{
		super.createSettingsGUI(father);
	}

	public void createSimulationGUI()
	{
		g = new SimGUI(name);
		g.setBounds(200, 200, 500, 500);
		g.setBackground(.0f, .0f, .0f);
		g.show();
		g.initGL();

		initEngine();
		
		g.loopGL();
	}
}
