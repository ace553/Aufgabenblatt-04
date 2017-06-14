package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JPanel;

import de.hawhh.informatik.sml.kino.services.ServiceObserver;
import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.fachwerte.Platz;
import de.hawhh.informatik.sml.kino.materialien.Kinosaal;
import de.hawhh.informatik.sml.kino.services.VorstellungsService;
import de.hawhh.informatik.sml.kino.werkzeuge.ObservableSubwerkzeug;

/**
 * Mit diesem Werkzeug können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Material. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses Werkzeug ist ein eingebettetes Subwerkzeug. Es kann nicht beobachtet
 * werden.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2017
 */
public class PlatzVerkaufsWerkzeug extends ObservableSubwerkzeug
        implements ServiceObserver
{

	private static int werkzeugIDCounter = 0;
	private final int WERKZEUG_ID;
	// Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
	private VorstellungsService _vorstellung;

	private PlatzVerkaufsWerkzeugUI _ui;

	/**
	 * Initialisiert das PlatzVerkaufsWerkzeug.
	 */
	public PlatzVerkaufsWerkzeug()
	{
		WERKZEUG_ID = ++werkzeugIDCounter;
		_ui = new PlatzVerkaufsWerkzeugUI();
		registriereUIAktionen();
		// Am Anfang wird keine Vorstellung angezeigt:
		setVorstellung(null);
	}

	@Override
	public void reagiereAufAenderung()
	{
		if (_vorstellung != null)
		{
			for (Platz platz : _vorstellung.getKinosaal().getPlaetze())
			{
				_ui.getPlatzplan().sperrePlatz(platz,_vorstellung.istGesperrt(platz, WERKZEUG_ID));
			}

			for (Platz platz : _vorstellung.getKinosaal().getPlaetze())
			{
				if (_vorstellung.istPlatzVerkauft(platz))
				{
					_ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
				}
				else
				{
					_ui.getPlatzplan().markierePlatzAlsFrei(platz);
				}
			}
		}
	}

	/**
	 * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
	 * Kontextwerkzeug eingebettet werden.
	 * 
	 * @ensure result != null
	 */
	public JPanel getUIPanel()
	{
		return _ui.getUIPanel();
	}

	/**
	 * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
	 */
	private void registriereUIAktionen()
	{
		_ui.getDeselectButton().addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aktualisierePlatzplan();
				
			}
		});
		_ui.getVerkaufenButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				informiereUeberAenderung();
				// verkaufePlaetze(_vorstellung);
			}
		});

		_ui.getStornierenButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				stornierePlaetze(_vorstellung);
			}
		});

		_ui.getPlatzplan()
		        .addPlatzSelectionListener(new PlatzSelectionListener()
		        {
			        @Override
			        public void auswahlGeaendert(PlatzSelectionEvent event)
			        {
				        reagiereAufNeuePlatzAuswahl(
				                event.getAusgewaehltePlaetze());

			        }
		        });
	}

	/**
	 * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert
	 * hat.
	 * 
	 * @param plaetze
	 *            die jetzt ausgewählten Plätze.
	 */
	private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze)
	{
		_ui.getVerkaufenButton().setEnabled(istVerkaufenMoeglich(plaetze));
		_ui.getStornierenButton().setEnabled(istStornierenMoeglich(plaetze));
		_ui.getDeselectButton().setEnabled(!plaetze.isEmpty());
		if (_vorstellung != null)
		{
			_vorstellung.gebePlaetzeVonWerkzeugFrei(_vorstellung.getKinosaal().getPlaetze(), WERKZEUG_ID);
			_vorstellung.sperrePlaetze(plaetze, WERKZEUG_ID);
		}
		aktualisierePreisanzeige(plaetze);
	}

	/**
	 * Aktualisiert den anzuzeigenden Gesamtpreis
	 */
	private void aktualisierePreisanzeige(Set<Platz> plaetze)
	{

		if (istVerkaufenMoeglich(plaetze))
		{
			Geldbetrag preis = _vorstellung.getPreisFuerPlaetze(plaetze);
			_ui.getPreisLabel()
			        .setText("Gesamtpreis: " + preis.gibStringWert() + " €");
		} else
		{
			_ui.getPreisLabel().setText("Gesamtpreis:");
		}
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle storniert werden können.
	 */
	private boolean istStornierenMoeglich(Set<Platz> plaetze)
	{
		return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle verkauft werden können.
	 */
	private boolean istVerkaufenMoeglich(Set<Platz> plaetze)
	{
		return !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
	}

	/**
	 * Setzt die Vorstellung. Sie ist das Material dieses Werkzeugs. Wenn die
	 * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
	 * Vorstellung darf auch null sein.
	 */
	public void setVorstellung(VorstellungsService vorstellung)
	{
		if (_vorstellung != null)
		{
			_vorstellung.gebePlaetzeVonWerkzeugFrei(_vorstellung.getKinosaal().getPlaetze(), WERKZEUG_ID);
			_vorstellung.entferneBeobachter(this);
		}
		_vorstellung = vorstellung;
		if (_vorstellung != null)
			_vorstellung.registriereBeobachter(this);
		aktualisierePlatzplan();
	}

	/**
	 * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
	 */
	public void aktualisierePlatzplan()
	{
		if (_vorstellung != null)
		{
			Kinosaal saal = _vorstellung.getKinosaal();
			_ui.getPlatzplan().setAnzahlPlaetze(saal.getAnzahlReihen(),
			        saal.getAnzahlSitzeProReihe());

			for (Platz platz : saal.getPlaetze())
			{
				if (_vorstellung.istPlatzVerkauft(platz))
				{
					_ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
				}
			}
		} else
		{
			_ui.getPlatzplan().setAnzahlPlaetze(0, 0);
		}
	}

	/**
	 * Verkauft die ausgewählten Plaetze.
	 */
	public void verkaufeAusgewaehltePlaetze()
	{
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		_vorstellung.verkaufePlaetze(plaetze);
		aktualisierePlatzplan();
	}

	/**
	 * Storniert die ausgewählten Plaetze.
	 */
	private void stornierePlaetze(VorstellungsService vorstellung)
	{
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		vorstellung.stornierePlaetze(plaetze);
		aktualisierePlatzplan();
	}

	public Geldbetrag getPreisFuerAusgewähltePlaetze()
	{
		return _vorstellung.getPreisFuerPlaetze(
		        _ui.getPlatzplan().getAusgewaehltePlaetze());
	}
}
