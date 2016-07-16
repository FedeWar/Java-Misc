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
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import it.FedeWar.Fractnet.PluginManager;
import it.FedeWar.Fractnet.fractals.Fractal;
import it.FedeWar.Fractnet.fractals.ComplexFract;
import it.FedeWar.Fractnet.math.Complex;

/* Pannello sui cui disegnare */
class Canvas extends JPanel
{
	private static final long serialVersionUID = -6573971607519471731L;
	
	private Rectangle		zoomBounds;
	private double			zoomVal = 1.0;
	
	private BufferedImage	fractalImage;
	private Fractal			currFract;
	private int				oldSelection = -1;
	private String			oldArg = "0;0";
	private boolean			refreshFractal = true;
	
	public Canvas(int width, int height)
	{
		super();
		setSize(width, height);
		fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		zoomBounds = new Rectangle(0, 0, 0, 0);
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
			currFract.init(getWidth(), getHeight(), fractalImage);
			
			// Imposta i parametri per i frattali complessi
			if(currFract instanceof ComplexFract)
			{
				((ComplexFract)currFract).setC(Complex.Parse(arg));
				zoomVal = 1.0;
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
				zoomVal = ((ComplexFract) currFract).getZoom();
		}
		
		// Non ridisegna il frattale ma solo il rettangolo dello zoom
		else if(!refreshFractal)
		{
			g2.drawImage(fractalImage, null, null);
			
			// Se supporta lo zoom, calcola i bordi dello zoom
			if(currFract instanceof ComplexFract)
			{
				g2.setColor(Color.WHITE);
				genBounds();
				g2.drawRect(zoomBounds.x, zoomBounds.y, zoomBounds.width, zoomBounds.height);
			}
		}
	}

	/* Calcola il bordo dello zoom,
	 * non controla che il frattale sia complesso */
	public void genBounds()
	{
		double fractZoom = ((ComplexFract) currFract).getZoom();

		// Lo zoom sta aumentando, calcola il nuovo bordo
		if(zoomVal > fractZoom)
		{
			double rapp = fractZoom / zoomVal;	// Rapporto vecchio e nuovo zoom
			double W = rapp * getWidth();		// La larghezza del rettangolo
			double H = rapp * getHeight();		// L'altezza del rettangolo
			
			zoomBounds.setBounds(
				(int)(getWidth() / 2.0 - W / 2),
				(int)(getHeight() / 2.0 - H / 2),
				(int)(W), (int)(H));
		}

		// L'immagine sta venendo rimpicciolita,
		// non si può disegnare il bordo
		else
			zoomBounds.setBounds(0, 0, getWidth(), getHeight());
	}

	/* Applica le modifiche fatte dall'utente */
	public void apply()
	{
		refreshFractal = true;	// Al refresh ridisegna il frattale
		
		// Aggiorna lo zoom del frattale
		if(currFract instanceof ComplexFract)
			((ComplexFract) currFract).setZoom(zoomVal);
		
		// TODO apply trasl too
	}
	
	/* Setter per lo zoom */
	public void setZoom(double newZoom)
	{
		zoomVal = newZoom;
		refreshFractal = false;
	}
	
	/* Getter per lo zoom */
	public double getZoom()
	{
		return zoomVal;
	}
}