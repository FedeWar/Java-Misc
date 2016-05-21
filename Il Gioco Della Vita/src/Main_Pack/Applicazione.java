package Main_Pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import Engine_Pack.Engine;
import Graphics_Pack.Window;

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
		win.runDrawing();
		T.start();
	}

	public void runSimulation()
	{
		win.runSimulation();
		E = new Engine(win.canvas);
		// E.exportTo("C:\\Users\\Federico\\Desktop\\D.txt");	// Prova
		isRunning = true;
	}

	public void stopSimulation()
	{
		win.stopSimulation();
		isRunning = false;
		E = null;
	}
}
