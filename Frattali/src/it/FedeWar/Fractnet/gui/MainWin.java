/* Copyright 2016 Federico Guerra aka FedeWar
	
	This file is part of Fractnet.

	Fractnet is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Fractnet is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Fractnet.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.FedeWar.Fractnet.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.FedeWar.Fractnet.PluginManager;


/* Finestra principale dell'app, generata con WindowBuilder */
public class MainWin extends JFrame
{
	private static final long serialVersionUID = -393757332107664646L;
	
	private JPanel contentPane;
	private JList<String> lstFracts;
	private Canvas pnlCanvas;
	
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();
			
			// È stato premuto il bottone: Disegna
			if(button.getText().compareTo("Disegna") == 0)
			{
				// Ridisegna il frattale e la finestra
				pnlCanvas.newFract(lstFracts.getSelectedValue());
				repaint();
			}
		}
	}
	
	/**
	 * Create the frame.
	 */
	public MainWin()
	{
		super("Fractal Viewer");
		
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("File");
		menuBar.add(mnuFile);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlCanvas = new Canvas();
		pnlCanvas.setBackground(Color.BLACK);
		pnlCanvas.setBounds(0, 0, width / 2, height - 120);
		contentPane.add(pnlCanvas);
		
		lstFracts = new JList<String>(PluginManager.getNames());
		lstFracts.setBounds(width / 2 + 12, 12, 200, height / 2);
		contentPane.add(lstFracts);
		
		JButton btnDraw = new JButton("Disegna");
		btnDraw.setBounds(905, 12, 117, 25);
		btnDraw.addActionListener(new ButtonListener());
		contentPane.add(btnDraw);
		
		setVisible(true);
	}
}