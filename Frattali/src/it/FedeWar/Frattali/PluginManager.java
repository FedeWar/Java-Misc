package it.FedeWar.Frattali;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import it.FedeWar.Frattali.fractals.Frattale;

/* Gestione dei plugin, la classe non può essere instanziata */
public abstract class PluginManager
{
	private	static DefaultListModel<String> names;		// Lista nomi frattali disponibili
	private	static ArrayList<Class<Frattale>> plugins;	// Lista classi dei frattali
	
	/* Inizializza le liste */
	static
	{
		plugins = new ArrayList<Class<Frattale>>();
		names = new DefaultListModel<String>();
		loadBuiltinFractals();
	}
	
	/* Carica i frattali distribuiti col programma */
	@SuppressWarnings("unchecked")
	private static void loadBuiltinFractals()
	{
		String pluginsPath = "it.FedeWar.Frattali.fractals.plugins.";
		try {
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "Mandelbrot"));
			names.addElement("Mandelbrot");
			
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "Julia"));
			names.addElement("Julia");
			
			plugins.add((Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass(pluginsPath + "Sierpinski"));
			names.addElement("Sierpinski");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/* Alloca un frattale per essere disegnato */
	public static Frattale create(int selected, int width, int height)
	{
		try
		{
			// Crea, inizializza e restituisce un nuovo frattale
			Frattale fra = plugins.get(selected).newInstance();
			fra.Init(width, height);
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
		Class<Frattale> plugin;
		try
		{
			plugin = (Class<Frattale>) ClassLoader.getSystemClassLoader().loadClass(name);
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
