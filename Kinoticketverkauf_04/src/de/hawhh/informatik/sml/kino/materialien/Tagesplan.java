package de.hawhh.informatik.sml.kino.materialien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import de.hawhh.informatik.sml.kino.fachwerte.Datum;
import de.hawhh.informatik.sml.kino.services.VorstellungsService;

/**
 * Ein Tagesplan verzeichnet alle Vorstellungen, die in dem Kino an einem
 * bestimmten Tag laufen.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2017
 */
public class Tagesplan
{
    private Datum _tag;
    private List<VorstellungsService> _vorstellungen;

    // Der Comparator wird zur Sortierung der Vorstellungen innerhalb eines
    // Tagesplans verwendet.
    private static Comparator<VorstellungsService> _vergleichNachAnfangszeit = new Comparator<VorstellungsService>()
    {
        @Override
        public int compare(VorstellungsService v1, VorstellungsService v2)
        {
            assert v1 != null : "Vorbedingung verletzt: v1 != null";
            assert v2 != null : "Vorbedingung verletzt: v2 != null";

            // Der Vergleich der Anfangszeiten reicht hier aus, weil in
            // einem Tagesplan sowieso nur Vorstellungen mit gleichem Datum
            // laufen.
            return v1.getAnfangszeit().compareTo(v2.getAnfangszeit());
        }
    };

    /**
     * Initialisiert einen neuen, leeren Tagesplan.
     * 
     * @param tag
     *            der Tag.
     * 
     * @require tag != null
     */
    public Tagesplan(Datum tag)
    {
        assert tag != null : "Vorbedingung verletzt: tag != null";

        _tag = tag;
        _vorstellungen = new ArrayList<VorstellungsService>();
    }

    /**
     * Gibt das Datum zurück, für das dieser Tagesplan gilt.
     * 
     * @ensure result != null
     */
    public Datum getDatum()
    {
        return _tag;
    }

    /**
     * Fügt diesem Tagesplan eine Vorstellung hinzu.
     * 
     * @param v
     *            die Vorstellung.
     * 
     * @require v != null
     * @require die Vorstellung laeuft an dem Tag dieses Tagesplans
     */
    public void fuegeVorstellungHinzu(Vorstellung v)
    {
        assert v != null : "Vorbedingung verletzt: v != null";
        assert v.getDatum().equals(_tag) : "Vorbedingung verletzt: v.getDatum().equals(_tag)";

        _vorstellungen.add(new VorstellungsService(v));
    }

    /**
     * Gibt alle Vorstellungen des Tages zurück. Die Vorstellungen werden
     * sortiert nach ihrer Anfangszeit zurückgegeben.
     * 
     * @ensure result != null
     */
    public List<VorstellungsService> getVorstellungen()
    {
        List<VorstellungsService> result = new ArrayList<VorstellungsService>(_vorstellungen);
        Collections.sort(result, _vergleichNachAnfangszeit);
        return result;
    }
}
