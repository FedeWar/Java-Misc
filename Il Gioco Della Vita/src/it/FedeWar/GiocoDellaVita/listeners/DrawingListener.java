package it.FedeWar.GiocoDellaVita.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import it.FedeWar.GiocoDellaVita.graphics.Image;

public class DrawingListener implements MouseMotionListener
{
	private static final int[] COLOR = new int[]{0, Color.WHITE.getRGB(), Color.RED.getRGB(), Color.BLACK.getRGB()};
	private Image I;	// Immagine su cui scrivere
	private int color;	// Colore scelto
	
	public DrawingListener(Image target) { I = target; }
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		/* Sceglie il colore */
		if(SwingUtilities.isLeftMouseButton(arg0))
			color = COLOR[1];
		else if(SwingUtilities.isRightMouseButton(arg0))
			color = COLOR[3];
		else if(SwingUtilities.isMiddleMouseButton(arg0))
			color = COLOR[2];
		else return;
		/* Disegna il quadrato */
		try{
			I.drawPoint(arg0.getX() / I.Tile, arg0.getY() / I.Tile, color);
		} catch (ArrayIndexOutOfBoundsException e1)
		{
			System.out.print("Out of bounds: ");
			System.out.println(arg0.getX() / I.Tile + "," + arg0.getY() / I.Tile);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
