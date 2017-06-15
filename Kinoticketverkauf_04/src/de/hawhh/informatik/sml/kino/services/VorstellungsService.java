package de.hawhh.informatik.sml.kino.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.hawhh.informatik.sml.kino.fachwerte.Datum;
import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.fachwerte.Platz;
import de.hawhh.informatik.sml.kino.fachwerte.Uhrzeit;
import de.hawhh.informatik.sml.kino.materialien.Film;
import de.hawhh.informatik.sml.kino.materialien.Kinosaal;
import de.hawhh.informatik.sml.kino.materialien.Vorstellung;

public class VorstellungsService extends AbstractObservableService
{

	private Vorstellung _vorstellung;

	private int[][] _gesperrtVon;

	public VorstellungsService(Vorstellung vorstellung)
	{
		_vorstellung = vorstellung;
		_gesperrtVon = new int[_vorstellung.getKinosaal().getAnzahlReihen()][_vorstellung.getKinosaal()
		                                   		        .getAnzahlSitzeProReihe()];

	}
	
	
	public synchronized boolean istNichtGesperrt(Platz platz)
	{
		return _gesperrtVon[platz.getReihe()][platz.getSitz()] == 0;
	}
	
	public synchronized boolean istGesperrtVon(Platz platz, int werkzeugID)
	{
		return _gesperrtVon[platz.getReihe()][platz.getSitz()] == werkzeugID;
	}
	
	private synchronized void sperrePlatz(Platz platz, int werkzeugID)
	{
		_gesperrtVon[platz.getReihe()][platz.getSitz()] = werkzeugID;
	}
	
	private void gebePlatzVonWerkzeugFrei(Platz platz, int werkzeugID)
	{
		if (istGesperrtVon(platz, werkzeugID))
			gebePlatzFrei(platz);
	}
	
	public synchronized void gebePlatzFrei(Platz platz)
	{
		_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
	}

