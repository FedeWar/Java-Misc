package it.FedeWar.TrigonometricSeries.Graphics;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame
{
	/* Creare dei metodi per leggere da questi valori */
	private JFrame		mainFrame;
	private WavePanel	pnlWave;
	private GraphPanel	pnlGraph;
	private JCheckBox	chckbxMostraSomma;
	private JSlider		sldZoom;
	private JTextField	function;
	
	class ButtonListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			JButton src = (JButton)arg0.getSource();
			
			if(src.getText() == "Disegna")
			{
				/* Invia i valori dei controlli al GraphPanel */
				int zoom = sldZoom.getValue();
				if(zoom == 0) zoom = 1;
				if(zoom <= 50)
					pnlGraph.setZoom((float) Math.log10(zoom / 5.0));
				else
					pnlGraph.setZoom(zoom - 50);	// Migliorare
				pnlGraph.setWave(pnlWave.getSelectedId(), pnlWave.getSelectedWave());
				pnlGraph.setSum(chckbxMostraSomma.isSelected());

				/* Ridisegna il Panel */
				pnlGraph.repaint();
			}
			else if(src.getText() == "Aggiorna")
			{
				function.setText(pnlGraph.getFunction());
			}
			else if(src.getText() == "Grafico")
			{
				pnlGraph.Parse(function.getText());
				pnlWave.setSelectedWave(pnlGraph.getWave(0));
//				pnlWave.setSelectedId(0); FIXME
			}
		}

		@Override public void mouseEntered(MouseEvent arg0) {}
		@Override public void mouseExited(MouseEvent arg0) {}
		@Override public void mousePressed(MouseEvent arg0) {}
		@Override public void mouseReleased(MouseEvent arg0) {}
	}
	
	public static void main(String[] args)
	{
		new MainFrame();
	}

	/* Costruttore */
	public MainFrame()
	{
		initialize();
	}

	public boolean getDrawSum()
	{
		return chckbxMostraSomma.isSelected();
	}
	
	/* Inizializza i controlli dell'interfaccia grafica */
	private void initialize()
	{
		ButtonListener BL = new ButtonListener();
		
		/* Frame principale */
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 640, 480);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		/* Panel */
		
		pnlGraph = new GraphPanel(258, 11, 734, 683);
		mainFrame.getContentPane().add(pnlGraph);
		
		pnlWave = new WavePanel(pnlGraph);
		pnlWave.setBounds(10, 11, 238, 342);
		mainFrame.getContentPane().add(pnlWave);
		pnlWave.setLayout(null);
		
		JPanel pnlView = new JPanel();
		pnlView.setBounds(8, 307, 238, 341);
		mainFrame.getContentPane().add(pnlView);
		pnlView.setLayout(null);
		
		/* Label */
		
		JLabel lblZoom = new JLabel("Zoom");
		lblZoom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblZoom.setBounds(20, 45, 200, 23);
		pnlView.add(lblZoom);
		
		JLabel lblGrafico = new JLabel("Grafico");
		lblGrafico.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrafico.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGrafico.setBounds(10, 11, 218, 30);
		pnlView.add(lblGrafico);
		
		chckbxMostraSomma = new JCheckBox("Mostra Somma");
		chckbxMostraSomma.setBounds(10, 277, 218, 23);
		chckbxMostraSomma.setSelected(true);
		chckbxMostraSomma.setHorizontalAlignment(SwingConstants.CENTER);
		pnlView.add(chckbxMostraSomma);
		
		sldZoom = new JSlider();
		sldZoom.setMajorTickSpacing(1);
		sldZoom.setMinorTickSpacing(1);
		sldZoom.setBounds(20, 70, 200, 26);
		pnlView.add(sldZoom);
		
		JButton btnDisegna = new JButton("Disegna");
		btnDisegna.setBounds(63, 307, 105, 23);
		btnDisegna.addMouseListener(BL);
		pnlView.add(btnDisegna);
		
		function = new JTextField("sin(x)");
		function.setBounds(10, 200, 200, 20);
		pnlView.add(function);
		
		JButton updateFunc = new JButton("Aggiorna");
		updateFunc.setBounds(10, 225, 100, 20);
		updateFunc.addMouseListener(BL);
		pnlView.add(updateFunc);
		
		JButton drawFunc = new JButton("Grafico");
		drawFunc.setBounds(115, 225, 95, 20);
		drawFunc.addMouseListener(BL);
		pnlView.add(drawFunc);
		
		mainFrame.setVisible(true);
	}
}
