package it.FedeWar.NBody2D.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import it.FedeWar.NBody2D.Engine.Sim_Info;

import org.eclipse.swt.widgets.Label;

public class MainWin
{
	private Shell shlNbodySimulation;
	private Text txtWidth;
	private Text txtHeight;
	private Text txtObjCount;
	private Text txtMassVariation;
	private Text txtRadiusVariation;
	private Text txtStandardMass;
	private Text txtStandardRadius;
	private Sim_Info SI;
	private Text txtDimentions;

	class RunListener implements MouseListener
	{
		@Override public void mouseDoubleClick(MouseEvent arg0) {}

		@Override
		public void mouseDown(MouseEvent arg0) {}

		@Override
		public void mouseUp(MouseEvent arg0)
		{
			packInfo();
			shlNbodySimulation.dispose();
		}

	}

	/* Impacchetta le informazioni */
	private void packInfo()
	{
		SI = new Sim_Info();
		SI.width = Integer.parseInt(txtWidth.getText());
		SI.height = Integer.parseInt(txtHeight.getText());
		SI.obj_count = Integer.parseInt(txtObjCount.getText());
		SI.standard_mass = Integer.parseInt(txtStandardMass.getText());
		SI.standard_radius = Integer.parseInt(txtStandardRadius.getText());
		SI.mass_variation = Integer.parseInt(txtMassVariation.getText());
		SI.radius_variation = Integer.parseInt(txtRadiusVariation.getText());
		
		String D = txtDimentions.getText();
		SI.dim_x = Integer.parseInt(D.substring(0, D.indexOf(';')));
		SI.dim_y = Integer.parseInt(D.substring(D.indexOf(';') + 1, D.length()));
	}


	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public Sim_Info open()
	{
		Display display = Display.getDefault();
		shlNbodySimulation = new Shell();
		shlNbodySimulation.setSize(640, 480);
		shlNbodySimulation.setText("N-Body Simulation");
		
		Button btnRun = new Button(shlNbodySimulation, SWT.NONE);
		btnRun.setBounds(264, 408, 98, 33);
		btnRun.addMouseListener(new RunListener());
		btnRun.setText("Avvia");
		
		txtWidth = new Text(shlNbodySimulation, SWT.BORDER);
		txtWidth.setText("" + display.getBounds().width);
		txtWidth.setBounds(204, 58, 83, 33);
		
		Label lblWidth = new Label(shlNbodySimulation, SWT.CENTER);
		lblWidth.setBounds(10, 58, 188, 33);
		lblWidth.setText("Larghezza");
		
		txtHeight = new Text(shlNbodySimulation, SWT.BORDER);
		txtHeight.setText("" + display.getBounds().height);
		txtHeight.setBounds(204, 97, 83, 33);
		
		Label lblHeight = new Label(shlNbodySimulation, SWT.NONE);
		lblHeight.setAlignment(SWT.CENTER);
		lblHeight.setBounds(10, 97, 188, 33);
		lblHeight.setText("Altezza");
		
		Label lineSeparator = new Label(shlNbodySimulation, SWT.SEPARATOR | SWT.VERTICAL);
		lineSeparator.setBounds(321, 0, 1, 402);
		
		Label lblSettings = new Label(shlNbodySimulation, SWT.NONE);
		lblSettings.setAlignment(SWT.CENTER);
		lblSettings.setBounds(107, 10, 98, 20);
		lblSettings.setText("Impostazioni");
		
		txtObjCount = new Text(shlNbodySimulation, SWT.BORDER);
		txtObjCount.setText("256");
		txtObjCount.setBounds(204, 136, 83, 33);
		
		Label lblObjCount = new Label(shlNbodySimulation, SWT.CENTER);
		lblObjCount.setBounds(10, 136, 188, 33);
		lblObjCount.setText("Numero Oggetti");
		
		txtMassVariation = new Text(shlNbodySimulation, SWT.BORDER);
		txtMassVariation.setText("0");
		txtMassVariation.setBounds(204, 214, 83, 33);
		
		Label lblMassVariation = new Label(shlNbodySimulation, SWT.NONE);
		lblMassVariation.setAlignment(SWT.CENTER);
		lblMassVariation.setBounds(10, 214, 188, 33);
		lblMassVariation.setText("Variazione Massa");
		
		txtRadiusVariation = new Text(shlNbodySimulation, SWT.BORDER);
		txtRadiusVariation.setText("0");
		txtRadiusVariation.setBounds(204, 292, 83, 33);
		
		Label lblRadVariation = new Label(shlNbodySimulation, SWT.NONE);
		lblRadVariation.setAlignment(SWT.CENTER);
		lblRadVariation.setBounds(10, 292, 188, 33);
		lblRadVariation.setText("Variazione Raggio");
		
		txtStandardMass = new Text(shlNbodySimulation, SWT.BORDER);
		txtStandardMass.setText("1");
		txtStandardMass.setBounds(204, 175, 83, 33);
		
		txtStandardRadius = new Text(shlNbodySimulation, SWT.BORDER);
		txtStandardRadius.setText("1");
		txtStandardRadius.setBounds(204, 253, 83, 33);
		
		Label lblStandardRadius = new Label(shlNbodySimulation, SWT.NONE);
		lblStandardRadius.setAlignment(SWT.CENTER);
		lblStandardRadius.setBounds(10, 253, 188, 33);
		lblStandardRadius.setText("Raggio Standard");
		
		Label lblStandardMass = new Label(shlNbodySimulation, SWT.NONE);
		lblStandardMass.setAlignment(SWT.CENTER);
		lblStandardMass.setBounds(10, 175, 188, 33);
		lblStandardMass.setText("Massa Standard");
		
		txtDimentions = new Text(shlNbodySimulation, SWT.BORDER);
		txtDimentions.setBounds(204, 331, 83, 33);
		txtDimentions.setText("100;100");
		
		Label lblDimentions = new Label(shlNbodySimulation, SWT.NONE);
		lblDimentions.setAlignment(SWT.CENTER);
		lblDimentions.setBounds(10, 331, 188, 33);
		lblDimentions.setText("Dimenzioni Spazio");

		shlNbodySimulation.open();
		shlNbodySimulation.layout();
		while (!shlNbodySimulation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		return SI;
	}
}
