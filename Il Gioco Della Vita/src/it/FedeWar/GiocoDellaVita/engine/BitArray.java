package it.FedeWar.GiocoDellaVita.engine;

public class BitArray
{
	final int sizeof = 16;	// Dimensione elemento array
	char[][] Arr;			// Array
	
	public BitArray(int Width, int Height)
	{
		Arr = new char[Width][Height / sizeof];
	}
	
	void set(int x, int y, boolean v)
	{
		if(v)
			Arr[x][y / sizeof] |= 1 << (y % sizeof);
		else
			Arr[x][y / sizeof] &= ~(1 << (y % sizeof));
	}
	
	int get(int x, int y)
	{
		return (Arr[x][y / sizeof] & 1 << (y % sizeof)) >> (y % sizeof);
	}

	public int width()
	{
		return Arr.length;
	}
	
	public int height()
	{
		return Arr[0].length * sizeof;
	}
	
	char getElement(int x, int y)
	{
		return Arr[x][y];
	}

	public int elemCount()
	{
		return Arr[0].length;
	}

	public char[] getLine(int x)
	{
		return Arr[x];
	}
}
