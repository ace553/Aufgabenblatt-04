package de.hawhh.informatik.sml.kino.fachwerte;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class GeldbetragTest
{
    @Test 
    public void testGibStringWert()
    {
       Geldbetrag a = Geldbetrag.gibBetrag(0,0,true);
       assertEquals(a.gibStringWert(),"0,00");
       Geldbetrag b = Geldbetrag.gibBetrag(1,0,true);
       assertEquals(b.gibStringWert(),"1,00");
       Geldbetrag c = Geldbetrag.gibBetrag(0,1,true);
       assertEquals(c.gibStringWert(),"0,01");
       Geldbetrag d = Geldbetrag.gibBetrag(1,1,true);
       assertEquals(d.gibStringWert(),"1,01");
       Geldbetrag e = Geldbetrag.gibBetrag(32,21,true);
       assertEquals(e.gibStringWert(),"32,21");
       Geldbetrag f = Geldbetrag.gibBetrag(0,0,false);
       assertEquals(f.gibStringWert(),"0,00");
       Geldbetrag g = Geldbetrag.gibBetrag(1,0,false);
       assertEquals(g.gibStringWert(),"-1,00");
       Geldbetrag h = Geldbetrag.gibBetrag(0,1,false);
       assertEquals(h.gibStringWert(),"-0,01");
       Geldbetrag i = Geldbetrag.gibBetrag(1,1,false);
       assertEquals(i.gibStringWert(),"-1,01");
       Geldbetrag j = Geldbetrag.gibBetrag(32,21,false);
       assertEquals(j.gibStringWert(),"-32,21");
    }

    @Test
    public void testAddiereGeldbetrag()
    {
       Geldbetrag b = Geldbetrag.gibBetrag(1,22,true);
       Geldbetrag a = Geldbetrag.gibBetrag(1,00,true);
       Geldbetrag ergebnis1 = Geldbetrag.addiereGeldbetrag(a,b);
       assertEquals(ergebnis1.gibStringWert(),"2,22");
       
       Geldbetrag d = Geldbetrag.gibBetrag(12,34,true);
       Geldbetrag c = Geldbetrag.gibBetrag(0,67,true);
       Geldbetrag ergebnis2 = Geldbetrag.addiereGeldbetrag(c,d);
       assertEquals(ergebnis2.gibStringWert(),"13,01");
       
       Geldbetrag f = Geldbetrag.gibBetrag(1,22,false);
       Geldbetrag e = Geldbetrag.gibBetrag(31,36,false);
       Geldbetrag ergebnis3 = Geldbetrag.addiereGeldbetrag(e,f);
       assertEquals(ergebnis3.gibStringWert(),"-32,58");
       
       Geldbetrag h = Geldbetrag.gibBetrag(1,99,true);
       Geldbetrag g = Geldbetrag.gibBetrag(1,50,false);
       Geldbetrag ergebnis4 = Geldbetrag.addiereGeldbetrag(g,h);
       assertEquals(ergebnis4.gibStringWert(),"0,49");
       
       Geldbetrag j = Geldbetrag.gibBetrag(1,50,false);
       Geldbetrag i = Geldbetrag.gibBetrag(1,51,true);
       Geldbetrag ergebnis5 = Geldbetrag.addiereGeldbetrag(i,j);
       assertEquals(ergebnis5.gibStringWert(),"0,01");
    }
    
    @Test
    public void testSubtrahiereGeldbetrag()
    {
       Geldbetrag b = Geldbetrag.gibBetrag(1,22,true);
       Geldbetrag a = Geldbetrag.gibBetrag(1,0,true);
       Geldbetrag ergebnis1 = Geldbetrag.substrahiereGeldbetrag(a,b);
       assertEquals(ergebnis1.gibStringWert(),"-0,22");
       
       Geldbetrag d = Geldbetrag.gibBetrag(1,12,true);
       Geldbetrag c = Geldbetrag.gibBetrag(12,34,true);
       Geldbetrag ergebnis2 = Geldbetrag.substrahiereGeldbetrag(c,d);
       assertEquals(ergebnis2.gibStringWert(),"11,22");
       
       Geldbetrag f = Geldbetrag.gibBetrag(1,40,true);
       Geldbetrag e = Geldbetrag.gibBetrag(31,36,true);
       Geldbetrag ergebnis3 = Geldbetrag.substrahiereGeldbetrag(e,f);
       assertEquals(ergebnis3.gibStringWert(),"29,96");
       
       Geldbetrag h = Geldbetrag.gibBetrag(1,40,false);
       Geldbetrag g = Geldbetrag.gibBetrag(31,36,false);
       Geldbetrag ergebnis4 = Geldbetrag.substrahiereGeldbetrag(g,h);
       assertEquals(ergebnis4.gibStringWert(),"-29,96");
    }
    
    @Test
    public void testMultipliziereBetrag()
    {
       Geldbetrag a = Geldbetrag.gibBetrag(1,22,true);
       Geldbetrag ergebnis1 = Geldbetrag.multipliziereBetrag(1,a);
       assertEquals(ergebnis1.gibStringWert(),"1,22");
       
       Geldbetrag b = Geldbetrag.gibBetrag(12,34,true);
       Geldbetrag ergebnis2 = Geldbetrag.multipliziereBetrag(2,b);
       assertEquals(ergebnis2.gibStringWert(),"24,68");
       
       Geldbetrag c = Geldbetrag.gibBetrag(1,22,true);
       Geldbetrag ergebnis3 = Geldbetrag.multipliziereBetrag(-1,c);
       assertEquals(ergebnis3.gibStringWert(),"-1,22");
       
       Geldbetrag d = Geldbetrag.gibBetrag(1,27,true);
       Geldbetrag ergebnis4 = Geldbetrag.multipliziereBetrag(0,d);
       assertEquals(ergebnis4.gibStringWert(),"0,00");
       
       Geldbetrag e = Geldbetrag.gibBetrag(2,22,false);
       Geldbetrag ergebnis5 = Geldbetrag.multipliziereBetrag(-2,e);
       assertEquals(ergebnis5.gibStringWert(),"4,44");
    }
    
    @Test
    public void testGibBetragString()
    {
       Geldbetrag a = Geldbetrag.gibBetrag("1,22");
       assertEquals(a.gibStringWert(),"1,22");
       
       Geldbetrag b = Geldbetrag.gibBetrag("12,34");
       assertEquals(b.gibStringWert(),"12,34");
       
       Geldbetrag c = Geldbetrag.gibBetrag("3,66");
       assertEquals(c.gibStringWert(),"3,66");
       
       Geldbetrag d = Geldbetrag.gibBetrag("5,08");
       assertEquals(d.gibStringWert(),"5,08");
       
       Geldbetrag e = Geldbetrag.gibBetrag("-5,08");
       assertEquals(e.gibStringWert(),"-5,08");
       
       Geldbetrag f = Geldbetrag.gibBetrag("-0,00");
       assertEquals(f.gibStringWert(),"0,00");
    }
    
    @Test
    public void testGibBetragEurecent()
    {
       Geldbetrag a = Geldbetrag.gibBetrag(150);
       assertEquals(a.gibStringWert(),"1,50");
       
       Geldbetrag b = Geldbetrag.gibBetrag(1234);
       assertEquals(b.gibStringWert(),"12,34");
       
       Geldbetrag c = Geldbetrag.gibBetrag(0);
       assertEquals(c.gibStringWert(),"0,00");
       
       Geldbetrag d = Geldbetrag.gibBetrag(99);
       assertEquals(d.gibStringWert(),"0,99");

       Geldbetrag e = Geldbetrag.gibBetrag(-299);
       assertEquals(e.gibStringWert(),"-2,99");
       
       Geldbetrag f = Geldbetrag.gibBetrag(-0);
       assertEquals(f.gibStringWert(),"0,00");
    }
}
