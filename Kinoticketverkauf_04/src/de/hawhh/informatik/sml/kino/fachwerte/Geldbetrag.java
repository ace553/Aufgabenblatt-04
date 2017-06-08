package de.hawhh.informatik.sml.kino.fachwerte;

import java.util.regex.Pattern;

/**
 * Ein Geldbetrag, der aus einem Euro und einem Cent teil besteht.
 */ 
public final class Geldbetrag
{
    private final long _eurocent;
    
    /**
     *  Gibt einen Geldbetrag, wo euro und cent getrennt eingegeben werden.
     *  @param Den Euro Betrag
     *  @param Den Cent Betrag
     *  @param Betrag positiv? true oder false.
     *  @return Der Erzeugte Geldbetrag
     *  @require euro > -1
     *  @require cent > -1
     */
    public static Geldbetrag gibBetrag(int euro,int cent, boolean positiv)
    {
        assert euro > -1 : "Vorbedingung verletzt: euro negativ)";
        assert cent > -1 : "Vorbedingung verletzt: cent negativ)";
        if (positiv)
        {
            return new Geldbetrag(euro*100+cent);
        }
        else
        {
            return new Geldbetrag(-1*(euro*100+cent));
        }
    }
    
    /**
     *  Gibt einen Geldbtetrag der in Cent angegeben wird.
     *  @param Den Geldwert in Eurocent
     *  @return Der Erzeugte Geldbetrag
     */
    public static Geldbetrag gibBetrag(long eurocent)
    {
        return new Geldbetrag(eurocent);
    }
    
    /**
     *  Gibt einen Geldbtetrag der als Zeichenkette eingegeben wird.
     *  @param Die eingegebene Zeichenkette im Format "Euro,Cent" Cent muss 2 stellig sein!
     *  @return Der Erzeugte Geldbetrag
     *  @require Pattern.matches(".*,..",wert);
     */
    public static Geldbetrag gibBetrag(String wert)
    {
        assert Pattern.matches(".*,..",wert) : "Vorbedingung verletzt: Cent nicht zweistellig (führende 0 vorhanden?)";
        if (wert.indexOf('-') == -1) 
        {
            int cent = Integer.valueOf(wert.substring(wert.indexOf(',')+1));
            int euro = Integer.valueOf(wert.substring(0,wert.indexOf(',')));
            return Geldbetrag.gibBetrag(euro, cent,true);
        }
        else
        {
            int cent = Integer.valueOf(wert.substring(wert.indexOf(',')+1));
            int euro = Integer.valueOf(wert.substring(1,wert.indexOf(',')));
            return Geldbetrag.gibBetrag(euro, cent,false);
        }
    }
    
    /**
     *  Der Konstruktor der Klasse Geldbetrag.
     */
    private Geldbetrag(long eurocent)
    {
        _eurocent = eurocent;
    }
    
    /**
     * Addiert zwei Geldbeträge.
     *  @param Erster Summand als Geldbetrag
     *  @param Zweiter Summand als Geldbetrag
     *  @return Ein Geldbetrag aus der Summe der Geldbeträge
     *  @require a != null;
     *  @require b != null;
     */
    public static Geldbetrag addiereGeldbetrag(Geldbetrag a, Geldbetrag b)
    {
        assert a != null : "Vorbedingung verletzt: Geldbetrag a ist Null";
        assert b != null : "Vorbedingung verletzt: Geldbetrag b ist Null";
        return Geldbetrag.gibBetrag(a.gibEurocentWert()+b.gibEurocentWert());
    }
    
    /**
     * Substrahiert zwei Geldbeträge.
     *  @param Erster Subtrahend als Geldbetrag
     *  @param Zweiter Subtrahend als Geldbetrag
     *  @return Ein Geldbetrag aus der Differenz der Geldbeträge
     *  @require a != null;
     *  @require b != null;
     */
    public static Geldbetrag substrahiereGeldbetrag(Geldbetrag a, Geldbetrag b)
    {
        assert a != null : "Vorbedingung verletzt: Geldbetrag a ist Null";
        assert b != null : "Vorbedingung verletzt: Geldbetrag b ist Null";
        return Geldbetrag.gibBetrag(a.gibEurocentWert()-b.gibEurocentWert());
    }
    
    /**
     * Multipliziert einen Geldbetrag mit einem ganzzahligen Faktor.
     * 
     *  @param Der ganzzahlige Faktor 
     *  @param Zweiter Faktor als Geldbetrag
     *  @return Ein Geldbetrag aus der Multiplikation eines Geldbetrages und einer Zahl
     *   @require betrag != null;
     */
    public static Geldbetrag multipliziereBetrag(int faktor,Geldbetrag betrag)
    {
        assert betrag != null : "Vorbedingung verletzt: Geldbetrag betrag ist Null";
        long wert = betrag.gibEurocentWert();
        return Geldbetrag.gibBetrag(faktor*wert);
    }
    
    /**
     *  Gibt den Wert des Geldbetrages als Zeichenkette.
     *  @return Der Wert des Geldbetrags als String
     */
    public String gibStringWert()
    { 
        if (_eurocent > -1)
        {
            if (_eurocent%100 > 9)
            {
                return _eurocent/100 + "," + _eurocent%100;
            }
            else
            {
                return _eurocent/100 + "," + "0" + _eurocent%100;
            }
        }
        else
        {
            if (Math.abs(_eurocent)%100 > 9)
            {
                return  "-" + Math.abs(_eurocent/100) + "," + Math.abs(_eurocent%100);
            }
            else
            {
                return  "-" + Math.abs(_eurocent/100) + "," + "0" +  Math.abs(_eurocent%100);
            }
        }
    }
    
    /**
     * Gibt den Wert des Geldbetrages als Cent-Betrag.
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
        return (int) (1000 * gibEurocentWert());
    }
}
