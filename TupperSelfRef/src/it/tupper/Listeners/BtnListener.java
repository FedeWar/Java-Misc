package it.tupper.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import it.tupper.Tupper;

public class BtnListener implements MouseListener
{
	private Tupper A;
	
	public BtnListener(Tupper a)
	{
		A = a;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		JButton source = (JButton) arg0.getSource();	// Bottone premuto
		
		if(source.getText().compareTo("Disegna") == 0)	// Disegna il grafico
		{
			BigInteger Num = new BigInteger(A.getNum());	// Numero dell'utente
			int remainder = Num.mod(Tupper.BI17).intValue();// Resto numero con 17
			
			if(remainder == 0)		// Se il remainder è 0
				A.Draw(Num);		// Disegna il grafico
			else					// Se non è 0 lancia un messaggio di errore
				JOptionPane.showMessageDialog(null,
					"Num % 17 deve essere uguale a 0, attuale: " + remainder,
					"Errore", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(source.getText().compareTo("Calcola") == 0)
			A.invertDrawMode();		// Attiva / Disattiva la modalità di disegno
	}
	
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}
