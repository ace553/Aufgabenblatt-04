package de.hawhh.informatik.sml.kino.werkzeuge.zahlung;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ZahlungsWerkzeugUI
{
	private static final String TITEL = "Zahlung";

	protected JDialog _dialog;

	protected JLabel _gesamtBetragLabel;
	protected JFormattedTextField _gezahltTextField;
	protected JLabel _nochZuZahlenLabel;

	protected JButton _okButton;
	protected JButton _abbrechenButton;

	public ZahlungsWerkzeugUI(JFrame owner)
	{
		_dialog = new JDialog(owner, TITEL, Dialog.ModalityType.DOCUMENT_MODAL);
		_dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		_dialog.getContentPane().setLayout(new BorderLayout());

		_dialog.getContentPane().add(erstelleButtonPanel(), BorderLayout.SOUTH);
		_dialog.getContentPane().add(erstelleRechnungsPanel(),
		        BorderLayout.CENTER);
	}

	/**
	 * Erstellt das Rechnungspanel.
	 * 
	 * @return Das erstellte Rechnungspanel
	 */
	private JComponent erstelleRechnungsPanel()
	{
		JComponent rechnungsPanel = new JPanel();
		rechnungsPanel
		        .setLayout(new BoxLayout(rechnungsPanel, BoxLayout.Y_AXIS));

		_gesamtBetragLabel = new JLabel("Gesamtbetrag: xx.xx €");

		JComponent gezahltPanel = new JPanel(new FlowLayout());
		JLabel gezahltLabel = new JLabel("Gezahlt: ");
		gezahltPanel.add(gezahltLabel);

		_gezahltTextField = new JFormattedTextField(
		        NumberFormat.getCurrencyInstance(Locale.GERMANY));
		_gezahltTextField.setMaximumSize(new Dimension(400, 20));
		_gezahltTextField.setValue(new Double(0));

		gezahltPanel.add(_gezahltTextField);

		_nochZuZahlenLabel = new JLabel("Noch zu zahlen: xx.xx €");

		rechnungsPanel.add(_gesamtBetragLabel);
		rechnungsPanel.add(gezahltPanel);
		rechnungsPanel.add(_nochZuZahlenLabel);
		return rechnungsPanel;
	}

	/**
	 * Erstellt das Button Panel
	 * 
	 * @return Das erstellte Button Panel
	 */
	private JComponent erstelleButtonPanel()
	{
		JComponent buttonPanel = new JPanel(new FlowLayout());
		_okButton = new JButton("OK");
		_okButton.setEnabled(false);
		_abbrechenButton = new JButton("Abbrechen");
		buttonPanel.add(_abbrechenButton);
		buttonPanel.add(_okButton);

		return buttonPanel;
	}

	/**
	 * Zeigt das Fenster an.
	 */
	protected void zeigeFenster()
	{
		_dialog.pack();
		_dialog.setVisible(true);
	}

	/**
	 * Schließt das Fenster.
	 */
	protected void schliesseFenster()
	{
		_dialog.setVisible(false);
	}
}
