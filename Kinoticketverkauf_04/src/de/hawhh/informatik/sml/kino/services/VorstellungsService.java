package de.hawhh.informatik.sml.kino.services;

import java.util.Collection;
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
		_gesperrtVon = new int[_vorstellung.getKinosaal()
		        .getAnzahlReihen()][_vorstellung.getKinosaal()
		                .getAnzahlSitzeProReihe()];
	}

	public boolean istGesperrt(Platz platz, int werkzeugID)
	{
		if (_gesperrtVon[platz.getReihe()][platz.getSitz()] == 0)
			return false;
		else if (_gesperrtVon[platz.getReihe()][platz.getSitz()] != werkzeugID)
			return true;
		else
			return false;
	}

	private void sperrePlatz(Platz platz, int werkzeugID)
	{
		_gesperrtVon[platz.getReihe()][platz.getSitz()] = werkzeugID;
	}

	public void gebePlaetzeVonWerkzeugFrei(Collection<Platz> plaetze,
	        int werkzeugID)
	{
		for (Platz platz : plaetze)
		{
			gebePlatzVonWerkzeugFrei(platz, werkzeugID);
		}
	}
	
	private void gebePlatzVonWerkzeugFrei(Platz platz, int werkzeugID)
	{
		if(_gesperrtVon[platz.getReihe()][platz.getSitz()] == werkzeugID)
			_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
	}

	public void sperrePlaetze(Collection<Platz> plaetze, int werkzeugID)
	{

		for (Platz platz : plaetze)
		{
			sperrePlatz(platz, werkzeugID);
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

	public void verkaufePlatz(Platz platz)
	{
		_vorstellung.verkaufePlatz(platz);
		_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
		informiereUeberAenderung();
	}

	public void stornierePlatz(Platz platz)
	{
		_vorstellung.stornierePlatz(platz);
		_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
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
			_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
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
			_gesperrtVon[platz.getReihe()][platz.getSitz()] = 0;
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
}
