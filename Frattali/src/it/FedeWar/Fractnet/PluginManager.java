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

package it.FedeWar.Fractnet;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import it.FedeWar.Fractnet.fractals.Fractal;

/* Gestione dei plugin, la classe non può essere instanziata */
public abstract class PluginManager
{
	private	static DefaultListModel<String> names;		// Lista nomi frattali disponibili
	private	static ArrayList<Class<Fractal>> plugins;	// Lista classi dei frattali
	
	/* Inizializza le liste */
	static
	{
		plugins = new ArrayList<Class<Fractal>>();
		names = new DefaultListModel<String>();
		loadBuiltinFractals();
	}
	
	/* Carica i frattali distribuiti col programma */
	@SuppressWarnings("unchecked")
	private static void loadBuiltinFractals()
	{
		String pluginsPath = "it.FedeWar.Fractnet.fractals.plugins.";
		try {
			plugins.add((Class<Fractal>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "Mandelbrot"));
			names.addElement("Mandelbrot");
			
			plugins.add((Class<Fractal>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "Julia"));
			names.addElement("Julia");
			
			plugins.add((Class<Fractal>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "SierpinskiSquares"));
			names.addElement("Tappeto di Sierpinski");
			
			plugins.add((Class<Fractal>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "SierpinskiTriangles"));
			names.addElement("Triangoli di Sierpinski");
		} catch (ClassNotFoundException e) {
			System.err.println("Impossibile caricare la classe: " + e.getMessage());
		}
	}
	
	/* Alloca un frattale per essere disegnato */
	public static Fractal create(int selected)
	{
		try
		{
			// Crea, inizializza e restituisce un nuovo frattale
			Fractal fra = plugins.get(selected).newInstance();
			return fra;
		}
		catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Errore allocazione oggetto, è un frattale valido?");
		}
		return null;
	}
	
	/* Carica un nuovo frattale e lo aggiunge alla lista */
	@SuppressWarnings("unchecked")
	public static void add(String name)
	{
		Class<Fractal> plugin;
		try
		{
			plugin = (Class<Fractal>) ClassLoader.getSystemClassLoader().loadClass(name);
			plugins.add(plugin);
			names.addElement(plugin.getName());
		}
		catch (ClassNotFoundException e) {
			System.err.println("Non è stato possibile caricare la classe!");
		}
	}
	
	/* Getter per la lista dei nomi */
	public static DefaultListModel<String> getNames()
	{
		return names;
	}
	
	/* Restituisce l'id del frattale selezionato */
	public static int search(String key)
	{
		for(int i = 0; i < names.size(); i++)
			if(names.getElementAt(i) == key)
				return i;
		return -1;
	}
}
