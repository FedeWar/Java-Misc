package it.tupper.Listeners;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.tupper.Tupper;

/* Listener per il timer che si occupa di disegnare sul canvas
 * con il mouse ogni 10ms e ogni 100ms di ridisegnare tutta la GUI */
public class TmrListener implements ActionListener
{
	Tupper A;
	
	public TmrListener(Tupper a)
	{
		A = a;
	}

	int counts = 0;	// Incrementato ogni 10 ms, serve per il refresh ogni 100 ms
	@Override public void actionPerformed(ActionEvent arg0)
	{
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		
		A.I.drawPoint(	// Allinea e disegna i punti su cui passa il mouse
			(mouse.x - (A.frame.getLocation().x + (A.frame.getWidth() - A.frame.getContentPane().getWidth()) / 2)) / A.I.Tile,
			(mouse.y - (A.frame.getLocation().y + A.frame.getHeight() - A.frame.getContentPane().getHeight() - 9)) / A.I.Tile,
			A.color);
		
		if(counts++ == 10)		// Incrementa ogni 10ms per 10 volte
		{
			counts = 0;			// Azzera
			A.frame.repaint();	// Ridisegna tutta la GUI
		}
	}

}
