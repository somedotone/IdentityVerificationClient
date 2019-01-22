package logic;


import nxt.account.AccountPropertyTransactionType;
import nxt.account.PaymentTransactionType;
import nxt.account.Token;
import nxt.addons.JO;
import nxt.crypto.EncryptedData;
import nxt.http.callers.DecryptFromCall;
import nxt.http.callers.GetAccountPublicKeyCall;
import nxt.http.responses.TransactionResponse;
import nxt.util.Convert;

public class Logic {
	
	private JO challengeTextResponseObject;
	private JO responsedMessage;
	
	
	
	public JO createChallengeTextMessage(String account, String resource) {
		JO challengeRequest = new JO();
		challengeRequest.put("contract", Constants.CONTRACT_NAME);

		JO params = new JO();
		params.put(Constants.MESSAGE_TYPE_KEY, Constants.MESSAGE_TYPE_CHALLENGE_TEXT_REQUEST);
		params.put(Constants.ACCOUNTRS_KEY, account.trim());
		params.put(Constants.RESOURCES_KEY, resource);
		challengeRequest.put("params", params);
		
		return challengeRequest; 
	}
	
	
	public String generateTokenText(String challengeTextResponse, String passphrase) {
		challengeTextResponseObject = JO.parse(challengeTextResponse);
		
		String challengeText = challengeTextResponseObject.getString(Constants.CHALLENGE_TEXT_KEY);
		String token = Token.generateToken(passphrase, challengeText);

		return createTokenText(token);
	}
	
	private String createTokenText(String token) {
		String tokenString = Constants.TOKEN_TAG + "\n";
		tokenString += token + "\n";
		tokenString += Constants.TOKEN_TAG;
		
		return tokenString;
	}
	
	
	public JO createAccountVerificationMessage(String url) {
		String challengeText = challengeTextResponseObject.getString(Constants.CHALLENGE_TEXT_KEY);
		String signature = challengeTextResponseObject.getString(Constants.SIGNATURE_KEY);
		
		JO accountVerification = new JO();
		accountVerification.put("contract", Constants.CONTRACT_NAME);

		JO params = new JO();
		params.put(Constants.MESSAGE_TYPE_KEY, Constants.MESSAGE_TYPE_VERIFICATION_REQUEST);
		params.put(Constants.URL_KEY, url.trim());
		params.put(Constants.CHALLENGE_TEXT_KEY, challengeText);
		params.put(Constants.SIGNATURE_KEY, signature);
		accountVerification.put("params", params);
		
		return accountVerification;
	}
	
	
	public JO createInfoRequestMessage() {
		JO infoRequest = new JO();
		infoRequest.put("contract", Constants.CONTRACT_NAME);
		
		JO params = new JO();
		params.put(Constants.MESSAGE_TYPE_KEY, Constants.MESSAGE_TYPE_INFO_REQUEST);
		infoRequest.put("params", params);
		
		return infoRequest;
	}
	
	
	public JO sendMessage(SendMessageParams params) throws Exception {
		responsedMessage = new JO();
		
		if(params.isEncryptedMessage) params.encryptedMessage = encryptMessage(params);

		
		TransactionHandler transactionHandler = new TransactionHandler();
		transactionHandler.transmitTransaction(params);
		transactionHandler.receiveTransaction(params, (transaction) -> {
			
			JO messageObject = JO.parse(params.message);
			boolean stopSearching = false;
			switch (messageObject.getJo("params").getString(Constants.MESSAGE_TYPE_KEY)) {
			
				case Constants.MESSAGE_TYPE_INFO_REQUEST:
					if(isPaymentTransaction(transaction)) {
						responsedMessage = extractMessage(params, transaction);
						if(isExpectedMessage(Constants.MESSAGE_TYPE_INFO_RESPONSE) || isErrorMessage()) stopSearching = true;
					}
					break;
					
					
				case Constants.MESSAGE_TYPE_CHALLENGE_TEXT_REQUEST:
					if(isPaymentTransaction(transaction)) {
						responsedMessage = extractMessage(params, transaction);
						if(isExpectedMessage(Constants.MESSAGE_TYPE_CHALLENGE_TEXT_RESPONSE) || isErrorMessage()) stopSearching = true;
					}
					break;
					
					
				case Constants.MESSAGE_TYPE_VERIFICATION_REQUEST:
					if(isPaymentTransaction(transaction)) {
						responsedMessage = extractMessage(params, transaction);
						if(isErrorMessage() && isNotOverpaidError()) stopSearching = true;
						
					} else if(isAccountPropertyTransaction(transaction)) {
						stopSearching = true;
						responsedMessage = transaction.getJson();
					}
					break;
	
					
				default:
					break;
			}
			
			return stopSearching;
		});
		
		
		if(isErrorMessage() && isNotOverpaidError()) throw new MessageException(responsedMessage);
		return responsedMessage;
	}
	
