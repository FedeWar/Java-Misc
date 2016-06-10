package it.TS.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import it.TS.Math.Wave;
import it.TS.Utils.Parser;

/* Il pannello su cui vengono disegnate le onde */
public class GraphPanel extends JPanel
{
	private static final char[] AXIS = new char[] {'X', 'Y'};
	private static final long serialVersionUID = -7683048415031690008L;
	
	private boolean			drawSum = true;
	private float			zoomX, zoomY;
	private	ArrayList<Wave> Waves;

	public GraphPanel(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		zoomX = (float) (2.0f / width * Math.PI);
		zoomY = (float) (2.0f / height);
		Waves = new ArrayList<Wave>();
		Waves.add(new Wave());
	}
	
	/* I controlli sono stati modificati, quindi bisogna
	 * sostituire l'onda i cui parametri sono cambiati */
	public void setWave(int id, Wave W)
	{
		Waves.set(id, W);
	}
	
	public Wave getWave(int id)
	{
		return Waves.get(id);
	}
	
	public void addWave(Wave wave)
	{
		Waves.add(wave);
	}
	
	public void removeWave(int index)
	{
		Waves.remove(index);
	}
	
	public int wavesCount()
	{
		return Waves.size();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		/* Disegna solo le onde evidenziate */
		for(int i = 0; i < Waves.size(); i++)
			if(Waves.get(i).isShown)
				drawWave(i, g2);
		
		/* Disegna gli assi di riferimento */
		g2.setColor(Color.BLACK);
		g2.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2.drawChars(AXIS, 1, 1, getWidth() / 2 - 10, 10);
		g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
		g2.drawChars(AXIS, 0, 1, getWidth() - 10, getHeight() / 2 - 10);
		
		/* Calcola e disegna la somma di tutte le onde */
		if(drawSum) drawSum(g2);
	}
	
	public void setSum(boolean val)
	{
		drawSum = val;
	}

	public void setZoom(float val)
	{
		zoomX = (float) (2.0f / getWidth() * Math.PI) * val;
		zoomY = (float) (2.0f / getHeight()) * val;
	}

	public String getFunction()
	{
		String function = Waves.get(0).toString();
		
		for(int i = 1; i < Waves.size(); i++)
			function += " + " + Waves.get(i).toString();
		
		return function;
	}

	public void Parse(String function)
	{
		function = function.replaceAll(" ", "");
		int s = function.indexOf('s');
		Waves.clear();
		
		while(s != -1)
		{
			int start = function.lastIndexOf('+', s);
			start = start < 0 ? 0 : start;
			
			int len = function.indexOf('+', function.indexOf(')', start));
			len = len < 0 ? function.length() : len;
			
			Parser P = new Parser(function.substring(start, len));
			Waves.add(P.parseWave());
			s = function.indexOf('s', s + 1);
		}
	}
	
	private void drawSum(Graphics2D g2)
	{
		int sommaPunti = 0, oldSommaPunti = 0;
		g2.setColor(Color.black);
		
		/* Il primo va calcolato separatamente */
		oldSommaPunti = graphToScreen(getSumPoints(screenToGraph(0)));
		
		/* Per ogni punto dello schermo */
		for(int i = 1; i < getWidth(); i++)
		{
			/* Calcola la funzione in quel punto */
			sommaPunti = graphToScreen(getSumPoints(screenToGraph(i)));
			/* Disegna la linea */
			g2.drawLine(
				i, oldSommaPunti,
				i + 1, sommaPunti);
			oldSommaPunti = sommaPunti;
		}
	}
	
	private float getSumPoints(float X)
	{
		float Y = 0;
		
		/* Somma i valori della funzioni nel punto dato una ad una */
		for(int n = 0; n < Waves.size(); n++)
			/* Calcola il valore della funzione in quel punto */
			Y += Waves.get(n).getValueIn(X);
		
		return Y;
	}

	private void drawWave(int index, Graphics2D g2)
	{
		/* L'onda deve essere centrata, così
		 * calcola lo spostamento per posizionarla
		 * al centro del canvas */
		int offsetY = getHeight() / 2;
		/* Imposta il colore */
		g2.setColor(Waves.get(index).color);
		/* I valori di due punti consecutivi */
		float currVal, oldVal;
		/* Calcola il primo valore */
		oldVal = Waves.get(index).getValueIn(0) + offsetY;
		
		/* Per ogni punto dello schermo */
		for(int i = 1; i < getWidth(); i++)
		{
			currVal = Waves.get(index).getValueIn(i / getWidth()) + offsetY;
			/* Disegna una linea tra i due punti consecutivi dell'onda */
			g2.drawLine(i, (int)(oldVal * getHeight()), i + 1, (int)(currVal * getHeight()));
			oldVal = currVal;
		}
	}
	
	private float screenToGraph(int x)
	{
		return (x - getWidth() / 2) * zoomX;
	}
	
	private int graphToScreen(float y)
	{
		return (int) ((y / zoomY + getHeight() / 2));
	}
}
