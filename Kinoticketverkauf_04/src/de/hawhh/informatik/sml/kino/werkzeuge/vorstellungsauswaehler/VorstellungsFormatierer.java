package de.hawhh.informatik.sml.kino.werkzeuge.vorstellungsauswaehler;

import de.hawhh.informatik.sml.kino.materialien.Vorstellung;
import de.hawhh.informatik.sml.kino.services.VorstellungsService;

/**
 * Formatierer für eine {@link Vorstellung}.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2017
 */
class VorstellungsFormatierer
{
	private VorstellungsService _vorstellung;

	/**
	 * Initialisiert einen Formatierer für die angegebene Vorstellung.
	 * 
	 * @param vorstellung
	 *            die Vorstellung, die von diesem Formatierer dargestellt wird.
	 */
	public VorstellungsFormatierer(VorstellungsService vorstellung)
	{
		_vorstellung = vorstellung;
	}

	/**
	 * Gibt die Vorstellung zurück, die von diesem Formatierer dargestellt wird.
	 */
	VorstellungsService getVorstellung()
	{
		return _vorstellung;
	}

	@Override
	public String toString()
	{
		return _vorstellung.getAnfangszeit().getFormatiertenString() + " - "
		        + _vorstellung.getFilm().getFormatiertenString() + ", "
		        + _vorstellung.getKinosaal().getName();
	}
}
