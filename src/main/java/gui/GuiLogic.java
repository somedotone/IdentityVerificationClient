package gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import config.Constants;
import logic.Logic;
import logic.SendMessageParams;
import nxt.addons.JO;
import textAreaWriter.TextAreaWriter;

public class GuiLogic {
	
	public static final int INFO_REQUEST = 0;
	public static final int CHALLENGE_TEXT = 1;
	public static final int TOKEN_GENERATION = 2;
	public static final int VERIFICATION = 3;
	
	private static TextAreaWriter infoWriter;
	private static GuiObjects guiObjects;
	
	private static JO messageRespone;
	
	private static Logic logic = new Logic();
	
	private static boolean isRunning = false;
	private static int tabIndex;
	
	
	
	public static void init(GuiObjects _guiGuiObjects) {
		guiObjects = _guiGuiObjects;
		infoWriter = new TextAreaWriter(guiObjects.infoTextArea);
		infoWriter.writeLine(addTime() + "initialized");
		
		setInfoRequestMessage();
	}

	private static String addTime() {
		return new Date().toGMTString() + ": ";
	}
	
	private static void setInfoRequestMessage() {
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "creating info request message...");
		
		JO message = logic.createInfoRequestMessage();
		guiObjects.infoRequestMessageTextField.setText(message.toJSONString());
		