	/**
	 * Prüft ob ein Platz für ein ein Platzverkaufswerkzeug gesperrt ist.
	 * 
	 * @param platz
	 *            Der zu prüfende Platz
	 * @param werkzeugID
	 *            Die WerkzeugID
	 * @return True wenn Gesperrt sonst false
	 * 
	 * @require werkzeugID > 0
	 */
	public boolean istGesperrt(Platz platz, int werkzeugID)
	{
		assert werkzeugID > 0 : "Die WerkzeugID muss 1 oder hoeher sein";
		if (istNichtGesperrt(platz))
		{
			return false;
		} else if (!istGesperrtVon(platz, werkzeugID))
		{

			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Methode für Testfälle um zu pruefen ob richtig in den Tagesplan
	 * hinzugefuegt
	 * 
	 * @param vorstellung
	 * @return
	 */
	public boolean hatVorstellung(Vorstellung vorstellung)
	{
		return _vorstellung.equals(vorstellung);
	}

	/**
	 * Sperrt Plaetze fuer alle PlatzVerkaufsWerkzeuge außer das gegebene
	 * Informiert Observer über diese Änderung
	 * 
	 * @param plaetze
	 *            Die zu sperrenden Plaetze
	 * @param werkzeugID
	 *            Die werkzeug ID
	 *            
	 * @require werkzeugID > 0
	 */
	public void sperrePlaetze(Collection<Platz> plaetze, int werkzeugID)
	{

		assert werkzeugID > 0 : "Die WerkzeugID muss 1 oder hoeher sein";
		for (Platz platz : plaetze)
		{
			sperrePlatz(platz, werkzeugID);
		}
		informiereUeberAenderung();
	}
	
	/**
	 * Verkauft den Platz und gibt ihn wieder fuer alle Platzverkaufswerkzeuge
	 * frei. Informiert OBserver über diese Änderung
	 * 
	 * @param platz
	 *            Der zu verkaufende Platz.
	 */
	public void verkaufePlatz(Platz platz)
	{
		_vorstellung.verkaufePlatz(platz);
		gebePlatzFrei(platz);
		informiereUeberAenderung();
	}

	/**
	 * Stroniert den PLatz und gibt ihn fuer alle PlatzVerkaufsWerkzeuge frei
	 * Informiert Observer über diese Änderung.
	 * 
	 * @param platz
	 *            Der zu stornierende Platz.
	 */
	public void stornierePlatz(Platz platz)
	{
		_vorstellung.stornierePlatz(platz);
		gebePlatzFrei(platz);
		informiereUeberAenderung();
	}

	/**
	 * Verkauft die Plaetze und gibt sie fuer alle PlatzVerkaufsWerkzeuge frei
	 * Informiert Observer über diese Änderung.
	 * 
	 * @param plaetze
	 *            Die zu verkaufenden Plaetze.
	 */
	public void verkaufePlaetze(Set<Platz> plaetze)
	{
		_vorstellung.verkaufePlaetze(plaetze);
		for (Platz platz : plaetze)
		{
			gebePlatzFrei(platz);
		}
		informiereUeberAenderung();
	}

	/**
	 * Storniert die Plaetze und gibt sie fuer alle PlatzVerkaufsWerkzeuge frei
	 * Informiert Observer über diese Änderung.
	 * 
	 * @param plaetze
	 *            Die zu stornierenden Plaetze.
	 */
	public void stornierePlaetze(Set<Platz> plaetze)
	{
		_vorstellung.stornierePlaetze(plaetze);
		for (Platz platz : plaetze)
		{
			gebePlatzFrei(platz);
		}
		informiereUeberAenderung();
	}

	/**
	 * Gibt den Platz frei wenn er von dieser WerkzeugID gesperrt ist.
	 * 
	 * @param plaetze
	 *            Die freizugebenen Plätze.
	 * @param werkzeugID
	 *            Die werkzeugID
	 *            
	 * @require werkzeugID > 0
	 */
	public void gebePlaetzeVonWerkzeugFrei(List<Platz> plaetze, int werkzeugID)
	{

		assert werkzeugID > 0 : "Die WerkzeugID muss 1 oder hoeher sein";
		for (Platz platz : plaetze)
		{
			gebePlatzVonWerkzeugFrei(platz, werkzeugID);
		}
		informiereUeberAenderung();
	}

	public Kinosaal getKinosaal()
	{
		return _vorstellung.getKinosaal();
	}

	public Film getFilm()
	{
		return _vorstellung.getFilm();
	}

	public Uhrzeit getAnfangszeit()
	{
		return _vorstellung.getAnfangszeit();
	}

	public Uhrzeit getEndzeit()
	{
		return _vorstellung.getEndzeit();
	}

	public Datum getDatum()
	{
		return _vorstellung.getDatum();
	}

	public Geldbetrag getPreis()
	{
		return _vorstellung.getPreis();
	}

	public boolean hatPlatz(Platz platz)
	{
		return _vorstellung.hatPlatz(platz);
	}

	public boolean hatPlaetze(Set<Platz> plaetze)
	{
		return _vorstellung.hatPlaetze(plaetze);
	}

	public Geldbetrag getPreisFuerPlaetze(Set<Platz> plaetze)
	{
		return _vorstellung.getPreisFuerPlaetze(plaetze);
	}

	public boolean istPlatzVerkauft(Platz platz)
	{
		return _vorstellung.istPlatzVerkauft(platz);
	}

	public int getAnzahlVerkauftePlaetze()
	{
		return _vorstellung.getAnzahlVerkauftePlaetze();
	}

	public boolean sindStornierbar(Set<Platz> plaetze)
	{
		return _vorstellung.sindStornierbar(plaetze);
	}

	public boolean sindVerkaufbar(Set<Platz> plaetze)
	{
		return _vorstellung.sindVerkaufbar(plaetze);
	}

	@Override
	public String toString()
	{
		return _vorstellung.toString();
	}
}
