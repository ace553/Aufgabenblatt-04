package de.hawhh.informatik.sml.kino.fachwerte;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class GeldbetragTest
{
	
    @Test 
    public void testGibStringWert()
    {
       Geldbetrag a = Geldbetrag.gibBetrag(0,0);
       assertEquals(a.gibStringWert(),"0,00");
       Geldbetrag b = Geldbetrag.gibBetrag(1,0);
       assertEquals(b.gibStringWert(),"1,00");
       Geldbetrag c = Geldbetrag.gibBetrag(0,1);
       assertEquals(c.gibStringWert(),"0,01");
       Geldbetrag d = Geldbetrag.gibBetrag(1,1);
       assertEquals(d.gibStringWert(),"1,01");
       Geldbetrag e = Geldbetrag.gibBetrag(32,21);
       assertEquals(e.gibStringWert(),"32,21");
    }

    @Test
    public void testAddiereGeldbetrag()
    {
       Geldbetrag b = Geldbetrag.gibBetrag(1,22);
       Geldbetrag a = Geldbetrag.gibBetrag(1,00);
       Geldbetrag ergebnis1 = Geldbetrag.addiereGeldbetrag(a,b);
       assertEquals(ergebnis1.gibStringWert(),"2,22");
       
       Geldbetrag d = Geldbetrag.gibBetrag(12,34);
       Geldbetrag c = Geldbetrag.gibBetrag(0,67);
       Geldbetrag ergebnis2 = Geldbetrag.addiereGeldbetrag(c,d);
       assertEquals(ergebnis2.gibStringWert(),"13,01");
    }
    
    @Test
    public void testSubtrahiereGeldbetrag()
    {
       Geldbetrag b = Geldbetrag.gibBetrag(1,22);
       Geldbetrag a = Geldbetrag.gibBetrag(1,0);
       Geldbetrag ergebnis1 = Geldbetrag.substrahiereGeldbetrag(a,b);
       assertEquals(ergebnis1.gibStringWert(),"0,00");
       
       Geldbetrag d = Geldbetrag.gibBetrag(1,12);
       Geldbetrag c = Geldbetrag.gibBetrag(12,34);
       Geldbetrag ergebnis2 = Geldbetrag.substrahiereGeldbetrag(c,d);
       assertEquals(ergebnis2.gibStringWert(),"11,22");
       
       Geldbetrag f = Geldbetrag.gibBetrag(1,40);
       Geldbetrag e = Geldbetrag.gibBetrag(31,36);
       Geldbetrag ergebnis3 = Geldbetrag.substrahiereGeldbetrag(e,f);
       assertEquals(ergebnis3.gibStringWert(),"29,96");
    }
    
    @Test
    public void testMultipliziereBetrag()
    {
       Geldbetrag a = Geldbetrag.gibBetrag(1,22);
       Geldbetrag ergebnis1 = Geldbetrag.multipliziereBetrag(1,a);
       assertEquals(ergebnis1.gibStringWert(),"1,22");
       
       Geldbetrag b = Geldbetrag.gibBetrag(12,34);
       Geldbetrag ergebnis2 = Geldbetrag.multipliziereBetrag(2,b);
       assertEquals(ergebnis2.gibStringWert(),"24,68");
       
       Geldbetrag c = Geldbetrag.gibBetrag(1,22);
       Geldbetrag ergebnis3 = Geldbetrag.multipliziereBetrag(1,c);
       assertEquals(ergebnis3.gibStringWert(),"1,22");
       
       Geldbetrag d = Geldbetrag.gibBetrag(1,27);
       Geldbetrag ergebnis4 = Geldbetrag.multipliziereBetrag(0,d);
       assertEquals(ergebnis4.gibStringWert(),"0,00");
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
       

       
       Geldbetrag e = Geldbetrag.gibBetrag("0,00");
       assertEquals(e.gibStringWert(),"0,00");
       
       Geldbetrag f = Geldbetrag.gibBetrag("10000000000000000,00");
       assertEquals(f.gibStringWert(),"10000000,00");
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

       Geldbetrag e = Geldbetrag.gibBetrag(299);
       assertEquals(e.gibStringWert(),"2,99");
       
       Geldbetrag f = Geldbetrag.gibBetrag(-0);
       assertEquals(f.gibStringWert(),"0,00");
       
       Geldbetrag g = Geldbetrag.gibBetrag(Integer.MAX_VALUE);
       assertEquals(g.gibStringWert(),"21474836,47");
    }
}
