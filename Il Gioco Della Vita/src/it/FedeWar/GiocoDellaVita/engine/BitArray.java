package it.FedeWar.GiocoDellaVita.engine;

public class BitArray
{
	static final int sizeof = 32;	// Dimensione elemento array
	int[][] Arr;					// Array
	
	/* Costruisce un oggetto, la compressione avviene lungo height */
	public BitArray(int Width, int Height)
	{
		Arr = new int[Width][Height / sizeof];
	}
	
	/* Imposta il valore dell'elemento (x,y) con v */
	void set(int x, int y, boolean v)
	{
		if(v)
			Arr[x][y / sizeof] |= 1 << (y % sizeof);
		else
			Arr[x][y / sizeof] &= ~(1 << (y % sizeof));
	}
	
	/* Ottiene il valore in posizione (x,y) */
	int get(int x, int y)
	{
		return (Arr[x][y / sizeof] & 1 << (y % sizeof)) >> (y % sizeof);
	}

	/* Il numero di elementi lungo la larghezza */
	public int width()
	{
		return Arr.length;
	}
	
	/* Il numero di elementi lungo l'altezza */
	public int height()
	{
		return Arr[0].length * sizeof;
	}
}
