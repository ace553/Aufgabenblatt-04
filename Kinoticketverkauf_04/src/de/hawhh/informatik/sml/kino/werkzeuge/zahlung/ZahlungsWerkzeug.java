package de.hawhh.informatik.sml.kino.werkzeuge.zahlung;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.werkzeuge.ObservableSubwerkzeug;

public class ZahlungsWerkzeug extends ObservableSubwerkzeug
{

	private ZahlungsWerkzeugUI _ui;
	private Geldbetrag _zahlungsBetrag;

	private boolean _bezahlt;

	/**
	 * Der Konstruktor des ZahlungsWerkzeug Ein neues Zahlunsgwerkzeug ist
	 * geschlossen und nicht bezahlt.
	 * 
	 * @param owner
	 *            Das Unterliegende Fenster, das während der Sichbarkeit des
	 *            Zahlungsdialogs gesperrt ist.
	 */
	public ZahlungsWerkzeug(JFrame owner)
	{
		_zahlungsBetrag = null;
		_ui = new ZahlungsWerkzeugUI(owner);
		_bezahlt = false;
		registriereUIAktionen();
	}

	/**
	 * Beginnt einen neuen Zahlvorgang mit dem gegebenen zahlungsBetrag Updated
	 * den Dialog. Zeigt den Dialog
	 * 
	 * @param zahlungsBetrag
	 *            Der Betrag, der gezahlt werden muss.
	 */
	public void beginneZahlvorgang(Geldbetrag zahlungsBetrag)
	{
		_zahlungsBetrag = zahlungsBetrag;
		_bezahlt = false;
		aktualisiereZahlungsBetragLabel();
		aktualisiereNochZuZahlenLabel();
		_ui.zeigeFenster();
	}

	/**
	 * Schließt das Fenster und informiert ueber die erfolgreiche Bezahlung
	 */
	private void reagiereAufOkButtonKlick()
	{
		_ui.versteckeFenster();
		_bezahlt = true;
		informiereUeberAenderung();
	}

	/**
	 * Schliesst das Fenster und informiert ueber die nicht erfolgreiche
	 * Bezahlung
	 */
	private void reagiereAufAbbrechenButtonKlick()
	{
		_ui.versteckeFenster();
		_bezahlt = false;
		informiereUeberAenderung();
	}

	/**
	 * Aktialisiert das noch zu Zahlen Label.
	 */
	private void reagiereAufTextEingabe()
	{
		aktualisiereNochZuZahlenLabel();
	}

	/**
	 * Aktualisiert das noch zu Zahlen Label nach dem Derzeitigen Inhalt des
	 * Textfeldes.
	 */
	private void aktualisiereNochZuZahlenLabel()
	{
		Geldbetrag iBetrag = Geldbetrag
		        .gibBetrag(_ui._gezahltTextField.getText());

		Geldbetrag nochZuZahlen = Geldbetrag
		        .substrahiereGeldbetrag(_zahlungsBetrag, iBetrag);

		if (nochZuZahlen.gibEurocentWert() > 0)
		{
			_ui._okButton.setEnabled(false);

			_ui._nochZuZahlenLabel.setText(
			        "Noch zu zahlen: " + nochZuZahlen.gibStringWert() + " €");
			_ui._nochZuZahlenLabel.setForeground(Color.red);
		} else
		{
			_ui._okButton.setEnabled(true);

			_ui._nochZuZahlenLabel.setText("Genug Bezahlt");
			_ui._nochZuZahlenLabel.setForeground(Color.green);
		}
	}

	/**
	 * Aktualisiert das Gesamtbetrag Label nach dem jetzigen Gesamtbetrag.
	 */
	private void aktualisiereZahlungsBetragLabel()
	{
		_ui._gesamtBetragLabel.setText(
		        "Gesamtbetrag: " + _zahlungsBetrag.gibStringWert() + " €");
	}

	/**
	 * Gibt an ob die Bezahlung erfogreich war.
	 * 
	 * @return true, wenn Bezahlung erfolgreich, sonst false.
	 */
	public boolean isBezahlt()
	{
		return _bezahlt;
	}

	/**
	 * Fuegt Funktionalität zu den Buttons hinzu und sorgt dafür, dass der
	 * reagiereAUfTexteingabe richtig aufgerufen wird.
	 */
	private void registriereUIAktionen()
	{

		_ui._okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				reagiereAufOkButtonKlick();
			}
		});

		_ui._abbrechenButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				reagiereAufAbbrechenButtonKlick();

			}
		});
		_ui._gezahltTextField.getDocument()
		        .addDocumentListener(new DocumentListener()
		        {

			        @Override
			        public void changedUpdate(DocumentEvent e)
			        {
				        reagiereAufTextEingabe();
			        }

			        @Override
			        public void insertUpdate(DocumentEvent e)
			        {
				        reagiereAufTextEingabe();
			        }

			        @Override
			        public void removeUpdate(DocumentEvent e)
			        {
				        reagiereAufTextEingabe();
			        }
		        });

		_ui._dialog.addComponentListener(new ComponentListener()
		{
			@Override
			public void componentHidden(ComponentEvent arg0)
			{
				reagiereAufAbbrechenButtonKlick();

			}
			
			
			@Override
			public void componentShown(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
	}
}
