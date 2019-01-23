package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class Gui {

	private JFrame frmIdentityVerificationClient;
	private JTextField accountRsField;
	private JTextArea challengeTextRequestTextArea;
	private JPasswordField passphraseField;
	private JTextArea tokenTextArea;
	private JTextField tokenUrlField;
	private JTextArea verificationTextArea;
	private JTextField recipientTextField;
	private JPasswordField masterPassphraseField;
	private JTextField infoRequestMessageTextField;
	private JTextField supportedChainTextField;
	private JTextField priceTextField;
	private JTextField expirationMinutesTextField;
	private JTextField contractVersionTextField;
	private JTextField contractDomainTextField;
	private JTextField resourceTextField;
	private JTextField remoteNodeTextField;
	private JSpinner amountSpinner;
	private JSpinner feeSpinner;
	private JButton sendMessageButton;
	private JTextArea infoTextArea;
	private JScrollPane scrollPane;
	private JTabbedPane messagePane;
	
	
	private GuiObjects guiObjects = new GuiObjects();
	private JTextArea challengeTextResponseTextArea;
	private JComboBox onlineResourceCBox;
	private JButton feeButton;
	private JCheckBox encryptMessageCheckbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frmIdentityVerificationClient.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIdentityVerificationClient = new JFrame();
		frmIdentityVerificationClient.setTitle("Identity Verification Client v0.9.1");
		frmIdentityVerificationClient.setResizable(false);
		frmIdentityVerificationClient.setBounds(100, 100, 520, 810);
		frmIdentityVerificationClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmIdentityVerificationClient.getContentPane().setLayout(springLayout);
		
		messagePane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, messagePane, 10, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, messagePane, 10, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, messagePane, 373, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, messagePane, 508, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(messagePane);
		
		JPanel informations = new JPanel();
		messagePane.addTab("Info", null, informations, null);
		SpringLayout sl_informations = new SpringLayout();
		informations.setLayout(sl_informations);
		
		JLabel infoRequestLMessageLabel = new JLabel("Info Request Message:");
		infoRequestLMessageLabel.setToolTipText("message to request contract informations");
		sl_informations.putConstraint(SpringLayout.NORTH, infoRequestLMessageLabel, 10, SpringLayout.NORTH, informations);
		sl_informations.putConstraint(SpringLayout.WEST, infoRequestLMessageLabel, 10, SpringLayout.WEST, informations);
		informations.add(infoRequestLMessageLabel);
		
		infoRequestMessageTextField = new JTextField();
		infoRequestMessageTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, infoRequestMessageTextField, 6, SpringLayout.SOUTH, infoRequestLMessageLabel);
		sl_informations.putConstraint(SpringLayout.WEST, infoRequestMessageTextField, 10, SpringLayout.WEST, informations);
		sl_informations.putConstraint(SpringLayout.EAST, infoRequestMessageTextField, 467, SpringLayout.WEST, informations);
		informations.add(infoRequestMessageTextField);
		infoRequestMessageTextField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		sl_informations.putConstraint(SpringLayout.NORTH, separator_1, 18, SpringLayout.SOUTH, infoRequestMessageTextField);
		sl_informations.putConstraint(SpringLayout.WEST, separator_1, 10, SpringLayout.WEST, informations);
		sl_informations.putConstraint(SpringLayout.SOUTH, separator_1, 34, SpringLayout.SOUTH, infoRequestMessageTextField);
		sl_informations.putConstraint(SpringLayout.EAST, separator_1, 467, SpringLayout.WEST, informations);
		informations.add(separator_1);
		
		JLabel supportedChainLabel = new JLabel("Supported Chain:");
		supportedChainLabel.setToolTipText("chain of payment transactions");
		sl_informations.putConstraint(SpringLayout.WEST, supportedChainLabel, 0, SpringLayout.WEST, infoRequestLMessageLabel);
		informations.add(supportedChainLabel);
		
		supportedChainTextField = new JTextField();
		supportedChainTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		supportedChainTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, supportedChainLabel, 5, SpringLayout.NORTH, supportedChainTextField);
		sl_informations.putConstraint(SpringLayout.NORTH, supportedChainTextField, 6, SpringLayout.SOUTH, separator_1);
		sl_informations.putConstraint(SpringLayout.WEST, supportedChainTextField, -85, SpringLayout.EAST, informations);
		sl_informations.putConstraint(SpringLayout.EAST, supportedChainTextField, -10, SpringLayout.EAST, informations);
		informations.add(supportedChainTextField);
		supportedChainTextField.setColumns(10);
		
		JLabel priceLabel = new JLabel("Price:");
		priceLabel.setToolTipText("verification price");
		sl_informations.putConstraint(SpringLayout.NORTH, priceLabel, 16, SpringLayout.SOUTH, supportedChainLabel);
		sl_informations.putConstraint(SpringLayout.WEST, priceLabel, 0, SpringLayout.WEST, infoRequestLMessageLabel);
		informations.add(priceLabel);
		
		priceTextField = new JTextField();
		priceTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		priceTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, priceTextField, 6, SpringLayout.SOUTH, supportedChainTextField);
		sl_informations.putConstraint(SpringLayout.WEST, priceTextField, -85, SpringLayout.EAST, informations);
		sl_informations.putConstraint(SpringLayout.EAST, priceTextField, -10, SpringLayout.EAST, informations);
		priceTextField.setColumns(10);
		informations.add(priceTextField);
		
		expirationMinutesTextField = new JTextField();
		expirationMinutesTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		expirationMinutesTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, expirationMinutesTextField, 6, SpringLayout.SOUTH, priceTextField);
		sl_informations.putConstraint(SpringLayout.WEST, expirationMinutesTextField, 0, SpringLayout.WEST, supportedChainTextField);
		sl_informations.putConstraint(SpringLayout.EAST, expirationMinutesTextField, 0, SpringLayout.EAST, infoRequestMessageTextField);
		expirationMinutesTextField.setColumns(10);
		informations.add(expirationMinutesTextField);
		
		contractVersionTextField = new JTextField();
		contractVersionTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		contractVersionTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, contractVersionTextField, 6, SpringLayout.SOUTH, expirationMinutesTextField);
		sl_informations.putConstraint(SpringLayout.WEST, contractVersionTextField, 0, SpringLayout.WEST, supportedChainTextField);
		sl_informations.putConstraint(SpringLayout.EAST, contractVersionTextField, -10, SpringLayout.EAST, informations);
		contractVersionTextField.setColumns(10);
		informations.add(contractVersionTextField);
		
		contractDomainTextField = new JTextField();
		contractDomainTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		contractDomainTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, contractDomainTextField, 6, SpringLayout.SOUTH, contractVersionTextField);
		sl_informations.putConstraint(SpringLayout.EAST, contractDomainTextField, -10, SpringLayout.EAST, informations);
		contractDomainTextField.setColumns(10);
		informations.add(contractDomainTextField);
		
		resourceTextField = new JTextField();
		resourceTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		resourceTextField.setEditable(false);
		sl_informations.putConstraint(SpringLayout.NORTH, resourceTextField, 6, SpringLayout.SOUTH, contractDomainTextField);
		sl_informations.putConstraint(SpringLayout.EAST, resourceTextField, 0, SpringLayout.EAST, infoRequestMessageTextField);
		resourceTextField.setColumns(10);
		informations.add(resourceTextField);
		
		JLabel expirationMinutesLabel = new JLabel("Challenge Text Expiration TIme (in Minutes):");
		expirationMinutesLabel.setToolTipText("time after the requested challenge text expires");
		sl_informations.putConstraint(SpringLayout.NORTH, expirationMinutesLabel, 5, SpringLayout.NORTH, expirationMinutesTextField);
		sl_informations.putConstraint(SpringLayout.WEST, expirationMinutesLabel, 10, SpringLayout.WEST, informations);
		informations.add(expirationMinutesLabel);
		
		JLabel contractVersionLabel = new JLabel("Contract Version:");
		contractVersionLabel.setToolTipText("version of identityVerification contract");
		sl_informations.putConstraint(SpringLayout.NORTH, contractVersionLabel, 5, SpringLayout.NORTH, contractVersionTextField);
		sl_informations.putConstraint(SpringLayout.WEST, contractVersionLabel, 0, SpringLayout.WEST, infoRequestLMessageLabel);
		informations.add(contractVersionLabel);
		
		JLabel contractDomainLabel = new JLabel("Contract Domain:");
		contractDomainLabel.setToolTipText("domain / realm of contract");
		sl_informations.putConstraint(SpringLayout.WEST, contractDomainTextField, 167, SpringLayout.EAST, contractDomainLabel);
		sl_informations.putConstraint(SpringLayout.NORTH, contractDomainLabel, 5, SpringLayout.NORTH, contractDomainTextField);
		sl_informations.putConstraint(SpringLayout.WEST, contractDomainLabel, 0, SpringLayout.WEST, infoRequestLMessageLabel);
		informations.add(contractDomainLabel);
		
		JLabel resourceLabel = new JLabel("Online Resources:");
		resourceLabel.setToolTipText("supported online resources");
		sl_informations.putConstraint(SpringLayout.WEST, resourceTextField, 6, SpringLayout.EAST, resourceLabel);
		sl_informations.putConstraint(SpringLayout.NORTH, resourceLabel, 16, SpringLayout.SOUTH, contractDomainLabel);
		sl_informations.putConstraint(SpringLayout.WEST, resourceLabel, 0, SpringLayout.WEST, infoRequestLMessageLabel);
		informations.add(resourceLabel);
		
		JPanel challengeTextRequest = new JPanel();
		messagePane.addTab("Challenge Text", null, challengeTextRequest, null);
		messagePane.setEnabledAt(1, true);
		SpringLayout sl_challengeTextRequest = new SpringLayout();
		challengeTextRequest.setLayout(sl_challengeTextRequest);
		
		JLabel accountRsLabel = new JLabel("Account used for Verification:");
		accountRsLabel.setToolTipText("account which is used for verification");
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
		onlineResourceTypeLabel.setToolTipText("type of online resource where the token will be posted");
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, onlineResourceTypeLabel, 15, SpringLayout.SOUTH, accountRsLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, onlineResourceTypeLabel, 0, SpringLayout.WEST, accountRsLabel);
		challengeTextRequest.add(onlineResourceTypeLabel);
		
		onlineResourceCBox = new JComboBox();
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, onlineResourceCBox, 6, SpringLayout.SOUTH, accountRsField);
		onlineResourceCBox.setModel(new DefaultComboBoxModel(new String[] {"http", "https", "facebook", "twitter", "github"}));
		onlineResourceCBox.setSelectedIndex(0);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, onlineResourceCBox, -138, SpringLayout.EAST, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, onlineResourceCBox, -10, SpringLayout.EAST, challengeTextRequest);
		challengeTextRequest.add(onlineResourceCBox);
		
		JButton challengeTextRequestButton = new JButton("generate Message");
		challengeTextRequestButton.setToolTipText("generates challenge text request message");
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, challengeTextRequestButton, 0, SpringLayout.EAST, accountRsField);
		challengeTextRequestButton.addActionListener((e) -> GuiLogic.setChallengeTextMessage());
		challengeTextRequest.add(challengeTextRequestButton);
		
		JLabel challengeTextRequestLabel = new JLabel("Challenge Text Request Message:");
		challengeTextRequestLabel.setToolTipText("message to request the challenge text");
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, challengeTextRequestLabel, 129, SpringLayout.SOUTH, onlineResourceTypeLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, challengeTextRequestLabel, 0, SpringLayout.WEST, accountRsLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestLabel, 145, SpringLayout.SOUTH, onlineResourceTypeLabel);
		challengeTextRequest.add(challengeTextRequestLabel);
		
		challengeTextRequestTextArea = new JTextArea();
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestButton, -10, SpringLayout.NORTH, challengeTextRequestTextArea);
		sl_challengeTextRequest.putConstraint(SpringLayout.NORTH, challengeTextRequestTextArea, 225, SpringLayout.NORTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestTextArea, -10, SpringLayout.SOUTH, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, challengeTextRequestTextArea, 10, SpringLayout.WEST, challengeTextRequest);
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, challengeTextRequestTextArea, 0, SpringLayout.EAST, accountRsField);
		challengeTextRequestTextArea.setLineWrap(true);
		challengeTextRequestTextArea.setEditable(false);
		challengeTextRequest.add(challengeTextRequestTextArea);
		
		JPanel tokenGeneration = new JPanel();
		messagePane.addTab("Token Generation", null, tokenGeneration, null);
		SpringLayout sl_tokenGeneration = new SpringLayout();
		tokenGeneration.setLayout(sl_tokenGeneration);
		
		JLabel challengeTextResponseLabel = new JLabel("Challenge Text Response Message:");
		challengeTextResponseLabel.setToolTipText("challgenge text response message received from contract runner account. ");
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, challengeTextResponseLabel, 10, SpringLayout.NORTH, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, challengeTextResponseLabel, 10, SpringLayout.WEST, tokenGeneration);
		tokenGeneration.add(challengeTextResponseLabel);
		
		challengeTextResponseTextArea = new JTextArea();
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, challengeTextResponseTextArea, 467, SpringLayout.WEST, tokenGeneration);
		challengeTextResponseTextArea.setLineWrap(true);
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, challengeTextResponseTextArea, 14, SpringLayout.SOUTH, challengeTextResponseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, challengeTextResponseTextArea, 0, SpringLayout.WEST, challengeTextResponseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.SOUTH, challengeTextResponseTextArea, 102, SpringLayout.SOUTH, challengeTextResponseLabel);
		tokenGeneration.add(challengeTextResponseTextArea);
		
		JLabel passphraseLabel = new JLabel("Passphrase of Verification Account:");
		passphraseLabel.setToolTipText("passphrase of account used for verification");
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, passphraseLabel, 15, SpringLayout.SOUTH, challengeTextResponseTextArea);
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, passphraseLabel, 0, SpringLayout.WEST, challengeTextResponseLabel);
		tokenGeneration.add(passphraseLabel);
		
		passphraseField = new JPasswordField();
		passphraseField.setToolTipText("using sending account passphrase if empty");
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, passphraseField, 6, SpringLayout.EAST, passphraseLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, passphraseField, -10, SpringLayout.EAST, tokenGeneration);
		sl_tokenGeneration.putConstraint(SpringLayout.NORTH, passphraseField, 10, SpringLayout.SOUTH, challengeTextResponseTextArea);
		tokenGeneration.add(passphraseField);
		
		JButton tokenGenerationButton = new JButton("generate Token");
		tokenGenerationButton.setToolTipText("generates token");
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, tokenGenerationButton, -10, SpringLayout.EAST, tokenGeneration);
		tokenGenerationButton.addActionListener((e) -> GuiLogic.setTokenText());
		tokenGeneration.add(tokenGenerationButton);
		
		JLabel tokenLabel = new JLabel("public Token:");
		tokenLabel.setToolTipText("token that must be published on selected online resource type");
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
		
		JPanel verification = new JPanel();
		messagePane.addTab("Verification", null, verification, null);
		SpringLayout sl_verification = new SpringLayout();
		verification.setLayout(sl_verification);
		
		JLabel tokenUrlLabel = new JLabel("Token Location Url:");
		tokenUrlLabel.setToolTipText("url of token location");
		sl_verification.putConstraint(SpringLayout.WEST, tokenUrlLabel, 10, SpringLayout.WEST, verification);
		verification.add(tokenUrlLabel);
		
		tokenUrlField = new JTextField();
		sl_verification.putConstraint(SpringLayout.NORTH, tokenUrlLabel, 5, SpringLayout.NORTH, tokenUrlField);
		sl_verification.putConstraint(SpringLayout.NORTH, tokenUrlField, 10, SpringLayout.NORTH, verification);
		sl_verification.putConstraint(SpringLayout.WEST, tokenUrlField, -274, SpringLayout.EAST, verification);
		sl_verification.putConstraint(SpringLayout.EAST, tokenUrlField, -10, SpringLayout.EAST, verification);
		verification.add(tokenUrlField);
		tokenUrlField.setColumns(10);
		
		JLabel verificationLabel = new JLabel("Verification Request Message:");
		verificationLabel.setToolTipText("message for account verification");
		sl_verification.putConstraint(SpringLayout.WEST, verificationLabel, 0, SpringLayout.WEST, tokenUrlLabel);
		verification.add(verificationLabel);
		
		JButton verificationButton = new JButton("generate Message");
		verificationButton.setToolTipText("generates verification message");
		sl_verification.putConstraint(SpringLayout.NORTH, verificationLabel, 5, SpringLayout.NORTH, verificationButton);
		sl_verification.putConstraint(SpringLayout.EAST, verificationButton, -10, SpringLayout.EAST, verification);
		verificationButton.addActionListener((e) -> GuiLogic.setAccountVerificationMessage());
		verification.add(verificationButton);
		
		verificationTextArea = new JTextArea();
		verificationTextArea.setEditable(false);
		sl_verification.putConstraint(SpringLayout.SOUTH, verificationButton, -8, SpringLayout.NORTH, verificationTextArea);
		sl_verification.putConstraint(SpringLayout.NORTH, verificationTextArea, 171, SpringLayout.NORTH, verification);
		sl_verification.putConstraint(SpringLayout.SOUTH, verificationTextArea, -10, SpringLayout.SOUTH, verification);
		sl_verification.putConstraint(SpringLayout.WEST, verificationTextArea, 0, SpringLayout.WEST, tokenUrlLabel);
		sl_verification.putConstraint(SpringLayout.EAST, verificationTextArea, 0, SpringLayout.EAST, tokenUrlField);
		verificationTextArea.setLineWrap(true);
		verification.add(verificationTextArea);
		
		sendMessageButton = new JButton("send Message");
		sendMessageButton.setToolTipText("sends the focused message");
		sendMessageButton.addActionListener((e) -> GuiLogic.sendMessage());
		springLayout.putConstraint(SpringLayout.EAST, sendMessageButton, 0, SpringLayout.EAST, messagePane);
		frmIdentityVerificationClient.getContentPane().add(sendMessageButton);
		
		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, messagePane);
		springLayout.putConstraint(SpringLayout.WEST, separator, 10, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, messagePane);
		frmIdentityVerificationClient.getContentPane().add(separator);
		
		JLabel recipientLabel = new JLabel("Contract Runner Account:");
		recipientLabel.setToolTipText("contract runner account");
		springLayout.putConstraint(SpringLayout.WEST, recipientLabel, 0, SpringLayout.WEST, messagePane);
		frmIdentityVerificationClient.getContentPane().add(recipientLabel);
		
		recipientTextField = new JTextField();
		recipientTextField.setText("ARDOR-5TT2-VS3T-EUTS-7WDBA");
		springLayout.putConstraint(SpringLayout.WEST, recipientTextField, -280, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, recipientTextField, -12, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(recipientTextField);
		recipientTextField.setColumns(10);
		
		JLabel amountLabel = new JLabel("Amount:");
		amountLabel.setToolTipText("amount to send");
		springLayout.putConstraint(SpringLayout.NORTH, recipientLabel, -32, SpringLayout.NORTH, amountLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, recipientLabel, -16, SpringLayout.NORTH, amountLabel);
		springLayout.putConstraint(SpringLayout.WEST, amountLabel, 0, SpringLayout.WEST, messagePane);
		frmIdentityVerificationClient.getContentPane().add(amountLabel);
		
		JLabel feeLabel = new JLabel("Fee:");
		feeLabel.setToolTipText("fees to pay for transaction");
		springLayout.putConstraint(SpringLayout.NORTH, feeLabel, 498, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, feeLabel, 0, SpringLayout.WEST, messagePane);
		springLayout.putConstraint(SpringLayout.EAST, feeLabel, 51, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(feeLabel);
		
		amountSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0 ,1000.0, 0.1));
		amountSpinner.setValue(1.0);
		springLayout.putConstraint(SpringLayout.NORTH, amountSpinner, 461, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, amountSpinner, 330, SpringLayout.EAST, amountLabel);
		springLayout.putConstraint(SpringLayout.EAST, amountSpinner, -12, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, amountLabel, 5, SpringLayout.NORTH, amountSpinner);
		springLayout.putConstraint(SpringLayout.NORTH, recipientTextField, -32, SpringLayout.NORTH, amountSpinner);
		springLayout.putConstraint(SpringLayout.SOUTH, recipientTextField, -6, SpringLayout.NORTH, amountSpinner);
		frmIdentityVerificationClient.getContentPane().add(amountSpinner);
		
		feeSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0 ,1000.0, 0.01));
		feeSpinner.setValue(0.1);
		springLayout.putConstraint(SpringLayout.SOUTH, amountSpinner, -6, SpringLayout.NORTH, feeSpinner);
		springLayout.putConstraint(SpringLayout.NORTH, feeSpinner, 493, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, feeSpinner, 519, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, feeSpinner, -12, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(feeSpinner);
		
		masterPassphraseField = new JPasswordField();
		masterPassphraseField.setToolTipText("same as verification account passphrase in case of verification message");
		springLayout.putConstraint(SpringLayout.WEST, masterPassphraseField, -280, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, masterPassphraseField, -12, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(masterPassphraseField);
		
		JLabel masterPassphraseLabel = new JLabel("Passphrase of Sending Account:");
		masterPassphraseLabel.setToolTipText("passphrase of transaction sending account");
		springLayout.putConstraint(SpringLayout.NORTH, masterPassphraseLabel, 530, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, masterPassphraseLabel, 10, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, masterPassphraseLabel, 546, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(masterPassphraseLabel);
		
		JLabel infoLabel = new JLabel("Info:");
		infoLabel.setToolTipText("field for gui informations");
		springLayout.putConstraint(SpringLayout.NORTH, infoLabel, 568, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, infoLabel, 10, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(infoLabel);
		
		feeButton = new JButton("calculate");
		feeButton.setToolTipText("calculates the focused message fees");
		springLayout.putConstraint(SpringLayout.NORTH, masterPassphraseField, 3, SpringLayout.SOUTH, feeButton);
		springLayout.putConstraint(SpringLayout.SOUTH, masterPassphraseField, 29, SpringLayout.SOUTH, feeButton);
		springLayout.putConstraint(SpringLayout.WEST, feeSpinner, 6, SpringLayout.EAST, feeButton);
		springLayout.putConstraint(SpringLayout.SOUTH, feeButton, 522, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, feeButton, 493, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, feeButton, 267, SpringLayout.EAST, feeLabel);
		springLayout.putConstraint(SpringLayout.EAST, feeButton, -132, SpringLayout.EAST, frmIdentityVerificationClient.getContentPane());
		feeButton.addActionListener((e) -> GuiLogic.calculateFee());
		feeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		frmIdentityVerificationClient.getContentPane().add(feeButton);
		
		remoteNodeTextField = new JTextField();
		remoteNodeTextField.setText("https://testardor.some.one/nxt");
		springLayout.putConstraint(SpringLayout.WEST, remoteNodeTextField, 0, SpringLayout.WEST, recipientTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, remoteNodeTextField, -6, SpringLayout.NORTH, recipientTextField);
		springLayout.putConstraint(SpringLayout.EAST, remoteNodeTextField, 0, SpringLayout.EAST, messagePane);
		remoteNodeTextField.setColumns(10);
		frmIdentityVerificationClient.getContentPane().add(remoteNodeTextField);
		
		JLabel remoteNodeLabel = new JLabel("Remote Node:");
		remoteNodeLabel.setToolTipText("ardor full node to connect to");
		springLayout.putConstraint(SpringLayout.WEST, remoteNodeLabel, 0, SpringLayout.WEST, messagePane);
		springLayout.putConstraint(SpringLayout.SOUTH, remoteNodeLabel, -16, SpringLayout.NORTH, recipientLabel);
		frmIdentityVerificationClient.getContentPane().add(remoteNodeLabel);
		
		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, sendMessageButton, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 590, SpringLayout.NORTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -45, SpringLayout.SOUTH, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 510, SpringLayout.WEST, frmIdentityVerificationClient.getContentPane());
		frmIdentityVerificationClient.getContentPane().add(scrollPane);
		
		infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		scrollPane.setViewportView(infoTextArea);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setToolTipText("clears the info field");
		clearButton.addActionListener((e) -> GuiLogic.clearInfoArea());
		springLayout.putConstraint(SpringLayout.NORTH, clearButton, 0, SpringLayout.NORTH, sendMessageButton);
		springLayout.putConstraint(SpringLayout.WEST, clearButton, 0, SpringLayout.WEST, messagePane);
		frmIdentityVerificationClient.getContentPane().add(clearButton);
		
		JButton verificationMessageCopyButton = new JButton("copy");
		verificationMessageCopyButton.setToolTipText("copies verification message to clipboard");
		sl_verification.putConstraint(SpringLayout.EAST, verificationMessageCopyButton, 61, SpringLayout.EAST, verificationLabel);
		verificationMessageCopyButton.addActionListener((e) -> GuiLogic.copyVerificationRequest());
		sl_verification.putConstraint(SpringLayout.WEST, verificationMessageCopyButton, 6, SpringLayout.EAST, verificationLabel);
		sl_verification.putConstraint(SpringLayout.SOUTH, verificationMessageCopyButton, 0, SpringLayout.SOUTH, verificationButton);
		verificationMessageCopyButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		verification.add(verificationMessageCopyButton);
		
		JButton tokenCopyButton = new JButton("copy");
		tokenCopyButton.setToolTipText("copies token to clipboard");
		sl_tokenGeneration.putConstraint(SpringLayout.EAST, tokenCopyButton, 59, SpringLayout.EAST, tokenLabel);
		tokenCopyButton.addActionListener((e) -> GuiLogic.copyToken());
		sl_tokenGeneration.putConstraint(SpringLayout.WEST, tokenCopyButton, 6, SpringLayout.EAST, tokenLabel);
		sl_tokenGeneration.putConstraint(SpringLayout.SOUTH, tokenCopyButton, 0, SpringLayout.SOUTH, tokenGenerationButton);
		tokenCopyButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		tokenGeneration.add(tokenCopyButton);
		
		JButton challengeTextRequestCopyButton = new JButton("copy");
		challengeTextRequestCopyButton.setToolTipText("copies challenge text request message to clipboard");
		sl_challengeTextRequest.putConstraint(SpringLayout.EAST, challengeTextRequestCopyButton, 59, SpringLayout.EAST, challengeTextRequestLabel);
		challengeTextRequestCopyButton.addActionListener((e) -> GuiLogic.copyChallengeTextRequest());
		sl_challengeTextRequest.putConstraint(SpringLayout.WEST, challengeTextRequestCopyButton, 6, SpringLayout.EAST, challengeTextRequestLabel);
		sl_challengeTextRequest.putConstraint(SpringLayout.SOUTH, challengeTextRequestCopyButton, 0, SpringLayout.SOUTH, challengeTextRequestButton);
		challengeTextRequestCopyButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		challengeTextRequest.add(challengeTextRequestCopyButton);
		
		JButton infoRequestCopyButton = new JButton("copy");
		infoRequestCopyButton.setToolTipText("copies info request message to clipboard");
		infoRequestCopyButton.addActionListener((e) -> GuiLogic.copyInfoRequest());
		sl_informations.putConstraint(SpringLayout.NORTH, infoRequestCopyButton, 5, SpringLayout.NORTH, informations);
		sl_informations.putConstraint(SpringLayout.WEST, infoRequestCopyButton, 6, SpringLayout.EAST, infoRequestLMessageLabel);
		sl_informations.putConstraint(SpringLayout.EAST, infoRequestCopyButton, 59, SpringLayout.EAST, infoRequestLMessageLabel);
		infoRequestCopyButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		informations.add(infoRequestCopyButton);
		
		encryptMessageCheckbox = new JCheckBox("encrypt");
		encryptMessageCheckbox.setSelected(true);
		encryptMessageCheckbox.setToolTipText("encrypts the message to send");
		springLayout.putConstraint(SpringLayout.NORTH, encryptMessageCheckbox, 2, SpringLayout.NORTH, sendMessageButton);
		springLayout.putConstraint(SpringLayout.EAST, encryptMessageCheckbox, 0, SpringLayout.WEST, sendMessageButton);
		encryptMessageCheckbox.setHorizontalTextPosition(SwingConstants.LEFT);
		frmIdentityVerificationClient.getContentPane().add(encryptMessageCheckbox);
		
		
		guiObjects.accountRsField = accountRsField;
		guiObjects.challengeTextRequestTextArea = challengeTextRequestTextArea;
		guiObjects.passphraseField = passphraseField;
		guiObjects.tokenTextArea = tokenTextArea;
		guiObjects.tokenUrlField = tokenUrlField;
		guiObjects.verificationTextArea = verificationTextArea;
		guiObjects.onlineResourceCBox = onlineResourceCBox;
		guiObjects.feeButton = feeButton;
		guiObjects.recipientTextField = recipientTextField;
		guiObjects.masterPassphraseField = masterPassphraseField;
		guiObjects.infoRequestMessageTextField = infoRequestMessageTextField;
		guiObjects.supportedChainTextField = supportedChainTextField;
		guiObjects.priceTextField = priceTextField;
		guiObjects.expirationMinutesTextField = expirationMinutesTextField;
		guiObjects.contractVersionTextField = contractVersionTextField;
		guiObjects.contractDomainTextField = contractDomainTextField;
		guiObjects.resourceTextField = resourceTextField;
		guiObjects.remoteNodeTextField = remoteNodeTextField;
		guiObjects.amountSpinner = amountSpinner;
		guiObjects.feeSpinner = feeSpinner;
		guiObjects.sendMessageButton = sendMessageButton;
		guiObjects.infoTextArea = infoTextArea;
		guiObjects.messagePane = messagePane;
		guiObjects.challengeTextResponseTextArea = challengeTextResponseTextArea;
		guiObjects.encryptMessageCheckbox = encryptMessageCheckbox;
		
		GuiLogic.init(guiObjects);
		messagePane.addChangeListener((e) -> GuiLogic.tabChanged());
	}
}
