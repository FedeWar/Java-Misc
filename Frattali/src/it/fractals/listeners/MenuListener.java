package it.fractals.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import it.fractals.FractalApp;

public class MenuListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JMenuItem source = (JMenuItem) arg0.getSource();
		if(source.getText() == "Load Script")
		{
			FractalApp.mainInstance.addPlugin("Frattali.Julia");
		}
	}
}
