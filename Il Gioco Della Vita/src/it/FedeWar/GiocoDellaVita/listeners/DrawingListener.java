package it.FedeWar.GiocoDellaVita.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import it.FedeWar.GiocoDellaVita.graphics.Image;

/* Cattura gli eventi generati dal movimento del mouse */
public class DrawingListener implements MouseMotionListener
{
	private static final int[] COLOR = new int[]{0, Color.WHITE.getRGB(), Color.RED.getRGB(), Color.BLACK.getRGB()};
	
	private Image targetImg;	// Immagine su cui scrivere
	private int color;			// Colore scelto
	
	public DrawingListener(Image target) { targetImg = target; }
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// Sceglie il colore secondo il pulsante
		if(SwingUtilities.isLeftMouseButton(arg0))
			color = COLOR[1];
		else if(SwingUtilities.isRightMouseButton(arg0))
			color = COLOR[3];
		else if(SwingUtilities.isMiddleMouseButton(arg0))
			color = COLOR[2];
		else return;

		// Disegna sull'immagine
		try{
			targetImg.drawPointA(arg0.getX(), arg0.getY(), color);
		} catch (ArrayIndexOutOfBoundsException e1)
		{
			System.err.print("Out of bounds: ");
			System.err.println(arg0.getX() + "," + arg0.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
