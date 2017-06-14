package de.hawhh.informatik.sml.kino.werkzeuge.kasse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.hawhh.informatik.sml.kino.fachwerte.Datum;
import de.hawhh.informatik.sml.kino.materialien.Kino;
import de.hawhh.informatik.sml.kino.materialien.Tagesplan;
import de.hawhh.informatik.sml.kino.services.VorstellungsService;
import de.hawhh.informatik.sml.kino.werkzeuge.SubwerkzeugObserver;
import de.hawhh.informatik.sml.kino.werkzeuge.datumsauswaehler.DatumAuswaehlWerkzeug;
import de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf.PlatzVerkaufsWerkzeug;
import de.hawhh.informatik.sml.kino.werkzeuge.vorstellungsauswaehler.VorstellungsAuswaehlWerkzeug;
import de.hawhh.informatik.sml.kino.werkzeuge.zahlung.ZahlungsWerkzeug;

/**
 * Das Kassenwerkzeug. Mit diesem Werkzeug kann die Benutzerin oder der Benutzer
 * eine Vorstellung auswählen und Karten für diese Vorstellung verkaufen und
 * stornieren.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2017
 */
public class KassenWerkzeug
{
	// Das Material dieses Werkzeugs
	private Kino _kino;

	// UI dieses Werkzeugs
	private KassenWerkzeugUI _ui;

	// Die Subwerkzeuge
	private PlatzVerkaufsWerkzeug _platzVerkaufsWerkzeug;
	private DatumAuswaehlWerkzeug _datumAuswaehlWerkzeug;
	private VorstellungsAuswaehlWerkzeug _vorstellungAuswaehlWerkzeug;
	private ZahlungsWerkzeug _zahlungsWerkzeug;

	/**
	 * Initialisiert das Kassenwerkzeug.
	 * 
	 * @param kino
	 *            das Kino, mit dem das Werkzeug arbeitet.
	 * 
	 * @require kino != null
	 */
	public KassenWerkzeug(Kino kino)
	{
		assert kino != null : "Vorbedingung verletzt: kino != null";

		_kino = kino;

		// Subwerkzeuge erstellen
		_platzVerkaufsWerkzeug = new PlatzVerkaufsWerkzeug();
		_datumAuswaehlWerkzeug = new DatumAuswaehlWerkzeug();
		_vorstellungAuswaehlWerkzeug = new VorstellungsAuswaehlWerkzeug();

		// UI erstellen (mit eingebetteten UIs der direkten Subwerkzeuge)
		_ui = new KassenWerkzeugUI(_platzVerkaufsWerkzeug.getUIPanel(),
		        _datumAuswaehlWerkzeug.getUIPanel(),
		        _vorstellungAuswaehlWerkzeug.getUIPanel());

		_zahlungsWerkzeug = new ZahlungsWerkzeug(_ui._frame);

		erzeugeListenerFuerSubwerkzeuge();
		registriereUIAktionen();
		setzeTagesplanFuerAusgewaehltesDatum();
		setzeAusgewaehlteVorstellung();

		_ui.zeigeFenster();
	}

	/**
	 * Erzeugt und registriert die Beobachter, die die Subwerkzeuge beobachten.
	 */
	private void erzeugeListenerFuerSubwerkzeuge()
	{
		_datumAuswaehlWerkzeug.registriereBeobachter(new SubwerkzeugObserver()
		{
			@Override
			public void reagiereAufAenderung()
			{
				setzeTagesplanFuerAusgewaehltesDatum();
			}
		});

		_vorstellungAuswaehlWerkzeug
		        .registriereBeobachter(new SubwerkzeugObserver()
		        {
			        @Override
			        public void reagiereAufAenderung()
			        {
				        setzeAusgewaehlteVorstellung();
			        }
		        });
		_platzVerkaufsWerkzeug.registriereBeobachter(new SubwerkzeugObserver()
		{

			@Override
			public void reagiereAufAenderung()
			{
				_zahlungsWerkzeug.beginneZahlvorgang(_platzVerkaufsWerkzeug
				        .getPreisFuerAusgewähltePlaetze());

			}
		});

		_zahlungsWerkzeug.registriereBeobachter(new SubwerkzeugObserver()
		{

			@Override
			public void reagiereAufAenderung()
			{
				zahlungAbgeschlossen();

			}

		});
	}

	/**
	 * Fügt die Funktionalitat zum Beenden-Button hinzu.
	 */
	private void registriereUIAktionen()
	{
		_ui.getBeendenButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				reagiereAufBeendenButton();
			}
		});
	}

	/**
	 * Setzt den in diesem Werkzeug angezeigten Tagesplan basierend auf dem
	 * derzeit im DatumsAuswahlWerkzeug ausgewählten Datum.
	 */
	private void setzeTagesplanFuerAusgewaehltesDatum()
	{
		Tagesplan tagesplan = _kino.getTagesplan(getAusgewaehltesDatum());
		_vorstellungAuswaehlWerkzeug.setTagesplan(tagesplan);
	}

	/**
	 * Passt die Anzeige an, wenn eine andere Vorstellung gewählt wurde.
	 */
	private void setzeAusgewaehlteVorstellung()
	{
		_platzVerkaufsWerkzeug.setVorstellung(getAusgewaehlteVorstellung());
	}

	/**
	 * Beendet die Anwendung.
	 */
	private void reagiereAufBeendenButton()
	{
		System.exit(0);
	}

	/**
	 * Gibt das derzeit gewählte Datum zurück.
	 */
	private Datum getAusgewaehltesDatum()
	{
		return _datumAuswaehlWerkzeug.getSelektiertesDatum();
	}

	/**
	 * Gibt die derzeit ausgewaehlte Vorstellung zurück. Wenn keine Vorstellung
	 * ausgewählt ist, wird <code>null</code> zurückgegeben.
	 */
	private VorstellungsService getAusgewaehlteVorstellung()
	{
		return _vorstellungAuswaehlWerkzeug.getAusgewaehlteVorstellung();
	}

	private void zahlungAbgeschlossen()
	{
		if (_zahlungsWerkzeug.isBezahlt())
		{
			_platzVerkaufsWerkzeug.verkaufeAusgewaehltePlaetze();
		}
	}
}
