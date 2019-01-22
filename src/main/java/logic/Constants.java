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

    public static final String CHALLENGE_TEXT_KEY = "challengeText";
    public static final String SIGNATURE_KEY = "signature";
    public static final String URL_KEY = "url";
    public static final String RESOURCES_KEY = "resources";
    public static final String VERSION_KEY = "version";
    public static final String PRICE_KEY = "price";
    public static final String CHAIN_KEY = "chain";
    public static final String ACCOUNTRS_KEY = "accountRS";
    
    
    /**********************************************************************************
     * TOKEN CLAIM CONSTANTS
     *********************************************************************************/

    public static final String TOKEN_TAG = "** Ardor Identity Verification Token **";
}
