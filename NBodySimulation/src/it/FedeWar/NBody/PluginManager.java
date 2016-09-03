package it.FedeWar.NBody;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

/* Gestione dei plugin, la classe non può essere instanziata */
public abstract class PluginManager
{
	private	static DefaultListModel<String> names;		// Lista nomi frattali disponibili
	private	static ArrayList<Class<Simulation>> plugins;// Lista classi dei frattali
	
	/* Inizializza le liste */
	static
	{
		plugins = new ArrayList<Class<Simulation>>();
		names = new DefaultListModel<String>();
		loadBuiltinFractals();
	}
	
	/* Carica i frattali distribuiti col programma */
	@SuppressWarnings("unchecked")
	private static void loadBuiltinFractals()
	{
		String pluginsPath = "it.FedeWar.NBody2D.Engine.";
		try {
			plugins.add((Class<Simulation>)
				ClassLoader.getSystemClassLoader().loadClass(
					pluginsPath + "Engine_2D.Simulation_2D"));
			
			names.addElement("Simulazione 2D No GPU");
			
			plugins.add((Class<Simulation>)
					ClassLoader.getSystemClassLoader().loadClass(
						pluginsPath + "Engine_3D.Simulation_3D"));
				
			names.addElement("Simulazione 3D");
			
			/*plugins.add((Class<Simulation>)
					ClassLoader.getSystemClassLoader().loadClass(
						pluginsPath + "CUDA.Simulation_CUDA"));*/
				
			names.addElement("Simulazione CUDA");
		} catch (ClassNotFoundException e) {
			System.err.println("Impossibile caricare la classe: " + e.getMessage());
		}
	}
	
	/* Alloca un frattale per essere disegnato */
	public static Simulation create(int selected)
	{
		Simulation sim = null;
		
		try
		{
			sim = plugins.get(selected).newInstance();
		}
		catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Errore allocazione oggetto, è una simulazione valida?");
		}
		
		return sim;
	}
	
	/* Carica un nuovo frattale e lo aggiunge alla lista */
	@SuppressWarnings("unchecked")
	public static void add(String name)
	{
		Class<Simulation> plugin;
		try
		{
			plugin = (Class<Simulation>) ClassLoader.getSystemClassLoader().loadClass(name);
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
			if(names.get(i) == key)
				return i;
		return -1;
	}
}
