package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import logic.LogicApi;

public class Gui {

	private JFrame frame;
	private JTextField accountRsField;
	private JTextArea challengeTextArea;
	private JPasswordField passphraseField;
	private JTextArea tokenTextArea;
	private JTextField tokenUrlField;
	private JTextArea accountVerificationTextArea;
	private JTextArea infoRequestTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
		initCustom();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JTabbedPane verificationPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, verificationPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, verificationPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, verificationPane, 373, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, verificationPane, 508, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(verificationPane);
		
		JPanel challengeTextRequest = new JPanel();
		verificationPane.addTab("Challenge Text Request", null, challengeTextRequest, null);
		SpringLayout sl_challengeTextRequest = new SpringLayout();
		challengeTextRequest.setLayout(sl_challengeTextRequest);
		
		JLabel accountRsLabel = new JLabel("Account:");
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, accountRsLabel, 15, SpringLayout.NORTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, accountRsLabel, 10, SpringLayout.WEST, challengeTextRequest);
		challengeTextRequest.add(accountRsLabel);
		
		accountRsField = new JTextField();
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, accountRsField, -264, SpringLayout.EAST, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, accountRsField, 10, SpringLayout.NORTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, accountRsField, -10, SpringLayout.EAST, challengeTextRequest);
		challengeTextRequest.add(accountRsField);
		accountRsField.setColumns(10);
		
		JLabel onlineResourceTypeLabel = new JLabel("Online Resource Type:");
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, onlineResourceTypeLabel, 15, SpringLayout.SOUTH, accountRsLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, onlineResourceTypeLabel, 0, SpringLayout.WEST, accountRsLabel);
		challengeTextRequest.add(onlineResourceTypeLabel);
		
		JComboBox onlineResourceCBox = new JComboBox();
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, onlineResourceCBox, 6, SpringLayout.SOUTH, accountRsField);
		onlineResourceCBox.setModel(new DefaultComboBoxModel(new String[] {"http", "https", "facebook", "twitter", "github"}));
		onlineResourceCBox.setSelectedIndex(0);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, onlineResourceCBox, -138, SpringLayout.EAST, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, onlineResourceCBox, -10, SpringLayout.EAST, challengeTextRequest);
		challengeTextRequest.add(onlineResourceCBox);
		
		JButton challengeTextRequestButton = new JButton("generate Message");
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, challengeTextRequestButton, 0, SpringLayout.EAST, accountRsField);
		challengeTextRequestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				challengeTextArea.setText(LogicApi.createChallengeTextMessage(accountRsField.getText(), (String) onlineResourceCBox.getSelectedItem()));
			}
		});
		challengeTextRequest.add(challengeTextRequestButton);
		
		JLabel challengeTextRequestLabel = new JLabel("Challenge Text Request Message:");
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, challengeTextRequestLabel, 129, SpringLayout.SOUTH, onlineResourceTypeLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, challengeTextRequestLabel, 0, SpringLayout.WEST, accountRsLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestLabel, 145, SpringLayout.SOUTH, onlineResourceTypeLabel);
		challengeTextRequest.add(challengeTextRequestLabel);
		
		challengeTextArea = new JTextArea();
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestButton, -10, SpringLayout.NORTH, challengeTextArea);
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, challengeTextArea, 225, SpringLayout.NORTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextArea, -10, SpringLayout.SOUTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, challengeTextArea, 10, SpringLayout.WEST, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, challengeTextArea, 0, SpringLayout.EAST, accountRsField);
		challengeTextArea.setLineWrap(true);
		challengeTextArea.setEditable(false);
		challengeTextRequest.add(challengeTextArea);
		
		JPanel tokenGeneration = new JPanel();
		verificationPane.addTab("Token Generation", null, tokenGeneration, null);
		SpringLayout sl_tokenGeneration = new SpringLayout();
		tokenGeneration.setLayout(sl_tokenGeneration);
		
		JLabel challengeTextResponseLabel = new JLabel("Challenge Text Response Message:");
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, challengeTextResponseLabel, 10, SpringLayout.NORTH, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, challengeTextResponseLabel, 10, SpringLayout.WEST, tokenGeneration);
		tokenGeneration.add(challengeTextResponseLabel);
		
		JTextArea challengeTextResponseTextArea = new JTextArea();
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, challengeTextResponseTextArea, 467, SpringLayout.WEST, tokenGeneration);
		challengeTextResponseTextArea.setLineWrap(true);
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, challengeTextResponseTextArea, 14, SpringLayout.SOUTH, challengeTextResponseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, challengeTextResponseTextArea, 0, SpringLayout.WEST, challengeTextResponseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.SOUTH, challengeTextResponseTextArea, 102, SpringLayout.SOUTH, challengeTextResponseLabel);
		tokenGeneration.add(challengeTextResponseTextArea);
		
		JLabel passphraseLabel = new JLabel("Passphrase:");
		tokenGeneration.add(passphraseLabel);
		
		passphraseField = new JPasswordField();
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, passphraseField, 90, SpringLayout.WEST, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, passphraseField, -10, SpringLayout.EAST, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, passphraseLabel, 5, SpringLayout.NORTH, passphraseField);
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, passphraseLabel, -6, SpringLayout.WEST, passphraseField);
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, passphraseField, 10, SpringLayout.SOUTH, challengeTextResponseTextArea);
		tokenGeneration.add(passphraseField);
		
		JButton tokenGenerationButton = new JButton("generate Token");
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, tokenGenerationButton, -10, SpringLayout.EAST, tokenGeneration);
		tokenGenerationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tokenTextArea.setText(LogicApi.generateTokenText(challengeTextResponseTextArea.getText(), new String(passphraseField.getPassword())));
			}
		});
		tokenGeneration.add(tokenGenerationButton);
		
		JLabel tokenLabel = new JLabel("public Token:");
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, tokenLabel, 5, SpringLayout.NORTH, tokenGenerationButton);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, tokenLabel, 10, SpringLayout.WEST, tokenGeneration);
		tokenGeneration.add(tokenLabel);
		
		tokenTextArea = new JTextArea();
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, tokenTextArea, 225, SpringLayout.NORTH, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.SOUTH, tokenTextArea, -10, SpringLayout.SOUTH, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.SOUTH, tokenGenerationButton, -9, SpringLayout.NORTH, tokenTextArea);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, tokenTextArea, 0, SpringLayout.WEST, challengeTextResponseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, tokenTextArea, 0, SpringLayout.EAST, challengeTextResponseTextArea);
		tokenTextArea.setLineWrap(true);
		tokenTextArea.setEditable(false);
		tokenGeneration.add(tokenTextArea);
		
		JPanel accountVerification = new JPanel();
		verificationPane.addTab("Account Verification", null, accountVerification, null);
		SpringLayout sl_accountVerification = new SpringLayout();
		accountVerification.setLayout(sl_accountVerification);
		
		JLabel tokenUrlLabel = new JLabel("Token Location Url:");
		sl_accountVerification.putConstraint(SpringLayout.WEST, tokenUrlLabel, 10, SpringLayout.WEST, accountVerification);
		accountVerification.add(tokenUrlLabel);
		
		tokenUrlField = new JTextField();
		sl_accountVerification.putConstraint(SpringLayout.NORTH, tokenUrlLabel, 5, SpringLayout.NORTH, tokenUrlField);
		sl_accountVerification.putConstraint(SpringLayout.NORTH, tokenUrlField, 10, SpringLayout.NORTH, accountVerification);
		sl_accountVerification.putConstraint(SpringLayout.WEST, tokenUrlField, -274, SpringLayout.EAST, accountVerification);
		sl_accountVerification.putConstraint(SpringLayout.EAST, tokenUrlField, -10, SpringLayout.EAST, accountVerification);
		accountVerification.add(tokenUrlField);
		tokenUrlField.setColumns(10);
		
		JLabel accountVerificationLabel = new JLabel("Account Verification Message:");
		sl_accountVerification.putConstraint(SpringLayout.WEST, accountVerificationLabel, 0, SpringLayout.WEST, tokenUrlLabel);
		accountVerification.add(accountVerificationLabel);
		
		JButton accountVerificationButton = new JButton("generate Message");
		sl_accountVerification.putConstraint(SpringLayout.NORTH, accountVerificationLabel, 5, SpringLayout.NORTH, accountVerificationButton);
		sl_accountVerification.putConstraint(SpringLayout.EAST, accountVerificationButton, -10, SpringLayout.EAST, accountVerification);
		accountVerificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountVerificationTextArea.setText(LogicApi.createAccountVerificationMessage(tokenUrlField.getText()));
			}
		});
		accountVerification.add(accountVerificationButton);
		
		accountVerificationTextArea = new JTextArea();
		sl_accountVerification.putConstraint(SpringLayout.SOUTH, accountVerificationButton, -8, SpringLayout.NORTH, accountVerificationTextArea);
		sl_accountVerification.putConstraint(SpringLayout.NORTH, accountVerificationTextArea, 171, SpringLayout.NORTH, accountVerification);
		sl_accountVerification.putConstraint(SpringLayout.SOUTH, accountVerificationTextArea, -10, SpringLayout.SOUTH, accountVerification);
		sl_accountVerification.putConstraint(SpringLayout.WEST, accountVerificationTextArea, 0, SpringLayout.WEST, tokenUrlLabel);
		sl_accountVerification.putConstraint(SpringLayout.EAST, accountVerificationTextArea, 0, SpringLayout.EAST, tokenUrlField);
		accountVerificationTextArea.setLineWrap(true);
		accountVerificationTextArea.setEditable(false);
		accountVerification.add(accountVerificationTextArea);
		
		JTabbedPane infoPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, infoPane, 10, SpringLayout.SOUTH, verificationPane);
		springLayout.putConstraint(SpringLayout.WEST, infoPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, infoPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, infoPane, -12, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(infoPane);
		
		JPanel infoRequest = new JPanel();
		infoPane.addTab("Info Request", null, infoRequest, null);
		SpringLayout sl_infoRequest = new SpringLayout();
		infoRequest.setLayout(sl_infoRequest);
		
		JLabel infoRequestMessageLabel = new JLabel("Info Request Message:");
		sl_infoRequest.putConstraint(SpringLayout.NORTH, infoRequestMessageLabel, 10, SpringLayout.NORTH, infoRequest);
		sl_infoRequest.putConstraint(SpringLayout.WEST, infoRequestMessageLabel, 10, SpringLayout.WEST, infoRequest);
		infoRequest.add(infoRequestMessageLabel);
		
		infoRequestTextArea = new JTextArea();
		infoRequestTextArea.setEditable(false);
		sl_infoRequest.putConstraint(SpringLayout.NORTH, infoRequestTextArea, 6, SpringLayout.SOUTH, infoRequestMessageLabel);
		sl_infoRequest.putConstraint(SpringLayout.WEST, infoRequestTextArea, 0, SpringLayout.WEST, infoRequestMessageLabel);
		sl_infoRequest.putConstraint(SpringLayout.EAST, infoRequestTextArea, 467, SpringLayout.WEST, infoRequest);
		infoRequest.add(infoRequestTextArea);
	}
	
	private void initCustom() {
		infoRequestTextArea.setText(LogicApi.createInfoRequestMessage());
	}
}
