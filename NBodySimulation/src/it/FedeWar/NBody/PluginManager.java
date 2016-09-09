package it.FedeWar.NBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import it.FedeWar.NBody.Engine.Simulation;

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
		loadBuiltin();
	}
	
	/* Carica le simulazioni distribuite col programma */
	private static void loadBuiltin()
	{
		String pluginsPath = "it.FedeWar.NBody.Engine.";
		String file = "/it/FedeWar/NBody/res/plugins";
		
		try {
			// Apre il file con la lista dei plugins
			FileReader plugins = new FileReader(new File(
					PluginManager.class.getResource(file).toURI()));
			BufferedReader br = new BufferedReader(plugins);
			
			int start = 0;
			int end = 1;
			// Il file è composto da una sola riga
			String data = br.readLine();
			// Divide il file in token separati da ';', ogni
			// token è un percorso di un plugin
			while((end = data.indexOf(';', start + 1)) != -1)
			{
				add(pluginsPath + data.substring(start, end));
				start = end + 1;
			}
			// Chiude lo stream
			br.close();
		}
		catch (URISyntaxException | FileNotFoundException e) {
			System.err.println("Errore nell'apertura della lista dei plugins.");
		} catch (IOException e) {
			System.err.println("Errore nella lettura della lista dei plugins.");
		}
	}
	
	/* Alloca un frattale per essere disegnato */
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
			plugin = (Class<Simulation>) ClassLoader.getSystemClassLoader().loadClass(name);
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
