package it.FedeWar.GiocoDellaVita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import it.FedeWar.GiocoDellaVita.engine.Engine;
import it.FedeWar.GiocoDellaVita.graphics.Window;

public class Applicazione extends JFrame
{
	private static final long serialVersionUID = 7412448245603113878L;

	public class TimerListener implements ActionListener
	{
		Applicazione A;
		TimerListener(Applicazione a) { A = a; }
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(A.isRunning)
				A.E.Refresh();
			A.repaint();
		}
	}
	
	public boolean isRunning = false;
	
	private Window win;
	private Engine E;
	private Timer T;
	
	public Applicazione()
	{
		super("Il Gioco Della Vita");
		win = new Window(this);
		add(win);
		pack();
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		T = new Timer(100, new TimerListener(this));
	}

	public static void main(String[] args) throws InterruptedException
	{
		new Applicazione();
	}
	
	public void runDrawing()
	{
		E = win.runDrawing();
		T.start();
	}

	public void runSimulation()
	{
		E.Init(win.canvas);
		win.runSimulation();
		isRunning = true;
	}

	public void stopSimulation()
	{
		win.stopSimulation();
		isRunning = false;
	}

	public void exportTo(String path)
	{
		E.Init(win.canvas);
		E.exportTo(path);
	}
	
	public void importFrom(String path)
	{
		E.importFrom(path, win.canvas);
	}
}
