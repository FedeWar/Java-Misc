package it.fractals.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.fractals.FractalApp;

public class Listener implements ActionListener
{
	private int old = -1;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton)e.getSource();
		
		/* Viene chiesto esplicitamente di aggiornare l'immagine */
		if(button.getText() == "Aggiorna")
		{
			/* Controlla se il frattale selezionato è cambiato */
			int selected = FractalApp.mainInstance.getSelectedFract();
			if(selected != old)
			{
				old = selected;
				FractalApp.mainInstance.createFractal(selected);
			}
			
			FractalApp.mainInstance.refresh();;		// Lo disegna
		}
		/*else if(button.getText() == "+")
		{
			F_S.Fra.zoom+=10;
			F_S.DrawBounds();
		}
		else if(button.getText() == "-")
		{
			F_S.Fra.zoom-=10;
			F_S.DrawBounds();
		}
		else if(button.getText() == "^")
		{
			F_S.Fra.trasl.i+=10;
			F_S.DrawBounds();
		}
		else if(button.getText() == "V")
		{
			F_S.Fra.trasl.i-=10;
			F_S.DrawBounds();
		}
		else if(button.getText() == "<")
		{
			F_S.Fra.trasl.r+=10;
			F_S.DrawBounds();
		}
		else if(button.getText() == ">")
		{
			F_S.Fra.trasl.r-=10;
			F_S.DrawBounds();
		}
		/* Premuto "Animazione", apre una input dialog per ottenere il
		 * percorso in cui salvare i frames e passa il percorso a F_S.Fra.animation */
		else if(button.getText().compareTo("Animazione") == 0)
		{
			/*F_S.Fra.animation(
				JOptionPane.showInputDialog(
				null, "Export to", "Export Animation", JOptionPane.QUESTION_MESSAGE));*/
		}
	}

}