		infoWriter.writeLine(addTime() + "...done.");
	}
	
	
	public static void clearInfoArea() {
		infoWriter.clear();
	}
	
	
	public static void tabChanged() {
		tabIndex = guiObjects.messagePane.getSelectedIndex();
		
		if(tabIndex == TOKEN_GENERATION) guiObjects.sendMessageButton.setEnabled(false);
		else if(!isRunning) guiObjects.sendMessageButton.setEnabled(true);
	}
	
	
	public static void setChallengeTextMessage() {
		if(checkChallengeTextInputFields()) return;
		
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "creating challenge text request message... ");
		
		JO message = logic.createChallengeTextMessage(guiObjects.accountRsField.getText(), (String) guiObjects.onlineResourceCBox.getSelectedItem());
		guiObjects.challengeTextRequestTextArea.setText(message.toJSONString());
		
		infoWriter.writeLine(addTime() + "...done.");
	}
	
	private static boolean checkChallengeTextInputFields() {
		String messageError = "cannot create challenge text";
		
		String accountForVerification = guiObjects.accountRsField.getText();
		if(accountForVerification == null || accountForVerification.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please set account used for verification");
			return true;
		}
		
		return false;
	}
	
	
	public static void setTokenText() {
		if(checkTokenInputFields()) return;
		
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "generating token...");
		
		String token = logic.generateTokenText(guiObjects.challengeTextResponseTextArea.getText(),  getPassphrase());
		guiObjects.tokenTextArea.setText(token);
		
		infoWriter.writeLine(addTime() + "...done.");
	}
	
	private static String getPassphrase() {
		String tokenPassphrase = (new String(guiObjects.passphraseField.getPassword())).trim();
		
		if(tokenPassphrase == null || tokenPassphrase.equals("")) {
			infoWriter.writeLine("...verification account passphrase empty...");
			infoWriter.writeLine("...using sending account passohrase...");
			return (new String(guiObjects.masterPassphraseField.getPassword())).trim();
		}
		
		return tokenPassphrase;
	}
	
	private static boolean checkTokenInputFields() {
		String messageError = "cannot create token";
		
		String challengeTextResponse = guiObjects.challengeTextResponseTextArea.getText();
		if(challengeTextResponse == null || challengeTextResponse.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please input challenge text response message");
			return true;
		}
		
		return false;
	}
	
	
	public static void setAccountVerificationMessage() {
		if(checkAccountVerificationInputFields()) return;
		
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "creating account verification request message...");
		
		JO message = logic.createAccountVerificationMessage(guiObjects.tokenUrlField.getText());
		infoWriter.writeLine(addTime() + "...done.");
		
		guiObjects.verificationTextArea.setText(message.toJSONString());
	}
	
	private static boolean checkAccountVerificationInputFields() {
		String messageError = "cannot create verification message";
		
		String tokenUrl = guiObjects.tokenUrlField.getText();
		if(tokenUrl == null || tokenUrl.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please set token url");
			return true;
		}
		
		try {
			new URL(tokenUrl);
		} catch (MalformedURLException e) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "token url is not a valid url");
			return true;
		}
		
		return false;
	}
	
	
	public static void sendMessage() {
		if(checkMessageSendingInputFields()) return;
		
		int tabIdx = tabIndex;
		
		SendMessageParams params = new SendMessageParams();
		params.amount = (double) guiObjects.amountSpinner.getValue();
		params.fee = (double) guiObjects.feeSpinner.getValue();
		params.recipient = guiObjects.recipientTextField.getText().trim();
		params.passphrase = (new String(guiObjects.masterPassphraseField.getPassword())).trim();
		params.chain = 2;
		params.message = getMessage(tabIdx);
		params.isEncryptedMessage = guiObjects.encryptMessageCheckbox.isSelected();
		
		
		Thread waitingSignal = new Thread(() -> {
			int counter = 0;
			boolean isFirstRun = true;
			
			while(true) {
				counter = writeWaitingSignal(isFirstRun, counter);
				if(isFirstRun) isFirstRun = false;
				
				try { TimeUnit.SECONDS.sleep(Constants.WAITING_SIGNAL_UPDATE_TIME); } 
				catch (InterruptedException e) { return; }
			}
		});
		
		
		new Thread(() -> {
			String messageType = JO.parse(params.message).getJo("params").getString(Constants.MESSAGE_TYPE_KEY);
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + "sending "+ messageType + " message...");
			guiObjects.sendMessageButton.setEnabled(false);
			guiObjects.feeButton.setEnabled(false);
			isRunning = true;
			waitingSignal.start();
			
			
			try {
				
				params.url = new URL(guiObjects.remoteNodeTextField.getText().trim());
				messageRespone = logic.sendMessage(params);
				
				switch (tabIdx) {
					case INFO_REQUEST:
						guiObjects.supportedChainTextField.setText(messageRespone.getString(Constants.CHAIN_KEY));
						guiObjects.priceTextField.setText(messageRespone.getString(Constants.PRICE_KEY));
						guiObjects.expirationMinutesTextField.setText(messageRespone.getString(Constants.EXPIRATION_MINUTES_KEY));
						guiObjects.contractVersionTextField.setText(messageRespone.getString(Constants.VERSION_KEY));
						guiObjects.contractDomainTextField.setText(messageRespone.getString(Constants.CONTRACT_DOMAIN_KEY));
						guiObjects.resourceTextField.setText(messageRespone.getArray(Constants.RESOURCES_KEY).toJSONArray().toJSONString());
						break;
						
						
					case CHALLENGE_TEXT:
						guiObjects.challengeTextResponseTextArea.setText(messageRespone.toJSONString());
						guiObjects.messagePane.setSelectedIndex(GuiLogic.TOKEN_GENERATION);
						break;
						
						
					case VERIFICATION:
						
						infoWriter.writeLine("property -> " + messageRespone.getJo("attachment").getString("property"));
						infoWriter.writeLine("value -> " + messageRespone.getJo("attachment").getString("value"));
						infoWriter.writeLine("transaction fullHash -> " + messageRespone.getString("fullHash"));
						infoWriter.writeEmptyLine();
						break;
						
		
					default:
						break;
				}
				
				
				infoWriter.writeLine(addTime() + "..." + messageType + " successfully proceed.");
			} catch (Exception e) {
				e.printStackTrace();
				infoWriter.writeLine(addTime() + "... stopped!! Following error occured:");
				infoWriter.writeLine(addTime() + e.getMessage());
			}
			
			
			waitingSignal.interrupt();
			isRunning = false;
			guiObjects.feeButton.setEnabled(true);
			if(tabIndex != TOKEN_GENERATION) guiObjects.sendMessageButton.setEnabled(true);
		}).start();
	}
	
	private static boolean checkMessageSendingInputFields() {
		String messageError = "cannot send message";
		
		
		if(tabIndex == CHALLENGE_TEXT) {
			String challengeTextRequest = guiObjects.challengeTextRequestTextArea.getText();
			if(challengeTextRequest == null || challengeTextRequest.equals("")) {
				infoWriter.writeEmptyLine();
				infoWriter.writeLine(addTime() + messageError);
				infoWriter.writeLine(addTime() + "please generate challenge text request message");
				return true;
			}
		}
		
			
		if(tabIndex == VERIFICATION) {
			String verificationRequest = guiObjects.verificationTextArea.getText();
			if(verificationRequest == null || verificationRequest.equals("")) {
				infoWriter.writeEmptyLine();
				infoWriter.writeLine(addTime() + messageError);
				infoWriter.writeLine(addTime() + "please generate verification request message");
				return true;
			}
		}
		
		
		return checkSendingInputFields(messageError);
	}
	
	private static boolean checkSendingInputFields(String messageError) {
		
		String url = guiObjects.remoteNodeTextField.getText();
		if(url == null || url.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please set remote node url");
			return true;
		}
		
		if(!url.endsWith("/nxt")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "remote node url must end with \"/nxt\"");
			return true;
		}
		
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "remote node url is not a valid url");
			return true;
		}
		
		
		String recipient = guiObjects.recipientTextField.getText();
		if(recipient == null || recipient.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please set contract runner account");
			return true;
		}
		
		
		String passphrase = (new String(guiObjects.masterPassphraseField.getPassword())).trim();
		if(passphrase == null || passphrase.equals("")) {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + messageError);
			infoWriter.writeLine(addTime() + "please set sending account passphrase");
			return true;
		}
		
		return false;
	}
	
	private static String getMessage(int tab) {
		switch (tab) {
			case INFO_REQUEST: return guiObjects.infoRequestMessageTextField.getText();
			case CHALLENGE_TEXT: return guiObjects.challengeTextRequestTextArea.getText();
			case VERIFICATION: return guiObjects.verificationTextArea.getText();
			default: return null;
		}
	}
	
	private static int writeWaitingSignal(boolean isFirstRun, int counter) {		
		String text = "";
		counter = counter % Constants.WAITING_SIGNAL_LENGTH;
		for (int i = 0; i <= counter; i++) text += ". ";

		if(!isFirstRun) infoWriter.removeLine();
		infoWriter.writeLine(text);
		
		return ++counter;
	}

	
	public static void calculateFee() {
		if(checkFeeCalculationInputFields()) return;
		
		SendMessageParams params = new SendMessageParams();
		params.amount = (double) guiObjects.amountSpinner.getValue();
		params.calculateFee = true;
		params.recipient = guiObjects.recipientTextField.getText().trim();
		params.passphrase = (new String(guiObjects.masterPassphraseField.getPassword())).trim();
		params.chain = 2;
		params.message = getMessage(tabIndex);
		params.isEncryptedMessage = guiObjects.encryptMessageCheckbox.isSelected();
		
		
		new Thread(() -> {
			infoWriter.writeEmptyLine();
			infoWriter.writeLine(addTime() + "calculating fee ...");
			guiObjects.sendMessageButton.setEnabled(false);
			guiObjects.feeButton.setEnabled(false);
			isRunning = true;
			
			
			try { 
				params.url = new URL(guiObjects.remoteNodeTextField.getText().trim());
				guiObjects.feeSpinner.setValue(logic.calculateFee(params));
				infoWriter.writeLine(addTime() + "... fee successfully calculated.");
			} catch (Exception e) {
				e.printStackTrace();
				infoWriter.writeLine(addTime() + "... stopped!! Following error occured:");
				infoWriter.writeLine(addTime() + e.getMessage());
			}
		
			
			infoWriter.writeEmptyLine();
			isRunning = false;
			guiObjects.feeButton.setEnabled(true);
			if(tabIndex != TOKEN_GENERATION) guiObjects.sendMessageButton.setEnabled(true);
		}).start();
	}
	
	private static boolean checkFeeCalculationInputFields() {
		return checkSendingInputFields("cannot calculate fees");
	}


	public static void copyInfoRequest() {
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "info request message copied to clipboard.");
		copyToClipboard(guiObjects.infoRequestMessageTextField.getText());
	}
	
	private static void copyToClipboard(String text) {
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	
	public static void copyChallengeTextRequest() {
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "challenge text request message copied to clipboard.");
		copyToClipboard(guiObjects.challengeTextRequestTextArea.getText());
	}
	
	
	public static void copyToken() {
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "token copied to clipboard.");
		copyToClipboard(guiObjects.tokenTextArea.getText());
	}
	
	
	public static void copyVerificationRequest() {
		infoWriter.writeEmptyLine();
		infoWriter.writeLine(addTime() + "verification request message copied to clipboard.");
		copyToClipboard(guiObjects.verificationTextArea.getText());
	}
}
