package it.FedeWar.Frattali.graphics;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

/* Classe base per disegnare frattali */
public abstract class Frattale
{
	protected int Width, Height;
	protected BufferedImage Image;
	
	public Frattale() {}	// Non deve prendere parametri
	
	public void Init(int width, int height)
	{
		Width = width;
		Height = height;
		Image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	public void Draw() {}
	
	public void setC(double r, double i) {}
	
	public BufferedImage getImage()
	{
		return Image;
	}
	
	public void animation(String path)
	{
		JOptionPane.showMessageDialog(null, "Animazione non disponibile!", "Errore", JOptionPane.ERROR_MESSAGE);
	}
}
