package logic;


import nxt.account.Token;
import nxt.addons.JO;

public class LogicApi {
	
	private static JO challengeTextResponseObject;
	
	
	
	public static String createChallengeTextMessage(String account, String resource) {
		JO challengeRequest = new JO();
		challengeRequest.put("contract", Constants.CONTRACT_NAME);

        JO params = new JO();
        params.put(Constants.MESSAGE_TYPE_KEY, Constants.MESSAGE_TYPE_CHALLENGE_TEXT_REQUEST);
        params.put(Constants.ACCOUNTRS_KEY, account.trim());
        params.put(Constants.RESOURCES_KEY, resource);
        challengeRequest.put("params", params);
		
		return challengeRequest.toJSONString(); 
	}
	
	
	public static String generateTokenText(String challengeTextResponse, String passphrase) {
		challengeTextResponseObject = JO.parse(challengeTextResponse);
		
		String challengeText = challengeTextResponseObject.getString(Constants.CHALLENGE_TEXT_KEY);
		String token = Token.generateToken(passphrase, challengeText);
		return createTokenText(token);
	}
	
	private static String createTokenText(String token) {
        String tokenString = Constants.TOKEN_TAG + "\n";
        tokenString += token + "\n";
        tokenString += Constants.TOKEN_TAG;
        return tokenString;
    }
	
	
	public static String createAccountVerificationMessage(String url) {
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
        
        return accountVerification.toJSONString();
	}
	
	
	public static String createInfoRequestMessage() {
		JO infoRequest = new JO();
		infoRequest.put("contract", Constants.CONTRACT_NAME);
		
		JO params = new JO();
        params.put(Constants.MESSAGE_TYPE_KEY, Constants.MESSAGE_TYPE_INFO_REQUEST);
        infoRequest.put("params", params);
		
        return infoRequest.toJSONString();
	}
}
