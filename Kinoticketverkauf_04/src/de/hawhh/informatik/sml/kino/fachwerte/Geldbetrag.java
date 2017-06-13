package de.hawhh.informatik.sml.kino.fachwerte;

import java.util.regex.Pattern;

/**
 * Ein Geldbetrag, der aus einem Euro und einem Cent teil besteht.
 */
public final class Geldbetrag
{
	private final long _eurocent;

	/**
	 * Der Konstruktor der Klasse Geldbetrag.
	 */
	private Geldbetrag(long eurocent)
	{
		_eurocent = eurocent;
	}

	/**
	 * Gibt einen Geldbetrag, wo euro und cent getrennt eingegeben werden.
	 * 
	 * @param Den
	 *            Euro Betrag
	 * @param Den
	 *            Cent Betrag
	 * @param Betrag
	 *            positiv? true oder false.
	 * @return Der Erzeugte Geldbetrag
	 * @require euro >= 0
	 * @require cent >= 0
	 */
	public static Geldbetrag gibBetrag(int euro, int cent)
	{
		assert euro >= 0 : "Vorbedingung verletzt: euro negativ)";
		assert cent >= 0 : "Vorbedingung verletzt: cent negativ)";

		return new Geldbetrag(euro * 100 + cent);
	}

	/**
	 * Gibt einen Geldbtetrag der in Cent angegeben wird.
	 * 
	 * @param Den
	 *            Geldwert in Eurocent
	 * @return Der Erzeugte Geldbetrag
	 */
	public static Geldbetrag gibBetrag(long eurocent)
	{
		assert eurocent >= 0 : "Vorbedingung verletzt: eurocent negativ)";

		return new Geldbetrag(eurocent);
	}

	/**
	 * Gibt einen Geldbtetrag der als Zeichenkette eingegeben wird.
	 * 
	 * @param Die
	 *            eingegebene Zeichenkette im Format "Euro,Cent" Cent muss 2
	 *            stellig sein!
	 * @return Der Erzeugte Geldbetrag
	 * @require Pattern.matches(".*,..",wert);
	 */
	public static Geldbetrag gibBetrag(String wert)
	{
		assert Pattern.matches("\\d*,\\d\\d",
		        wert) : "Vorbedingung verletzt: Cent nicht zweistellig (führende 0 vorhanden?)";

		if (!wert.matches("\\d*([,.]\\d?\\d?)?"))
			return Geldbetrag.gibBetrag(0);

		String[] sWert = wert.replace(".", ",").split(",");
		if(sWert.length == 0)
		{
			sWert = new String[]{"0"};
		}
		if (sWert[0].length() > 7)
			sWert[0] = "10000000";
		if (sWert[0].length() == 0)
			sWert[0] = "0";
		
		int cent = 0;
		if (sWert.length > 1)
		{
			if (sWert[1].length() == 1)
				sWert[1] += "0";
			cent = Integer.valueOf(sWert[1]);
		}
		int euro = Integer.valueOf(sWert[0]);
		return Geldbetrag.gibBetrag(euro, cent);
	}

	/**
	 * Addiert zwei Geldbeträge.
	 * 
	 * @param Erster
	 *            Summand als Geldbetrag
	 * @param Zweiter
	 *            Summand als Geldbetrag
	 * @return Ein Geldbetrag aus der Summe der Geldbeträge
	 * @require a != null;
	 * @require b != null;
	 */
	public static Geldbetrag addiereGeldbetrag(Geldbetrag a, Geldbetrag b)
	{
		assert a != null : "Vorbedingung verletzt: Geldbetrag a ist Null";
		assert b != null : "Vorbedingung verletzt: Geldbetrag b ist Null";
		return Geldbetrag.gibBetrag(a.gibEurocentWert() + b.gibEurocentWert());
	}

	/**
	 * Substrahiert zwei Geldbeträge.
	 * 
	 * @param Erster
	 *            Subtrahend als Geldbetrag
	 * @param Zweiter
	 *            Subtrahend als Geldbetrag
	 * @return Ein Geldbetrag aus der Differenz der Geldbeträge
	 * @require a != null;
	 * @require b != null;
	 */
	public static Geldbetrag substrahiereGeldbetrag(Geldbetrag a, Geldbetrag b)
	{
		assert a != null : "Vorbedingung verletzt: Geldbetrag a ist Null";
		assert b != null : "Vorbedingung verletzt: Geldbetrag b ist Null";
		long eurocent = a.gibEurocentWert() - b.gibEurocentWert();
		if (eurocent < 0)
		{
			eurocent = 0;
		}
		return Geldbetrag.gibBetrag(eurocent);
	}

	/**
	 * Multipliziert einen Geldbetrag mit einem ganzzahligen Faktor.
	 * 
	 * @param Der
	 *            ganzzahlige Faktor
	 * @param Zweiter
	 *            Faktor als Geldbetrag
	 * @return Ein Geldbetrag aus der Multiplikation eines Geldbetrages und
	 *         einer Zahl
	 * @require betrag != null;
	 */
	public static Geldbetrag multipliziereBetrag(int faktor, Geldbetrag betrag)
	{
		assert betrag != null : "Vorbedingung verletzt: Geldbetrag betrag ist Null";
		assert faktor >= 0 : "Vorbedingung verletzt: faktor negativ)";

		long wert = betrag.gibEurocentWert();
		return Geldbetrag.gibBetrag(faktor * wert);
	}

	/**
	 * Gibt den Wert des Geldbetrages als Zeichenkette.
	 * 
	 * @return Der Wert des Geldbetrags als String
	 */
	public String gibStringWert()
	{

		if (_eurocent % 100 > 9)
		{
			return _eurocent / 100 + "," + _eurocent % 100;
		} else
		{
			return _eurocent / 100 + "," + "0" + _eurocent % 100;
		}
	}

	/**
	 * Gibt den Wert des Geldbetrages als Cent-Betrag.
	 * 
	 * @return Der Wert des Geldbetrags in Eurocent
	 */
	public long gibEurocentWert()
	{
		return _eurocent;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean ergebnis = false;
		if (o instanceof Geldbetrag)
		{
			Geldbetrag betrag = (Geldbetrag) o;
			ergebnis = (this.gibEurocentWert() == betrag.gibEurocentWert());
		}
		return ergebnis;
	}

	@Override
	public int hashCode()
	{
		return (int) gibEurocentWert();
	}
}
