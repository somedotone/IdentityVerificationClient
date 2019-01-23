package logic;

public class Constants {
	private Constants() {}

	
	/**********************************************************************************
	 * CONTRACT CONSTANTS
	 *********************************************************************************/
	
	public static final String CONTRACT_NAME="IdentityVerification";
	
	
	/**********************************************************************************
	 * MESSAGE CONSTANTS
	 *********************************************************************************/
	
	public static final String PARAMETER_OBJECT_NAME = "setupParams";
	
	public static final String MESSAGE_TYPE_KEY = "type";
	
	public static final String MESSAGE_TYPE_CHALLENGE_TEXT_REQUEST = "challengeTextRequest";
	public static final String MESSAGE_TYPE_CHALLENGE_TEXT_RESPONSE = "challengeTextResponse";
	public static final String MESSAGE_TYPE_VERIFICATION_REQUEST = "accountVerificationRequest";
	public static final String MESSAGE_TYPE_INFO_REQUEST = "infoRequest";
	public static final String MESSAGE_TYPE_INFO_RESPONSE = "infoResponse";
	public static final String MESSAGE_TYPE_FEE_CALCULATION = "calulateFee";
	
	public static final String CHALLENGE_TEXT_KEY = "challengeText";
	public static final String SIGNATURE_KEY = "signature";
	public static final String URL_KEY = "url";
	public static final String RESOURCES_KEY = "resources";
	public static final String VERSION_KEY = "version";
	public static final String PRICE_KEY = "price";
	public static final String CHAIN_KEY = "chain";
	public static final String ACCOUNTRS_KEY = "accountRS";
	public static final String EXPIRATION_MINUTES_KEY = "expirationMinutes";
	public static final String CONTRACT_DOMAIN_KEY = "contractDomain";
	
	
	/**********************************************************************************
	 * TOKEN CLAIM CONSTANTS
	 *********************************************************************************/
	
	public static final String TOKEN_TAG = "** Ardor Identity Verification Token **";
	
	
	/**********************************************************************************
	 * TRANSACTION CONSTANTS
	 *********************************************************************************/
	
	public static final long RESPONSE_TIMEOUT_SECONDS = 180;
	
	
	/**********************************************************************************
	 * ERROR CONSTANTS
	 *********************************************************************************/
	
	public static final int ERROR_CODE_OVERPAID = 10012;
	
	
	/**********************************************************************************
	 * GUI CONSTANTS
	 *********************************************************************************/
	
	public static final int WAITING_SINGAL_UPDATE_TIME = 1; // seconds
	public static final int WAITING_SINGAL_LENGTH = 30; // characters
}
