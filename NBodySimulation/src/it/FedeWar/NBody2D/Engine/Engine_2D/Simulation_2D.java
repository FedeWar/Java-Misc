package it.FedeWar.NBody2D.Engine.Engine_2D;

import java.awt.Dimension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import it.FedeWar.NBody2D.Engine.Simulation;

public class Simulation_2D extends Simulation
{
	private Text txtWinDims;
	private Text txtObjCount;
	private Text txtMassVariation;
	private Text txtRadiusVariation;
	private Text txtStandardMass;
	private Text txtStandardRadius;
	private Text txtSpaceDims;

	@Override
	public void genEngine()
	{
		engine = new Engine_2D((Sim_Info_2D) info);
	}
	
	@Override
	public Engine_2D getEngine()
	{
		return (Engine_2D) engine;
	}
	
	@Override
	public void packInfo()
	{
		Sim_Info_2D info = new Sim_Info_2D();

		String wDim = txtWinDims.getText();
		int winDimW = Integer.parseInt(wDim.substring(0, wDim.indexOf(';')));
		int winDimH = Integer.parseInt(wDim.substring(wDim.indexOf(';') + 1, wDim.length()));
		
		info.winDim = new Dimension(winDimW, winDimH);

		info.obj_count = Integer.parseInt(txtObjCount.getText());
		info.standard_mass = Integer.parseInt(txtStandardMass.getText());
		info.standard_radius = Integer.parseInt(txtStandardRadius.getText());
		info.mass_variation = Integer.parseInt(txtMassVariation.getText());
		info.radius_variation = Integer.parseInt(txtRadiusVariation.getText());

		String sDim = txtSpaceDims.getText();
		int spcDimW = Integer.parseInt(sDim.substring(0, sDim.indexOf(';')));
		int spcDimH = Integer.parseInt(sDim.substring(sDim.indexOf(';') + 1, sDim.length()));
		
		info.spaceDim = new Dimension(spcDimW, spcDimH);

		this.info = info;
	}

	@Override
	public void genGUI(Composite father)
	{
		Display display = Display.getDefault();

		txtWinDims = new Text(father, SWT.BORDER);
		txtWinDims.setText("" + display.getBounds().width + ";" + display.getBounds().height);
		txtWinDims.setBounds(189, 30, 98, 33);

		Label lblWinDims = new Label(father, SWT.CENTER);
		lblWinDims.setBounds(10, 30, 188, 33);
		lblWinDims.setText("Dimensioni Finestra");

		txtObjCount = new Text(father, SWT.BORDER);
		txtObjCount.setText("256");
		txtObjCount.setBounds(204, 108, 83, 33);

		Label lblObjCount = new Label(father, SWT.CENTER);
		lblObjCount.setBounds(10, 108, 188, 33);
		lblObjCount.setText("Numero Oggetti");

		txtMassVariation = new Text(father, SWT.BORDER);
		txtMassVariation.setText("0");
		txtMassVariation.setBounds(204, 186, 83, 33);

		Label lblMassVariation = new Label(father, SWT.NONE);
		lblMassVariation.setAlignment(SWT.CENTER);
		lblMassVariation.setBounds(10, 186, 188, 33);
		lblMassVariation.setText("Variazione Massa");

		txtRadiusVariation = new Text(father, SWT.BORDER);
		txtRadiusVariation.setText("0");
		txtRadiusVariation.setBounds(204, 264, 83, 33);

		Label lblRadVariation = new Label(father, SWT.NONE);
		lblRadVariation.setAlignment(SWT.CENTER);
		lblRadVariation.setBounds(10, 264, 188, 33);
		lblRadVariation.setText("Variazione Raggio");

		txtStandardMass = new Text(father, SWT.BORDER);
		txtStandardMass.setText("1");
		txtStandardMass.setBounds(204, 147, 83, 33);

		txtStandardRadius = new Text(father, SWT.BORDER);
		txtStandardRadius.setText("1");
		txtStandardRadius.setBounds(204, 225, 83, 33);

		Label lblStandardRadius = new Label(father, SWT.NONE);
		lblStandardRadius.setAlignment(SWT.CENTER);
		lblStandardRadius.setBounds(10, 225, 188, 33);
		lblStandardRadius.setText("Raggio Standard");

		Label lblStandardMass = new Label(father, SWT.NONE);
		lblStandardMass.setAlignment(SWT.CENTER);
		lblStandardMass.setBounds(10, 147, 188, 33);
		lblStandardMass.setText("Massa Standard");

		txtSpaceDims = new Text(father, SWT.BORDER);
		txtSpaceDims.setBounds(189, 69, 98, 33);
		txtSpaceDims.setText("500;500");

		Label lblDimentions = new Label(father, SWT.NONE);
		lblDimentions.setAlignment(SWT.CENTER);
		lblDimentions.setBounds(10, 69, 188, 33);
		lblDimentions.setText("Dimensioni Spazio");
	}
}
