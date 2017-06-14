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

	public VorstellungsService(Vorstellung vorstellung)
	{
		_vorstellung = vorstellung;

	}

	public boolean istGesperrt(Platz platz, int werkzeugID)
	{
		if (_vorstellung.istNichtGesperrt(platz))
		{
			return false;
		} else if (!_vorstellung.istGesperrtVon(platz, werkzeugID))
		{

			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * Methode für Testfälle um zu pruefen ob richtig in den Tagesplan hinzugefuegt
	 * @param vorstellung
	 * @return
	 */
	public boolean hatVorstellung(Vorstellung vorstellung)
	{
		return _vorstellung.equals(vorstellung);
	}

	public void sperrePlaetze(Collection<Platz> plaetze, int werkzeugID)
	{
		_vorstellung.sperrePlaetze(plaetze, werkzeugID);
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

	public void verkaufePlatz(Platz platz)
	{
		_vorstellung.verkaufePlatz(platz);
		_vorstellung.gebePlatzFrei(platz);
		informiereUeberAenderung();
	}

	public void stornierePlatz(Platz platz)
	{
		_vorstellung.stornierePlatz(platz);
		_vorstellung.gebePlatzFrei(platz);
		informiereUeberAenderung();
	}

	public int getAnzahlVerkauftePlaetze()
	{
		return _vorstellung.getAnzahlVerkauftePlaetze();
	}

	public void verkaufePlaetze(Set<Platz> plaetze)
	{
		_vorstellung.verkaufePlaetze(plaetze);
		for (Platz platz : plaetze)
		{
			_vorstellung.gebePlatzFrei(platz);
		}
		informiereUeberAenderung();
	}

	public boolean sindVerkaufbar(Set<Platz> plaetze)
	{
		return _vorstellung.sindVerkaufbar(plaetze);
	}

	public void stornierePlaetze(Set<Platz> plaetze)
	{
		_vorstellung.stornierePlaetze(plaetze);
		for (Platz platz : plaetze)
		{
			_vorstellung.gebePlatzFrei(platz);
		}
		informiereUeberAenderung();
	}

	public boolean sindStornierbar(Set<Platz> plaetze)
	{
		return _vorstellung.sindStornierbar(plaetze);
	}

	@Override
	public String toString()
	{
		return _vorstellung.toString();
	}

	public void gebePlaetzeVonWerkzeugFrei(List<Platz> plaetze, int wERKZEUG_ID)
	{
		_vorstellung.gebePlaetzeVonWerkzeugFrei(plaetze, wERKZEUG_ID);
		informiereUeberAenderung();
	}
}
