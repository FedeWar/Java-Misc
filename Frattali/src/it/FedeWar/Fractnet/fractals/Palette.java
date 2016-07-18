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
package it.FedeWar.Fractnet.fractals;

/**
 * @author FedeWar
 * @version 1.0, 18/07/2016
 * 
 * Classe base per l'implementazione di palette di colori.
 */
public abstract class Palette
{
	/**
	 * Calcola il valore del canale rosso nel punto x,
	 * il metodo deve essere sovraccaricato con la dichiarazione
	 * di un'altra classe che definisca una funzione matematica
	 * precisa per la generazione del colore.
	 * 
	 * @version 1.0, 18/07/2016
	 * @since 1.0, 18/07/2016
	 * 
	 * @param x Il punto in cui calcolare il colore
	 * @return Il valore del canale rosso nel punto x: [0, 256)
	 */
	public abstract int getRed(int x);
	
	/**
	 * Vedere la descrizione di {@link getRed}
	 * @version 1.0, 18/07/2016
	 * @since 1.0, 18/07/2016
	 * 
	 * @param x Il punto in cui calcolare il colore
	 * @return Il valore del canale verde nel punto x: [0, 256)
	 * */
	public abstract int getGreen(int x);
	
	/**
	 * Vedere la descrizione di {@link getRed}
	 * @version 1.0, 18/07/2016
	 * @since 1.0, 18/07/2016
	 * 
	 * @param x Il punto in cui calcolare il colore
	 * @return Il valore del canale blu nel punto x: [0, 256)
	 * */
	public abstract int getBlue(int x);
	
	/**
	 * Calcola il valore del colore in formato RGB 32 bit
	 * nel punto x, viene generata un bitmask con i tre canali,
	 * 8 bit ciascuno, in posizione: rosso 16, verde 8, blu 0.
	 * 
	 * @version 1.0, 18/07/2016
	 * @since 1.0, 18/07/2016
	 * 
	 * @param x Il punto in cui calcolare il colore
	 * @return Il colore nel punto x in formato RGB a 32 bit
	 */
	public int getRGB(int x)
	{
		return (255 << 24) |		// Canale Alpha, ignorato
			(getRed(x) << 16) |		// Rosso
			(getGreen(x) << 8) |	// Verde
			 getBlue(x);			// Blu
	}
}
