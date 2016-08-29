/* Copyright 2016 Federico Guerra aka FedeWar
	
	This file is part of Fractnet.

	Fractnet is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Fractnet is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Fractnet.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.FedeWar.Fractnet.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import it.FedeWar.Fractnet.PluginManager;
import it.FedeWar.Fractnet.fractals.Fractal;
import it.FedeWar.Fractnet.fractals.plugins.Julia;
import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.math.Complex;

/* Pannello sui cui disegnare il frattale */
class Canvas extends JPanel
{
	private static final long serialVersionUID = -6573971607519471731L;
	
	/* Grafica */
	private BufferedImage	fractalImage;
	private Fractal			currFract;
	
	/* Zoom e traslazione */
	private double			newClipWidth = 2.0;
	private double[]		newClipPos;
	
	/* Estremi per decidere se ridisegnare il frattale */
	private int				oldSelection = -1;
	private String			oldArg = "0;0";
	private boolean			refreshFractal = true;
	
	private class ClickListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg)
		{
			Julia J = (Julia) currFract;
			Complex p = new Complex(0, 0);
			J.toFractalCoordinates(arg.getPoint().x, arg.getPoint().y, p);
			System.out.println(p);
		}

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	public Canvas(int width, int height)
	{
		setSize(width, height);
		fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		newClipPos = new double[] {1, 1};
		addMouseListener(new ClickListener());
	}
	
	/* Crea un nuovo frattale da visualizzare */
	public void newFract(String name, String arg)
	{
		// Ottiene l'id del frattale selezionato nella lista
		int currentSelection = PluginManager.search(name);
		
		// Se il frattale selezionato è cambiato
		if(currentSelection != oldSelection)
		{
			oldSelection = currentSelection;
			
			// Alloca e inizializza il nuovo frattale
			currFract = PluginManager.create(currentSelection);
			currFract.init(fractalImage);
			
			// Imposta i parametri per i frattali complessi
			if(currFract instanceof ComplexFract)
			{
				((ComplexFract)currFract).setC(Complex.Parse(arg));
				newClipWidth = 1.0;
				newClipPos = new double[] {1, 1};
			}
		}
		
		// Se l'argomento è cambiato e il frattale è complesso
		else if(arg.compareTo(oldArg) != 0 && currFract instanceof ComplexFract)
		{
			oldArg = arg;
			((ComplexFract)currFract).setC(Complex.Parse(arg));
		}
		
		// Al prossimo refresh ridisegna il frattale
		refreshFractal = true;
	}
	
	/* Ridisegna il pannello */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Ridisegna il frattale
		if(refreshFractal && currFract != null)
		{
			currFract.draw();
			g2.drawImage(fractalImage, null, null);
			
			// Azzera lo zoom corrente
			if(currFract instanceof ComplexFract)
			{
				newClipWidth = ((ComplexFract) currFract).getClipWidth();
				newClipPos[0] = 0;
				newClipPos[1] = 0;
			}
		}
		
		// Non ridisegna il frattale ma solo il rettangolo dello zoom
		else if(!refreshFractal)
		{
			g2.drawImage(fractalImage, null, null);
			
			// Se supporta lo zoom, disegna i bordi dello zoom
			if(currFract instanceof ComplexFract)
				genBounds(g2);
		}
	}

	/* Calcola il bordo dello zoom,
	 * non controla che il frattale sia complesso */
	public void genBounds(Graphics2D g2)
	{
		double clipWidth = ((ComplexFract) currFract).getClipWidth();
		g2.setColor(Color.WHITE);			// Rettangolo bianco

		// Lo zoom sta aumentando, calcola il nuovo bordo
		if(newClipWidth < clipWidth)
		{
			double rapp = newClipWidth / clipWidth;	// Rapporto vecchio e nuovo zoom
			double W = rapp * getWidth();			// La larghezza del rettangolo
			double H = rapp * getHeight();			// L'altezza del rettangolo
			
			g2.drawRect(							// Disegna il rettangolo
				(int)(getWidth() / 2 - newClipPos[0] * getWidth() / clipWidth - W / 2),
				(int)(getHeight() / 2 - newClipPos[1] * getHeight() / clipWidth - H / 2),
				(int)(W), (int)(H));
		}
		// L'immagine sta venendo rimpicciolita,
		// non si può disegnare il bordo
		else
			g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	/* Applica le modifiche fatte dall'utente:
	 * aggiorna lo zoom e la traslazione */
	public void apply()
	{
		refreshFractal = true;
		
		if(currFract instanceof ComplexFract)
		{
			((ComplexFract) currFract).setClipWidth(newClipWidth);
			((ComplexFract) currFract).addTrasl(newClipPos);
		}
	}
	
	/* Aumenta lo spostamento di X e Y unità sugli assi,
	 * le unità di movimento variano con lo zoom */
	public void incTrasl(double X, double Y)
	{
		if(currFract instanceof ComplexFract)
		{
			newClipPos[0] += X / 2.0 * newClipWidth;
			newClipPos[1] += Y / 2.0 * newClipWidth;
			refreshFractal = false;
		}
	}
	
	/* Setter per lo zoom */
	public void setZoom(double newZoom)
	{
		newClipWidth = newZoom;
		refreshFractal = false;
		System.out.println(newZoom);
	}
	
	/* Getter per lo zoom */
	public double getZoom()
	{
		return newClipWidth;
	}
}