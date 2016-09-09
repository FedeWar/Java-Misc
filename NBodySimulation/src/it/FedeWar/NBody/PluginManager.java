package it.FedeWar.NBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import it.FedeWar.NBody.Engine.Simulation;

/* Gestione dei plugin, la classe non può essere instanziata */
public abstract class PluginManager
{
	private	static DefaultListModel<String> names;		// Lista nomi frattali disponibili
	private	static ArrayList<Class<Simulation>> plugins;// Lista classi dei frattali
	private static ClassLoader stdLoader;
	
	/* Inizializza i campi statici*/
	static
	{
		stdLoader = Applicazione.class.getClassLoader();
		plugins = new ArrayList<Class<Simulation>>();
		names = new DefaultListModel<String>();
		loadBuiltin();
	}
	
	/* Carica le simulazioni distribuite col programma */
	private static void loadBuiltin()
	{
		final String pluginsPath = "it.FedeWar.NBody.Engine.";
		final String file = "/it/FedeWar/NBody/res/plugins";
		
		try {
			// Apre il file con la lista dei plugins
			InputStream plugins = PluginManager.class.getResourceAsStream(file);
			
			int start = 0;
			int end = 1;
			// Legge il file
			String data = new String();
			int c = 0;
			while((c = plugins.read()) != -1)
				data += (char) c;
			
			// Divide il file in token separati da ';', ogni
			// token è un percorso di un plugin
			while((end = data.indexOf(';', start + 1)) != -1)
			{
				add(pluginsPath + data.substring(start, end));
				start = end + 1;
			}
			// Chiude lo stream
			plugins.close();
		}
		catch (IOException e) {
			System.err.println("Errore nella lettura della lista dei plugins.");
		}
	}
	
	/* Alloca un plugins per essere eseguito */
	public static Simulation create(int selected)
	{
		Simulation sim = null;
		
		try {
			sim = plugins.get(selected).newInstance();
		}
		catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Errore allocazione oggetto, è una simulazione valida?");
		}
		
		return sim;
	}
	
	/* Carica una nuova simulazione e la aggiunge alla lista */
	@SuppressWarnings("unchecked")
	public static void add(String name)
	{
		Class<Simulation> plugin;
		try {
			plugin = (Class<Simulation>) stdLoader.loadClass(name);
			plugins.add(plugin);
			names.addElement((String) plugin.getField("name").get(null));
		}
		catch (ClassNotFoundException e) {
			System.err.println("Impossibile caricare la classe: " + e.getMessage());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			System.err.println("La classe non presenta un nome.");
		} catch (SecurityException e) {
			System.err.println("Security violation.");
		} catch (IllegalArgumentException e) {
			System.err.println("Illegal argument.");
		}
	}
	
	/* Getter per la lista dei nomi */
	public static DefaultListModel<String> getNames() {
		return names;
	}
	
	/* Restituisce l'id del plugin selezionato */
	public static int search(String key)
	{
		for(int i = 0; i < names.size(); i++)
			if(names.get(i) == key)
				return i;
		return -1;
	}
}
