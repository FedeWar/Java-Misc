package it.tupper.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.tupper.Tupper;

public class PnlListener implements MouseListener
{
	private Tupper A;
	
	public PnlListener(Tupper a)
	{
		A = a;
	}
	
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		A.clickOnPanel(arg0.getButton(), true);
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		A.clickOnPanel(arg0.getButton(), false);
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}