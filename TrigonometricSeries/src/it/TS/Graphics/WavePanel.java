package it.TS.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.TS.Math.Wave;

public class WavePanel extends JPanel
{
	private static final long serialVersionUID = -6426568610069758982L;
	
	private GraphPanel	pnlGraph;
	private JCheckBox	chckbxEvidenzia;
	private JButton		btnColore;
	private JSpinner	spnWavID;
	private JSpinner	spnXOffset;
	private JSpinner	spnYOffset;
	private JTextField	txtHz;
	private JTextField	txtA;
	
	/* Cattura tutti gli eventi generati da un click */
	class ButtonListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Object source = arg0.getSource();
			
			/* Fa scegliere all'utente, tramite una dialog box, il colore da usare */
			if(source instanceof JButton && ((JButton)source).getText() == "Colore")
			{
				Color scelto = JColorChooser.showDialog(null, "Scegli un Colore" , Color.GREEN);
				if(scelto != null)
					setColor(getSelectedId(), scelto);
			}
			/* Aggiunge o rimuove un onda */
			else if(source instanceof JLabel)
			{
				/* Ottiene la sorgente dell'evento */
				JLabel src = (JLabel)arg0.getSource();
				
				/* Se è stata premuta la label '+'
				 * aggiunge un'onda alla lista*/
				if(src.getText().compareTo("+") == 0)
					pnlGraph.addWave(new Wave());
				
				/* Se è stata premuta la label '-'
				 * rimuove un'onda dalla lista */
				else if(src.getText().compareTo("-") == 0)
					pnlGraph.removeWave(getSelectedId());
			}
			else
			{
				throw new java.lang.UnknownError();
			}
		}

		@Override public void mouseEntered(MouseEvent arg0) {}
		@Override public void mouseExited(MouseEvent arg0) {}
		@Override public void mousePressed(MouseEvent arg0) {}
		@Override public void mouseReleased(MouseEvent arg0) {}
	}
	
	/* Cattura gli eventi dagli Spinner */
	class SpinnerListener implements ChangeListener
	{
		int oldId = 0;
		@Override public void stateChanged(ChangeEvent e)
		{
			/* Controlla che i valori non sballino */
			int newId = getSelectedId();
			if(newId < 0)
				newId = setSelectedId(0);
			else if(newId >= pnlGraph.wavesCount())
				newId = setSelectedId(pnlGraph.wavesCount() - 1);
			
			/* Aggiorna l'onda modificata */
			pnlGraph.setWave(oldId, getSelectedWave());
			/* Aggiorna i controlli di conseguenza */
			setSelectedWave(pnlGraph.getWave(newId));
			
			pnlGraph.repaint();
			oldId = newId;
		}
	}
	
	public WavePanel(GraphPanel gp)
	{
		pnlGraph = gp;
		initialize();
	}
	
	public double getHz()
	{
		return Double.valueOf(txtHz.getText());
	}
	
	public void setHz(double newVal)
	{
		txtHz.setText(String.valueOf(newVal));
	}
	
	public double getA()
	{
		return Double.valueOf(txtA.getText());
	}
	
	public void setA(double A)
	{
		txtA.setText(String.valueOf(A));
	}

	public int getSelectedId()
	{
		return (int) spnWavID.getValue();
	}
	
	public int setSelectedId(int id)
	{
		spnWavID.setValue(id);
		return id;
	}

	public void setColor(int id, Color color)
	{
		btnColore.setBackground(color);
		btnColore.setForeground(new Color(
			255 - color.getRed(),
			255 - color.getGreen(),
			255 - color.getBlue()));
	}

	public Wave getSelectedWave()
	{
		Wave W = new Wave();
		
		W.Hz = getHz();
		W.Amp = getA();
		W.isShown = chckbxEvidenzia.isSelected();
		W.Pos.width = (int) spnXOffset.getValue();
		W.Pos.height = (int) spnYOffset.getValue();
		W.color = btnColore.getBackground();
		
		return W;
	}
	
	public void setSelectedWave(Wave W)
	{
		setHz(W.Hz);
		setA(W.Amp);
		chckbxEvidenzia.setSelected(W.isShown);
		spnXOffset.setValue(W.Pos.width);
		spnYOffset.setValue(W.Pos.height);
		btnColore.setBackground(W.color);
		btnColore.setForeground(new Color(
			255 - W.color.getRed(),
			255 - W.color.getGreen(),
			255 - W.color.getBlue()));
	}
	
	private void initialize()
	{
		/* Listener */
		ButtonListener BL = new ButtonListener();
		
		/* Label */
		
		JLabel lblFrequenza = new JLabel("Frequenza");
		lblFrequenza.setToolTipText("La frequenza dell'onda");
		lblFrequenza.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrequenza.setBounds(10, 98, 89, 14);
		this.add(lblFrequenza);
		
		JLabel lblAmpiezza = new JLabel("Ampiezza");
		lblAmpiezza.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmpiezza.setBounds(10, 129, 89, 14);
		this.add(lblAmpiezza);
		
		JLabel lblOndaId = new JLabel("Onda");
		lblOndaId.setHorizontalAlignment(SwingConstants.CENTER);
		lblOndaId.setBounds(10, 58, 89, 14);
		this.add(lblOndaId);
		
		JLabel lblOnda = new JLabel("Onda");
		lblOnda.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOnda.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnda.setBounds(10, 11, 218, 20);
		this.add(lblOnda);
		
		JLabel lblXOffset = new JLabel("Sfasamento X");
		lblXOffset.setHorizontalAlignment(SwingConstants.CENTER);
		lblXOffset.setBounds(10, 160, 89, 14);
		this.add(lblXOffset);
		
		JLabel lblYOffset = new JLabel("Sfasamento Y");
		lblYOffset.setHorizontalAlignment(SwingConstants.CENTER);
		lblYOffset.setBounds(10, 191, 89, 14);
		this.add(lblYOffset);
		
		JLabel lblAddWave = new JLabel("+");
		lblAddWave.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddWave.setBounds(208, 55, 20, 20);
		lblAddWave.addMouseListener(BL);
		add(lblAddWave);
		
		JLabel lblRemoveWave = new JLabel("-");
		lblRemoveWave.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveWave.setBounds(104, 55, 20, 20);
		lblRemoveWave.addMouseListener(BL);
		add(lblRemoveWave);
		
		/* Checkbox */
		
		chckbxEvidenzia = new JCheckBox("Evidenzia");
		chckbxEvidenzia.setMnemonic('e');
		chckbxEvidenzia.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxEvidenzia.setBounds(10, 215, 218, 23);
		add(chckbxEvidenzia);
		
		/* Spinner */
		
		spnWavID = new JSpinner();
		spnWavID.setBounds(130, 55, 71, 20);
		spnWavID.addChangeListener(new SpinnerListener());
		add(spnWavID);
		
		spnXOffset = new JSpinner();
		spnXOffset.setBounds(104, 157, 124, 20);
		add(spnXOffset);
		
		spnYOffset = new JSpinner();
		spnYOffset.setBounds(104, 188, 124, 20);
		add(spnYOffset);
		
		/* Textfield */
		
		txtHz = new JTextField();
		txtHz.setText("1.0");
		txtHz.setHorizontalAlignment(SwingConstants.CENTER);
		txtHz.setBounds(104, 95, 124, 20);
		txtHz.setColumns(10);
		add(txtHz);
		
		txtA = new JTextField();
		txtA.setText("1");
		txtA.setHorizontalAlignment(SwingConstants.CENTER);
		txtA.setBounds(104, 126, 124, 20);
		txtA.setColumns(10);
		add(txtA);
		
		/* Button */
		
		btnColore = new JButton("Colore");
		btnColore.setBounds(72, 245, 89, 23);
		btnColore.addMouseListener(BL);
		btnColore.setBackground(Color.BLACK);
		add(btnColore);
	}
}
