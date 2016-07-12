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

import javax.swing.JPanel;

import it.FedeWar.Fractnet.PluginManager;
import it.FedeWar.Fractnet.fractals.Frattale;

/* Pannello sui cui disegnare */
class Canvas extends JPanel
{
	private static final long serialVersionUID = -6573971607519471731L;
	
	private Frattale currFract;
	private int oldSelection = -1;
	
	/* Crea un nuovo frattale da visualizzare */
	public void newFract(String name)
	{
		// Ottiene l'id del frattale selezionato nella lista
		int currentSelection = PluginManager.search(name);
		
		// Evita di disegnare più del necessario
		if(currentSelection != oldSelection)
		{
			oldSelection = currentSelection;	// Cambia la vecchia selezione
			currFract = PluginManager.create(currentSelection, getWidth(), getHeight());
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
			g2.drawImage(currFract.getImage(), null, null);
		}
	}
}