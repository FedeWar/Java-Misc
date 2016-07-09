package it.FedeWar.Frattali.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

import it.FedeWar.Frattali.PluginManager;
import it.FedeWar.Frattali.fractals.Frattale;

public class MainWin extends JFrame
{
	private static final long serialVersionUID = -393757332107664646L;
	
	private JPanel contentPane;
	private JList<String> lstFracts;
	private Canvas pnlCanvas;

	/* Pannello sui cui disegnare */
	private class Canvas extends JPanel
	{
		private static final long serialVersionUID = -6573971607519471731L;
		private Frattale currFract;
		private int oldSelection = -1;
		
		/* Crea un nuovo frattale da visualizzare */
		public void newFract(String name)
		{
			// Ottiene l'id del frattale selezionato nella lista
			int currentSelection = PluginManager.search(lstFracts.getSelectedValue());
			
			// Evita di disegnare più del necessario
			if(currentSelection != oldSelection)
			{
				oldSelection = currentSelection;	// Cambia la vecchia selezione
				currFract = PluginManager.create(	// Crea un nuovo frattale
						currentSelection,
						pnlCanvas.getWidth(),
						pnlCanvas.getHeight());
			}
		}
		
		/* Ridisegna il pannello */
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			if(currFract != null)
			{
				currFract.Draw();
				g2.drawImage(currFract.getImage(), null, null);
			}
		}
	}
	
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();
			
			if(button.getText().compareTo("Disegna") == 0)			// Bottone "Disegna"
			{
				pnlCanvas.newFract(lstFracts.getSelectedValue());	// Cambia il frattale
				repaint();											// Ridisegna la finestra
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
