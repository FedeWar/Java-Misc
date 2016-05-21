package Main_Pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener implements ActionListener
{
	Applicazione App;

	public Listener(Applicazione app)
	{
		App = app;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton)e.getSource();	// Ottiene il bottone che ha lanciato il comando
		
		if(button.getText() == "Start drawing")
		{
			App.runDrawing();
		}
		else if(button.getText() == "Start game")
		{
			App.runSimulation();
		}
		else if(button.getText() == "Stop game")
			App.stopSimulation();
	}
}