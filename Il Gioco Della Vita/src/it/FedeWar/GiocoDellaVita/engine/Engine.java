package it.FedeWar.GiocoDellaVita.engine;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import it.FedeWar.GiocoDellaVita.graphics.Image;

public class Engine
{
	private final int[] COLORS = new int[]{Color.WHITE.getRGB(), Color.BLACK.getRGB(), Color.RED.getRGB()};
	private BitArray[] Mat;
	private int currBuf = 0;
	private int otherBuf = 1;
	private Image I;
	public boolean initialized = false;
	
	public Engine(int W, int H)
	{
		// Crea i due array
		Mat = new BitArray[] { new BitArray(W, H), new BitArray(W, H)};
	}

	/* Trasforma l'immagine passata come argomento in una matrice */
	public void Init(Image i)
	{
		I = i;
		initialized = true;
		
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
				if(I.getElementColor(X, Y) == COLORS[2])
					continue;
				int count = 0;						// Quanti vicini sono vivi
				int startx = (X == 0) ? 0 : X - 1;	// Da dove parte, non deve strabordare
				int starty = (Y == 0) ? 0 : Y - 1;
				int endx = (X == Mat[0].width() - 1) ? X : (X + 1);	// Dove interrompe
				int endy = (Y == Mat[0].height() - 1) ? Y : (Y + 1);
				
				for(int x = startx; x <= endx; x++)			// Itera sugli 8 elementi vicini
					for(int y = starty; y <= endy; y++)
						if(x != X || x != y)				// Non deve contare se stesso
							count += Mat[currBuf].get(x, y);// Somma il valore del vicino
				
				boolean lives = count > 2 && count < 5;		// Vive o muore?
				Mat[otherBuf].set(X, Y, lives);				// Lo imposta come deve
				I.drawPoint(X, Y, COLORS[lives ? 0 : 1]);	// Disegna
			}
		}
		
		// Buffer swapping
		int temp = currBuf;
		currBuf = otherBuf;
		otherBuf = temp;
	}
	
	public void importFrom(String path, Image i)
	{
		I = i;
		boolean val;
		
		try{
			BufferedReader BR = new BufferedReader(new FileReader(new File(path)));
			
			int W = readInt(BR);
			int H = readInt(BR);
			Mat = new BitArray[] { new BitArray(W, H), new BitArray(W, H)};
			
			for(int X = 0; X < Mat[currBuf].width(); X++)	// Itera sulla matrice
			{
				for(int Y = 0; Y < Mat[currBuf].height(); Y++)
				{
					val = BR.read() == '1';					// Verifica se � un uno
					I.drawPoint(X, Y, COLORS[val ? 0 : 1]);	// Lo disegna
				}
			}
			BR.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/* Salva i dati correnti nel file specificato in path */
	public void exportTo(String path)
	{
		BufferedWriter BW;
		try{
			// Apre il file e ne scrive l'header nella forma: "W H "
			BW = new BufferedWriter(new FileWriter(new File(path)));
			BW.write(Mat[currBuf].width() + " " + Mat[currBuf].height() + " ");
			
			for(int X = 0; X < Mat[currBuf].width(); X++)	// Itera sull'array
				for(int Y = 0; Y < Mat[currBuf].height(); Y++)
					BW.write(Mat[currBuf].get(X, Y) + '0');// Scrive carattere per carattere
			BW.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/* Legge dal buffer un numero intero, deve terminare con uno spazio */
	private int readInt(BufferedReader BR) throws IOException
	{
		String buf = new String();	// Buffer
		char c = 0;					// Ultimo carattere letto
		
		/* Legge carattere per carattere fino allo spazio */
		while((c = (char) BR.read()) != ' ')
			buf += c;	// Concatena il carattere al buffer
		
		return Integer.parseInt(buf);	// Restituisce dopo il parsing
	}
}
