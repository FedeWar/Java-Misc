package Frattali;

import javax.swing.JOptionPane;

/* Classe base per disegnare frattali */
public abstract class Frattale
{
	int Width, Height;
	
	public Frattale() {}
	
	public void Init(int width, int height)
	{
		Width = width;
		Height = height;
	}
	
	public void Draw() {}
	
	public void setC(double r, double i) {}
	
	public void animation(String path)
	{
		JOptionPane.showMessageDialog(null, "Animazione non disponibile!", "Errore", JOptionPane.ERROR_MESSAGE);
	}
}
