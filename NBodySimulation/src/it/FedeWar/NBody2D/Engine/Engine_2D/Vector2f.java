package it.FedeWar.NBody2D.Engine.Engine_2D;

public class Vector2f
{
	public float x;
	public float y;
	
	public Vector2f(float X, float Y)
	{
		x = X;
		y = Y;
	}
	
	/* Costruttore in forma polare */
	public Vector2f(float len, double angle)
	{
		x = (float) (Math.cos(angle) * len);
		y = (float) (Math.sin(angle) * len);
	}
	
	/* Calcola la norma del vettore */
	public float norm()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	/*public float getDst(Vector2 arg)//ottiene la distanza da un vettore intero
	{
		return (float)Math.sqrt((x-arg.x)*(x-arg.x) + (y-arg.y)*(y-arg.y));
	}*/
	
	public float getDst(Vector2f arg)//ottiene la distanza da un altro vettore reale
	{
		return (float)Math.sqrt((x - arg.x) * (x - arg.x) + (y - arg.y) * (y - arg.y));
	}
	
	public float getAngle()//ottiene l'angolo del vettore con l'angolo con l'asse X
	{
		return (float)(Math.PI/2 - Math.atan2(x, y));
	}
}
