package it.life.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import it.life.Applicazione;

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
			App.runDrawing();
			
		else if(button.getText() == "Start game")
			App.runSimulation();
		
		else if(button.getText() == "Stop game")
			App.stopSimulation();
		
		else if(button.getText().compareTo("Export") == 0)
			App.exportTo(JOptionPane.showInputDialog(App, "Path: ", "Export", JOptionPane.QUESTION_MESSAGE));
		
		else if(button.getText().compareTo("Import") == 0)
			App.importFrom(JOptionPane.showInputDialog(App, "Path: ", "Import", JOptionPane.QUESTION_MESSAGE));
	}
}