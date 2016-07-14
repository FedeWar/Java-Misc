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

import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
	private BufferedImage fractalImage;
	private Fractal currFract;
	private int oldSelection = -1;
	private String oldArg = "0;0";
	
	public Canvas(int width, int height)
	{
		super();
		setSize(width, height);
		fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
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
			currFract = PluginManager.create(currentSelection, getWidth(), getHeight());
			currFract.init(getWidth(), getHeight(), fractalImage);
			
			if(currFract instanceof ComplexFract)
				((ComplexFract)currFract).setC(Complex.Parse(arg));
		}
		// Se l'argomento è cambiato e il frattale è complesso
		else if(arg.compareTo(oldArg) != 0 && currFract instanceof ComplexFract)
		{
			oldArg = arg;
			((ComplexFract)currFract).setC(Complex.Parse(arg));
		}
	}
	
	/* Ridisegna il pannello */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(currFract != null)
		{
			currFract.Draw();
			g2.drawImage(fractalImage, null, null);
		}
	}
}