	private EncryptedData encryptMessage(SendMessageParams params) {
		JO getPublicKeyResponse = GetAccountPublicKeyCall.create()
			.account(params.recipient)
			.remote(params.url)
			.call();

		byte[] recipientPublicKey = Convert.parseHexString(getPublicKeyResponse.getString("publicKey"));
		byte[] compressedMessage = Convert.compress(Convert.toBytes(params.message));
		
		return EncryptedData.encrypt(compressedMessage, params.passphrase, recipientPublicKey);
	}
	
	private boolean isPaymentTransaction(TransactionResponse transaction) {
		if(transaction.getType() == PaymentTransactionType.ORDINARY.getType() &&
		   transaction.getSubType() == PaymentTransactionType.ORDINARY.getSubtype()) {
			return true;
		}
		return false;
	}

	private boolean isAccountPropertyTransaction(TransactionResponse transaction) {
		JO transactionJson = transaction.getJson();
		int type = transactionJson.getInt("type");
		int subtype = transactionJson.getInt("subtype"); // transaction.getSubType() returns same value as transaction.getType(). Seems to be a bug

		if(type == AccountPropertyTransactionType.ACCOUNT_PROPERTY_SET.getType() &&
		   subtype == AccountPropertyTransactionType.ACCOUNT_PROPERTY_SET.getSubtype()) {
			return true;
		}
		return false;
	}
	
	private JO extractMessage(SendMessageParams params, TransactionResponse transaction) {
		if(transaction.getAttachmentJson().isExist("encryptedMessage")) {
			return JO.parse(decryptMessage(params, transaction));
		} else {
			return JO.parse(transaction.getAttachmentJson().getString("message"));
		}
	}
	
	private String decryptMessage(SendMessageParams params, TransactionResponse transaction) {
		JO encryptedMessage = transaction.getAttachmentJson().getJo("encryptedMessage");
		
		JO decryptedMessage = DecryptFromCall.create()
				.account(params.recipient)
				.secretPhrase(params.passphrase)
				.data(encryptedMessage.getString("data"))
				.nonce(encryptedMessage.getString("nonce"))
				.remote(params.url)
				.call();

		return decryptedMessage.getString("decryptedMessage");
	}
	
	private boolean isExpectedMessage(String messageType) {
			String type = responsedMessage.getString(Constants.MESSAGE_TYPE_KEY);
			if(type == null || type.equals("")) return false;
			return type.equals(messageType);
	}
	
	private boolean isErrorMessage() {
			return responsedMessage.containsKey("errorDescription") && responsedMessage.containsKey("errorCode"); 
	}
	
	private boolean isNotOverpaidError() {
			return responsedMessage.getInt("errorCode") != Constants.ERROR_CODE_OVERPAID;
	}
	
	
	public double calculateFee(SendMessageParams params) throws Exception {
			return (new TransactionHandler()).calculateMinimumFee(params);
	}
}

