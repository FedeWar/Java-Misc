package it.life.graphics;
//migliorare la classe, renderla più elastica
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Image extends BufferedImage
{
	public int Tile;
	
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
