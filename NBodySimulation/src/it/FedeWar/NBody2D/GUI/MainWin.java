package it.FedeWar.NBody2D.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import it.FedeWar.NBody2D.Engine.PluginManager;
import it.FedeWar.NBody2D.Engine.Simulation;

public class MainWin
{
	private List lstSims;
	private Shell pnlMain;
	private Simulation sim;
	private Composite pnlSim;

	private class BtnListener implements MouseListener
	{
		private int btnID;
		
		public BtnListener(int btnid) {
			btnID = btnid;
		}
		
		@Override
		public void mouseUp(MouseEvent arg0)
		{
			// Premuto "Avvia"
			if(btnID == 0) {
				sim.packInfo();
				pnlMain.dispose();
			}
			// Premuto "Cambia simulazione"
			else if(btnID == 1) {
				sim = PluginManager.create(lstSims.getSelectionIndex());
				sim.genGUI(pnlSim);
			}
		}
		
		@Override public void mouseDoubleClick(MouseEvent arg0) {}
		@Override public void mouseDown(MouseEvent arg0) {}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public Simulation open()
	{
		Display display = Display.getDefault();
		pnlMain = new Shell();
		pnlMain.setSize(640, 480);
		pnlMain.setText("N-Body Simulation");
		
		Label lineSeparator = new Label(pnlMain, SWT.SEPARATOR | SWT.VERTICAL);
		lineSeparator.setBounds(321, 0, 1, 402);
		
		Label lblSettings = new Label(pnlMain, SWT.NONE);
		lblSettings.setAlignment(SWT.CENTER);
		lblSettings.setBounds(107, 10, 98, 20);
		lblSettings.setText("Impostazioni");
		
		Button btnRun = new Button(pnlMain, SWT.NONE);
		btnRun.setBounds(264, 408, 98, 33);
		btnRun.addMouseListener(new BtnListener(0));
		btnRun.setText("Avvia");
		
		lstSims = new List(pnlMain, SWT.BORDER);
		lstSims.setItems(PluginManager.getNames());
		lstSims.setBounds(328, 58, 296, 305);
		
		Label lblSimList = new Label(pnlMain, SWT.NONE);
		lblSimList.setAlignment(SWT.CENTER);
		lblSimList.setBounds(328, 10, 296, 42);
		lblSimList.setText("Lista Simulazioni");
		
		Button btnChangeSim = new Button(pnlMain, SWT.NONE);
		btnChangeSim.setBounds(376, 369, 193, 33);
		btnChangeSim.setText("Cambia Simulazione");
		btnChangeSim.addMouseListener(new BtnListener(1));
		
		pnlSim = new Composite(pnlMain, SWT.NONE);
		pnlSim.setBounds(10, 35, 312, 367);
		
		pnlMain.open();
		pnlMain.layout();
		
		while (!pnlMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		return sim;
	}
}
