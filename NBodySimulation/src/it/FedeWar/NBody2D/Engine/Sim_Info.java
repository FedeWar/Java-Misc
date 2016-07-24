package it.FedeWar.NBody2D.Engine;

import java.awt.Dimension;

/**
 * La quantità delle informazioni cambia a seconda del tipo
 * di simulazione, questa classe fornisce una base per
 * implementare ogni tipo di simulazione.
 * 
 * @author FedeWar
 * @version 1.0
 * @since 1.0
 * @date 24/07/2016
 * */
public abstract class Sim_Info
{
	public int obj_count;
	public Dimension winDim;
}
