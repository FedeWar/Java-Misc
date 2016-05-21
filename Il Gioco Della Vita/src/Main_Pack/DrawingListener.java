package Main_Pack;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import Graphics_Pack.Image;

public class DrawingListener implements MouseListener
{
	TimerListener TL;	// Evento chiamato dal timer
	Timer T;			// Timer per controllare i movimenti del mouse
	Image I;			// Immagine su cui scrivere
	
	public class TimerListener implements ActionListener
	{
		JFrame W;		// Frame a cui chiedere info sulla posizione
		boolean left;	// È stato premuto il pulsante sinistro
		
		TimerListener(JFrame w, Image i) { W = w; }	// Costruttore
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			
			try{
				I.drawElement(
					mouse.x - (W.getLocation().x + (W.getWidth() - W.getContentPane().getWidth()) / 2),
					mouse.y - (W.getLocation().y + W.getHeight() - W.getContentPane().getHeight() - 9),
					left ? Color.WHITE.getRGB() : Color.BLACK.getRGB());
			} catch (ArrayIndexOutOfBoundsException e1) {}
		}
	}
	
	public DrawingListener(JFrame w, Image i)
	{
		I = i;
		TL = new TimerListener(w, i);
		T = new Timer(1, TL);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		TL.left = arg0.getButton() == 1 ? true : false;
		T.start();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		T.stop();
	}
}
