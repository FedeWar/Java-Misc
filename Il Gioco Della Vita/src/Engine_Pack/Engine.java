package Engine_Pack;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Graphics_Pack.Image;

public class Engine
{
	private final int[] COLORS = new int[]{Color.WHITE.getRGB(), Color.BLACK.getRGB()};
	BitArray[] Mat;
	int currBuf = 0;
	int otherBuf = 1;
	Image I;
	
	public Engine(Image i)
	{
		I = i;
		
		// Crea i due array
		Mat = new BitArray[] {
				new BitArray(I.getWidth() / I.Tile, I.getHeight() / I.Tile),
				new BitArray(I.getWidth() / I.Tile, I.getHeight() / I.Tile)};
		
		// Inizializza il primo
		for(int x = 0; x < Mat[0].width(); x++)
			for(int y = 0; y < Mat[0].height(); y++)
				Mat[0].set(x, y, I.getElementColor(x, y) == COLORS[0]);
	}

	public void Refresh()
	{
		for(int X = 0; X < Mat[0].width(); X++)		// Itera su tutti gli elementi
		{
			for(int Y = 0; Y < Mat[0].height(); Y++)
			{
				int count = 0;						// Quanti vicini sono vivi
				int startx = (X == 0) ? 0 : X - 1;	// Da dove parte, non deve strabordare
				int starty = (Y == 0) ? 0 : Y - 1;
				int endx = (X == Mat[0].width() - 1) ? X : (X + 1);	// Dove interrompe
				int endy = (Y == Mat[0].height() - 1) ? Y : (Y + 1);
				
				for(int x = startx; x <= endx; x++)		// Itera sugli 8 elementi vicini
				{
					for(int y = starty; y <= endy; y++)
					{
						if(x == X && x == y) continue;	// Non deve contare se stesso
						count += Mat[currBuf].get(x, y);// Somma il valore del vicino
					}
				}
				
				boolean lives = count > 2 && count < 5;		// Vive o muore?
				Mat[otherBuf].set(X, Y, lives);				// Lo imposta come deve
				I.drawElement(X * I.Tile, Y * I.Tile, COLORS[lives ? 0 : 1]);	// Disegna
			}
		}
		
		// Buffer swapping
		int temp = currBuf;
		currBuf = otherBuf;
		otherBuf = temp;
	}
	
	public void exportTo(String path)	// Non terminato
	{
		BufferedWriter BW;
		
		try
		{
			BW = new BufferedWriter(new FileWriter(new File(path)));
			
			BW.write(Mat[currBuf].width() + " " + Mat[currBuf].height() + "\n");
			for(int x = 0; x < Mat[currBuf].elemCount(); x++)
				BW.write(Mat[currBuf].getLine(x));
			
			BW.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
