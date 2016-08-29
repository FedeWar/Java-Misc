package it.FedeWar.GiocoDellaVita.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

/* Immagine i cui pixel sono di grandezza a piacere */
public class Image extends BufferedImage
{
	private int Tile;
	
	public Image(int w, int h, int c, int tile)
	{
		super(w * tile, h * tile, c);
		Tile = tile;
	}
	
	/* Disegna un elemento in posizione X,Y allineato su una griglia */
	public void drawPoint(int X, int Y, int color)
	{
		X *= Tile;
		Y *= Tile;
		
		drawPointA(X, Y, color);
	}
	
	public void drawPointA(int X, int Y, int color)
	{
		int endX = Tile + X - (X % Tile);
		int endY = Tile + Y - (Y % Tile);
		
		for(int x = X - (X % Tile); x < endX; x++)
			for(int y = Y - (Y % Tile); y < endY; y++)
				super.setRGB(x, y, color);
	}
	
	/* Riempie l'immagine di un colore uniforme */
	public void fill(Color color)
	{
		for(int x = 0; x < getWidth(); x++)
			for(int y = 0; y < getHeight(); y++)
				super.setRGB(x, y, color.getRGB());
	}
	
	public int getElementColor(int x, int y)
	{
		return getRGB(x * Tile, y * Tile);
	}
}
