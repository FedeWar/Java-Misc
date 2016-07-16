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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.FedeWar.Fractnet.PluginManager;
import it.FedeWar.Fractnet.gui.Canvas;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;

/* Finestra principale dell'app, generata con WindowBuilder */
public class MainWin extends JFrame
{
	private static final long serialVersionUID = -393757332107664646L;
	
	private JList<String> lstFracts;
	private Canvas pnlCanvas;
	private JTextField txtArg;
	private JButton btnZoomPlus, btnZoomLess;	// Controlli dello zoom
	private JButton btnMove;
	private JButton btnMoveUp, btnMoveDown, btnMoveRight, btnMoveLeft;
	
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();
			
			// Viene chiesto di creare un nuovo frattale
			if(button.getText().compareTo("Disegna") == 0)
			{
				pnlCanvas.newFract(lstFracts.getSelectedValue(), txtArg.getText());
				repaint();
			}
			
			// Viene incrementato lo zoom 
			else if(button.equals(btnZoomPlus))
				pnlCanvas.setZoom(pnlCanvas.getZoom() * 2);
			// Viene decrementato lo zoom
			else if(button.equals(btnZoomLess))
				pnlCanvas.setZoom(pnlCanvas.getZoom() / 2);
			
			else if(button.equals(btnMoveUp))
				pnlCanvas.setTrasl(pnlCanvas.getTrasl()[0], pnlCanvas.getTrasl()[1] + 10);
			else if(button.equals(btnMoveDown))
				pnlCanvas.setTrasl(pnlCanvas.getTrasl()[0], pnlCanvas.getTrasl()[1] - 10);
			else if(button.equals(btnMoveRight))
				pnlCanvas.setTrasl(pnlCanvas.getTrasl()[0] + 10, pnlCanvas.getTrasl()[1]);
			else if(button.equals(btnMoveLeft))
				pnlCanvas.setTrasl(pnlCanvas.getTrasl()[0] - 10, pnlCanvas.getTrasl()[1]);
			
			// Ridisegna l'interfaccia
			else if(button.equals(btnMove))
				pnlCanvas.apply();
				
			repaint();
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
		
		ButtonListener BL = new ButtonListener();
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlCanvas = new Canvas(width / 2, height - 120);
		pnlCanvas.setBackground(Color.BLACK);
		pnlCanvas.setLocation(0, 0);
		contentPane.add(pnlCanvas);
		
		lstFracts = new JList<String>(PluginManager.getNames());
		lstFracts.setBounds(width / 2 + 12, 12, 200, height / 2);
		contentPane.add(lstFracts);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(1154, 0, 200, 648);
		contentPane.add(toolBar);
		
		JPanel panel = new JPanel();
		toolBar.add(panel);
		panel.setLayout(null);
		
		JButton btnDraw = new JButton("Disegna");
		btnDraw.setBounds(6, 22, 176, 25);
		btnDraw.addActionListener(BL);
		panel.add(btnDraw);
		
		JLabel lblArgomento = new JLabel("Argomento:");
		lblArgomento.setHorizontalAlignment(SwingConstants.CENTER);
		lblArgomento.setBounds(6, 72, 176, 15);
		panel.add(lblArgomento);
		
		txtArg = new JTextField();
		txtArg.setBounds(6, 99, 176, 19);
		panel.add(txtArg);
		txtArg.setHorizontalAlignment(SwingConstants.CENTER);
		txtArg.setText("-0.70176;-0.3842");
		txtArg.setColumns(10);
		
		btnZoomPlus = new JButton("");
		btnZoomPlus.setIcon(new ImageIcon(MainWin.class.getResource("/it/FedeWar/Fractnet/res/ZoomPlus.png")));
		btnZoomPlus.setBounds(16, 130, 30, 30);
		btnZoomPlus.addActionListener(BL);
		panel.add(btnZoomPlus);
		
		btnZoomLess = new JButton("");
		btnZoomLess.setBounds(145, 130, 30, 30);
		btnZoomLess.setIcon(new ImageIcon(MainWin.class.getResource("/it/FedeWar/Fractnet/res/ZoomLess.png")));
		btnZoomLess.addActionListener(BL);
		panel.add(btnZoomLess);
		
		btnMoveUp = new JButton("");
		btnMoveUp.setBounds(70, 130, 50, 50);
		btnMoveUp.addActionListener(BL);
		panel.add(btnMoveUp);
		
		btnMoveLeft = new JButton("");
		btnMoveLeft.setBounds(20, 180, 50, 50);
		btnMoveLeft.addActionListener(BL);
		panel.add(btnMoveLeft);
		
		btnMoveRight = new JButton("");
		btnMoveRight.setBounds(120, 180, 50, 50);
		btnMoveRight.addActionListener(BL);
		panel.add(btnMoveRight);
		
		btnMoveDown = new JButton("");
		btnMoveDown.setBounds(70, 230, 50, 50);
		btnMoveDown.addActionListener(BL);
		panel.add(btnMoveDown);
		
		btnMove = new JButton("");
		btnMove.setBounds(70, 180, 50, 50);
		btnMove.addActionListener(BL);
		panel.add(btnMove);
		
		setVisible(true);
	}
}
