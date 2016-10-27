package it.FedeWar.TrigonometricSeries.Graphics;

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
import javax.swing.JSpinner.ListEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.FedeWar.TrigonometricSeries.Math.Wave;

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
	
	/*
	 * Cattura tutti gli eventi generati da un click.
	 */
	class ButtonListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Object source = arg0.getSource();
			
			// Fa scegliere all'utente, tramite una dialog box, il colore da usare
			if(source instanceof JButton && ((JButton)source).getText() == "Colore")
			{
				Color scelto = JColorChooser.showDialog(null, "Scegli un Colore" , Color.GREEN);
				if(scelto != null)
					setColor(getSelectedId(), scelto);
			}
			else if(source instanceof JLabel)
			{
				// Ottiene il testo dell'elemento che ha lanciato l'evento
				String src = ((JLabel)arg0.getSource()).getText();
				
				// Aggiunge un'onda alla lista
				if(src.compareTo("+") == 0)
					addWave();
				
				// Rimuove l'onda selezionata alla lista
				else if(src.compareTo("-") == 0)
					removeWave();
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
	
	/*
	 * Visualizza il testo del JSpinner nel formato pos/max.
	 * Con 0 < posizione <= max e max >= 0.
	 */
	class CustomListModel extends SpinnerListModel
	{
		private static final long serialVersionUID = -364597415670249620L;
		
		private int max;
		private int pos;
		private ChangeListener listener;
		private final ChangeEvent event;
		
		public CustomListModel()
		{
			event = new ChangeEvent(this);
			max = pos = 0;
		}
		
		public void inc()
		{
			++max;
			listener.stateChanged(event);
		}
		
		public void dec() 
		{
			--max;
			checkBounds();
			listener.stateChanged(event);
		}
		
		@Override
		public void addChangeListener(ChangeListener l) {
			listener = l;
		}

		@Override
		public Object getNextValue()
		{
			++pos;
			checkBounds();
			listener.stateChanged(event);
			return genStr();
		}

		@Override
		public Object getPreviousValue()
		{
			listener.stateChanged(event);
			--pos;
			checkBounds();
			return genStr();
		}

		@Override
		public Object getValue() {
			return genStr();
		}

		@Override
		public void removeChangeListener(ChangeListener l) {
			listener = null;
		}

		@Override
		public void setValue(Object value)
		{
			String str = (String) value;
			pos = Integer.parseInt(str.substring(0, str.indexOf('/') - 1));
			checkBounds();
			listener.stateChanged(event);
		}

		private void checkBounds()
		{
			if(max < 0)
				max = 0;
			if(pos <= 0)
				pos = 1;
			if(pos > max)
				pos = max;
		}
		
		private String genStr() {
			return new String(String.valueOf(pos) + " / " + max);
		}
	}
	
	/*
	 * Cattura gli eventi dagli Spinner.
	 */
	class SpinnerListener implements ChangeListener
	{
		// Onda selezionata prima della generazione dell'evento
		private int oldId = 0;
		
		@Override public void stateChanged(ChangeEvent e)
		{
			int newId = getSelectedId();
			
			// Aggiorna l'onda modificata
			pnlGraph.setWave(oldId, getSelectedWave());
			
			// Aggiorna i controlli di conseguenza
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

	/*
	 * Restituisce l'indice dell'onda selezionata sullo spinner.
	 */
	public int getSelectedId()
	{
		// Esegue il parsing del numero
		String str = (String) spnWavID.getValue();
		return Integer.parseInt(str.substring(0, str.indexOf('/') - 1)) - 1;
	}

	/*
	 * Inpacchetta tutte le informazioni, contenute
	 * nei controlli della GUI, in un oggetto Wave.
	 * 
	 * @return Un nuovo oggetto Wave.
	 */
	public Wave getSelectedWave()
	{
		Wave W = new Wave();
		
		W.Hz = Double.valueOf(txtHz.getText());
		W.Amp = Double.valueOf(txtA.getText());
		W.isShown = chckbxEvidenzia.isSelected();
		W.Pos.width = (int) spnXOffset.getValue();
		W.Pos.height = (int) spnYOffset.getValue();
		W.color = btnColore.getBackground();
		
		return W;
	}
	
	/*
	 * Imposta i valori dei controlli secondi
	 * le infomazioni dell'onda W.
	 * 
	 * @param W L'onda da rappresentare.
	 */
	public void setSelectedWave(Wave W)
	{
		if(W == null)
			return;
		
		txtHz.setText(String.valueOf(W.Hz));
		txtA.setText(String.valueOf(W.Amp));
		chckbxEvidenzia.setSelected(W.isShown);
		spnXOffset.setValue(W.Pos.width);
		spnYOffset.setValue(W.Pos.height);
		btnColore.setBackground(W.color);
		btnColore.setForeground(new Color(
			255 - W.color.getRed(),
			255 - W.color.getGreen(),
			255 - W.color.getBlue()));
	}
	
	private void setColor(int id, Color color)
	{
		btnColore.setBackground(color);
		btnColore.setForeground(new Color(
			255 - color.getRed(),
			255 - color.getGreen(),
			255 - color.getBlue()));
	}
	
	/*
	 * Aggiunge una nuova onda alla lista.
	 */
	private void addWave()
	{
		// Aumenta la lunghezza della lista di uno
		((CustomListModel)spnWavID.getModel()).inc();
		
		// Avverte la lista delle onde di aggiungerne una
		pnlGraph.addWave(new Wave());
	}
	
	/*
	 * Rimuove dalla lista l'onda selezionata.
	 */
	private void removeWave()
	{
		// Riduce di uno il numero di onde
		((CustomListModel)spnWavID.getModel()).dec();
		
		// Avverte la lista di onde di eliminarne una
		pnlGraph.removeWave(getSelectedId());
	}
	
	/*
	 * Inizializza i controlli dell'intergaccia grafica.
	 */
	private void initialize()
	{
		// Listener
		ButtonListener BL = new ButtonListener();
		
		// Label
		
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
		
		// Checkbox
		
		chckbxEvidenzia = new JCheckBox("Evidenzia");
		chckbxEvidenzia.setMnemonic('e');
		chckbxEvidenzia.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxEvidenzia.setBounds(10, 215, 218, 23);
		add(chckbxEvidenzia);
		
		// Spinner
		
		spnWavID = new JSpinner(new CustomListModel());
		spnWavID.setBounds(130, 55, 71, 20);
		spnWavID.addChangeListener(new SpinnerListener());
		ListEditor dEditor = new JSpinner.ListEditor(spnWavID);
		spnWavID.setEditor(dEditor);
		add(spnWavID);
		
		spnXOffset = new JSpinner();
		spnXOffset.setBounds(104, 157, 124, 20);
		add(spnXOffset);
		
		spnYOffset = new JSpinner();
		spnYOffset.setBounds(104, 188, 124, 20);
		add(spnYOffset);
		
		// Textfield
		
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
		
		// Button
		
		btnColore = new JButton("Colore");
		btnColore.setBounds(72, 245, 89, 23);
		btnColore.addMouseListener(BL);
		btnColore.setBackground(Color.BLACK);
		add(btnColore);
	}
}
