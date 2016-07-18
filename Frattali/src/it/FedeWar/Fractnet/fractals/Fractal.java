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
package it.FedeWar.Fractnet.fractals;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

/* Classe base per disegnare frattali */
public abstract class Fractal
{
	protected int Width, Height;
	protected BufferedImage Image;
	
	/* Viene chiamato dall'oggetto Class, non deve avere parametri */
	public Fractal() {}
	
	/* Inizializza l'oggetto, fa le veci del costruttore */
	public void init(int width, int height, BufferedImage i)
	{
		Width = width;
		Height = height;
		Image = i;
	}
	
	/* Disegna il frattale */
	public abstract void draw();
	
	/* Genera un animazione */
	public void animation(String path)	// TODO, NON ANCORA IMPLEMENTATO
	{
		JOptionPane.showMessageDialog(null, "Animazione non disponibile!", "Errore", JOptionPane.ERROR_MESSAGE);
	}
}
