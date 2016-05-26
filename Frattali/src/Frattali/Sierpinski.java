package Frattali;

import java.awt.Color;

import it.fractals.FractalApp;

public class Sierpinski extends Frattale //Questo frattale non usa il piano complesso
{
	public void Draw()
    {
		int lato = FractalApp.mainInstance.getHeight() / 3;
    	for(int x = 0; x < 3; x++)
    	{
    		for(int y = 0; y < 3; y++)
    		{
    			if(x == 1 && y == 1)
    			{
    				FractalApp.mainInstance.canvas.drawRect(Color.BLACK, lato * x, lato * y, lato, lato);
    				continue;
    			}
    			FractalApp.mainInstance.canvas.drawRect(Color.RED, lato * x, lato * y, lato, lato);
    			Draw(lato / 3,lato * x, lato * y);
    		}
    	}
    }
	public void Draw(int lato, int X, int Y)
    {
		if(lato==0)return;
		for(int x=0;x<3;x++)
    	{
    		for(int y=0;y<3;y++)
    		{
    			if(x==1 && y==1)
    			{
    				FractalApp.mainInstance.canvas.drawRect(Color.BLACK, X+lato*x, Y+lato*y, lato, lato);
    				continue;
    			}
    			FractalApp.mainInstance.canvas.drawRect(Color.RED, X+lato*x, Y+lato*y, lato, lato);
    			Draw(lato/3,X+lato*x,Y+lato*y);
    		}
    	}
    }
}
