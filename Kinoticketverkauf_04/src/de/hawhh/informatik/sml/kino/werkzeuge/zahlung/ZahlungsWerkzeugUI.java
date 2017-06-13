package de.hawhh.informatik.sml.kino.werkzeuge.zahlung;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ZahlungsWerkzeugUI
{
	private static final String TITEL = "Zahlung";
	private static final int WIDTH = 200, HEIGHT = 130;

	protected JDialog _dialog;

	protected JLabel _gesamtBetragLabel;
	protected JTextField _gezahltTextField;
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
		JLabel euroLabel = new JLabel("€");
		gezahltPanel.add(gezahltLabel);

		_gezahltTextField = new JTextField("");
		_gezahltTextField.setPreferredSize(new Dimension(70, 20));

		gezahltPanel.add(_gezahltTextField);
		gezahltPanel.add(euroLabel);

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
	 * Setzt das Fenster in die mitte des owner fensters und
	 * zeigt das Fenster.
	 */
	protected void zeigeFenster()
	{
		_dialog.setBounds(_dialog.getOwner().getX()+(_dialog.getOwner().getWidth()/2) - WIDTH/2, _dialog.getOwner().getY()+ (_dialog.getOwner().getHeight()/2) - HEIGHT/2, WIDTH, HEIGHT);
		_dialog.setResizable(false);
		_dialog.setVisible(true);
	}

	/**
	 * Schließt das Fenster.
	 */
	protected void versteckeFenster()
	{
		_gezahltTextField.setText("");
		_dialog.setVisible(false);
	}
}
