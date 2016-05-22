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
	private static final int[] COLOR = new int[]{0, Color.WHITE.getRGB(), Color.RED.getRGB(), Color.BLACK.getRGB()};
	TimerListener TL;	// Evento chiamato dal timer
	Timer T;			// Timer per controllare i movimenti del mouse
	Image I;			// Immagine su cui scrivere
	
	public class TimerListener implements ActionListener
	{
		JFrame W;	// Frame a cui chiedere info sulla posizione
		int color;	// È stato premuto il pulsante sinistro
		
		TimerListener(JFrame w, Image i) { W = w; }	// Costruttore
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			
			try{
				I.drawPoint(
					(mouse.x - (W.getLocation().x + (W.getWidth() - W.getContentPane().getWidth()) / 2)) / I.Tile,
					(mouse.y - (W.getLocation().y + W.getHeight() - W.getContentPane().getHeight() - 9)) / I.Tile,
					color);
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
		TL.color = COLOR[arg0.getButton()];
		T.start();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		T.stop();
	}
}
