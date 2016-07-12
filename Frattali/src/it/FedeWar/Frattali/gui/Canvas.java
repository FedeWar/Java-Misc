package it.FedeWar.Frattali.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import it.FedeWar.Frattali.PluginManager;
import it.FedeWar.Frattali.fractals.Frattale;

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