package it.FedeWar.NBody2D.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import it.FedeWar.NBody2D.Engine.Engine;
import it.FedeWar.NBody2D.Engine.Sim_Info;
import org.eclipse.wb.swt.SWTResourceManager;

public class SimulationWin
{
	private Display display;
	private Engine E;
	private Sim_Info SI;
	private Shell shell;
	private Label lblObjsCount;
	Label lblFPS;
	private int posX, posY;		// Quanto la camera si è spostata
	
	/* Costruttore, inizializzazione variabili */
	public SimulationWin(Sim_Info si, Engine e)
	{
		E = e;
		SI = si;
	}
	
	/* Listener per il disegno sulla finestra, viene chiamato ogni
	 * volta che la finestra viene modificata e ad ogni frame */
	private class Painter implements PaintListener
	{
		@Override
		public void paintControl(PaintEvent arg0)
		{
			// Disegna lo sfondo
			arg0.gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
			arg0.gc.fillRectangle(0, 0, shell.getSize().x, shell.getSize().y);

			// Disegna tutti gli oggetti, uno per uno
			arg0.gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GRAY));
			for(int i = 0; i < E.pnum_objs; i++)
				E.go[i].draw(arg0.gc, posX, posY);
			
			lblObjsCount.setText("Numero Oggetti: " + E.pnum_objs);
		}
	}
	
	/* Listener per i comandi da tastiera della shell */
	private class Keyboard implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			switch(arg0.keyCode)
			{
			case SWT.ARROW_UP:
				posY++;
				break;
			case SWT.ARROW_DOWN:
				posY--;
				break;
			case SWT.ARROW_LEFT:
				posX++;
				break;
			case SWT.ARROW_RIGHT:
				posX--;
				break;
			default:
			}
		}

		@Override public void keyReleased(KeyEvent arg0) {}
	}
	
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open()
	{
		long benchStart = 0;	// Estremi per il benchmarking
		int frames = 0;			// Frame calcolati in un secondo
		
		display = Display.getDefault();
		shell = new Shell();
		shell.setSize(SI.getProperty(Sim_Info.WIDTH), SI.getProperty(Sim_Info.HEIGHT));
		shell.setText("Simulazione");
		// Listener per disegnare sulla finestra
		shell.addPaintListener(new Painter());
		shell.addKeyListener(new Keyboard());
		
		lblObjsCount = new Label(shell, SWT.NONE);
		lblObjsCount.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblObjsCount.setAlignment(SWT.RIGHT);
		lblObjsCount.setBounds(shell.getSize().x - 260 - 16, 10, 260, 20);
		lblObjsCount.setText("Numero Oggetti: 0");
		
		Label lblTime = new Label(shell, SWT.NONE);
		lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblTime.setAlignment(SWT.RIGHT);
		lblTime.setBounds(shell.getSize().x - 260 - 16, 36, 260, 20);
		lblTime.setText("Tempo Passato: 0s");
		
		lblFPS = new Label(shell, SWT.NONE);
		lblFPS.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		lblFPS.setAlignment(SWT.RIGHT);
		lblFPS.setBounds(shell.getSize().x - 276, 62, 260, 20);
		lblFPS.setText("Frame al Secondo: 0");

		shell.open();
		shell.layout();
		
		while (!shell.isDisposed())
		{
			// Benchmark
			if(System.currentTimeMillis() - benchStart >= 1000)
			{
				lblFPS.setText("Frame al Secondo: " + frames);
				frames = 0;
				benchStart = System.currentTimeMillis();
			}
			
			// Nuovo frame
			frames++;
			E.refresh();
			shell.redraw();
			
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}
}
