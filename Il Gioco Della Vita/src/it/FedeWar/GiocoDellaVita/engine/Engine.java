package it.FedeWar.GiocoDellaVita.engine;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import it.FedeWar.GiocoDellaVita.graphics.Image;

/* Gestisce l'evolversi del gioco */
public class Engine
{
	private final int[] COLORS = new int[]{Color.WHITE.getRGB(), Color.BLACK.getRGB(), Color.RED.getRGB()};
	
	private BitArray[]	Mat;		// Le due matrici
	private int			currBuf;	// Il buffer attualmente in uso
	private int			otherBuf;	// L'altro buffer
	private Image		targetImg;	// L'immagine su cui disegnare
	
	/* Costruittore */
	public Engine(int W, int H)
	{
		currBuf = 0;
		otherBuf = 1;
		Mat = new BitArray[] { new BitArray(W, H), new BitArray(W, H)};
	}

	/* Trasforma l'immagine passata come argomento in una matrice */
	public void Init(Image i)
	{
		targetImg = i;
		
		for(int x = 0; x < Mat[0].width(); x++)
			for(int y = 0; y < Mat[0].height(); y++)
				Mat[0].set(x, y, targetImg.getElementColor(x, y) == COLORS[0]);
	}

	public void Refresh()
	{
		// Itera su tutti gli elementi della matrice
		for(int X = 0; X < Mat[0].width(); X++)
		{
			for(int Y = 0; Y < Mat[0].height(); Y++)
			{
				// Ignora gli elementi di colore rosso
				if(targetImg.getElementColor(X, Y) == COLORS[2])
					continue;
				
				int count = 0;	// Quanti vicini sono vivi
				
				// Da dove cominciare a calcolare
				int startx = (X == 0) ? 0 : X - 1;
				int starty = (Y == 0) ? 0 : Y - 1;
				
				// Dove interrompere
				int endx = (X == Mat[0].width() - 1) ? X : (X + 1);
				int endy = (Y == Mat[0].height() - 1) ? Y : (Y + 1);
				
				// Itera sugli elementi vicini (max 8)
				for(int x = startx; x <= endx; x++)
					for(int y = starty; y <= endy; y++)
					{
						// Deve ignorare se stesso
						if(x != X || x != y)
							count += Mat[currBuf].get(x, y);// Somma il valore del vicino
					}
				
				boolean lives = count > 2 && count < 5;				// Vive o muore?
				Mat[otherBuf].set(X, Y, lives);						// Lo imposta nell'altro buffer
				targetImg.drawPoint(X, Y, COLORS[lives ? 0 : 1]);	// Disegna
			}
		}
		
		// Buffer swapping
		int temp = currBuf;
		currBuf = otherBuf;
		otherBuf = temp;
	}
	
	public void importFrom(String path, Image i)
	{
		targetImg = i;
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
					targetImg.drawPoint(X, Y, COLORS[val ? 0 : 1]);	// Lo disegna
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
