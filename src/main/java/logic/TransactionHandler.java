package logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nxt.account.Account;
import nxt.addons.JO;
import nxt.blockchain.Chain;
import nxt.blockchain.ChildChain;
import nxt.crypto.Crypto;
import nxt.http.callers.BroadcastTransactionCall;
import nxt.http.callers.GetBlockchainStatusCall;
import nxt.http.callers.GetBlockchainTransactionsCall;
import nxt.http.callers.GetBundlerRatesCall;
import nxt.http.callers.SendMoneyCall;
import nxt.http.callers.SignTransactionCall;
import nxt.http.responses.TransactionResponse;
import nxt.util.Convert;

public class TransactionHandler {
	
	public void transmitTransaction(TransactionParams params) {
		String transactionJSON = createTransaction(params);
		JO signedTransaction = signTransaction(params, transactionJSON); 
		
		broadcastTransaction(params, signedTransaction);
	}
	
	private String createTransaction(TransactionParams params) {
		JO transactionResponse = createSendMoneyTransaction(params);
		return transactionResponse.getJo("transactionJSON").toJSONString();
	}
	
	private JO createSendMoneyTransaction(TransactionParams params) {
		long oneCoin = ChildChain.getChildChain(params.chain).ONE_COIN;
		
		SendMoneyCall sendMoneyCall = SendMoneyCall.create(params.chain)
				.broadcast(false)
				.recipient(params.recipient)
				.publicKey(Crypto.getPublicKey(params.passphrase))
				.amountNQT((long) (params.amount * oneCoin))
				.feeNQT((long) (params.fee * oneCoin))
				.remote(params.url);
		
		if(params.isEncryptedMessage) {
			sendMoneyCall.encryptedMessageData(params.encryptedMessage.getData());
			sendMoneyCall.encryptedMessageNonce(params.encryptedMessage.getNonce());
			sendMoneyCall.encryptedMessageIsPrunable(true);
		} else {
			sendMoneyCall.message(params.message);
			sendMoneyCall.messageIsPrunable(true);
		}
		
		return sendMoneyCall.call();
	}
	
	private JO signTransaction(TransactionParams params, String transactionJSON) {
		return SignTransactionCall.create()
				.unsignedTransactionJSON(transactionJSON)
				.secretPhrase(params.passphrase)
				.validate("false")
				.call();
	}
	
	private void broadcastTransaction(TransactionParams params, JO signTransactionResponse) {
		BroadcastTransactionCall.create()
				.transactionJSON(signTransactionResponse.getString("transactionJSON"))
				.remote(params.url)
				.call();
	}
	
	
	public void receiveTransaction(TransactionParams params, TransactionCallback callback)throws InterruptedException, TransactionException {
		int epochTime = getEpochTime(params.url);
		String ownRS = Convert.rsAccount(Account.getId(Crypto.getPublicKey(params.passphrase)));
		
		long startTime = getTimestampSeconds();
		long expirationTime = startTime + Constants.RESPONSE_TIMEOUT_SECONDS;
		long currentTime = startTime;
		
		
		while (currentTime <= expirationTime) {
			
			for(TransactionResponse transaction : getTransactions(params.chain, epochTime, ownRS, params.url)) {
				if(transaction.getRecipientRs().equals(ownRS) && transaction.getSenderRs().equals(params.recipient)) {
					boolean stopSearching = callback.processTransaction(transaction);
					if(stopSearching) return;
				}
			}
		
			TimeUnit.SECONDS.sleep(1);
			currentTime = getTimestampSeconds();
		}
		
		throw new TransactionException("timeout");
	}
	
	private int getEpochTime(URL url) {
		JO getBlockchainStatusResponse = GetBlockchainStatusCall.create()
				.remote(url)
				.call();
		return getBlockchainStatusResponse.getInt("time");
	}
	
	private long getTimestampSeconds() {
			return (new Date()).getTime() / 1000;
	}
	
	private List<TransactionResponse> getTransactions(int chain, int epochTime, String accountRs, URL url) {
		return GetBlockchainTransactionsCall.create(chain)
				.timestamp(epochTime)
				.account(accountRs)
				.executedOnly(true)
				.remote(url)
				.getTransactions();
	}
	
	
	public double calculateMinimumFee(TransactionParams params) throws Exception {
		long minimumParentChainFeeFQT = getMinimumParentChainFee(params);
		long feeRateNQTPerFXT = getBestBundlingFee(params, minimumParentChainFeeFQT);
		long feeNQT = calculateFeeNQT(params, minimumParentChainFeeFQT, feeRateNQTPerFXT);
			
		long oneCoin = ChildChain.getChildChain(params.chain).ONE_COIN;
		return (double) feeNQT / oneCoin;
	}
	
	private long getMinimumParentChainFee(TransactionParams params) throws Exception {
		JO transactionResponse = createSendMoneyTransaction(params);
		System.out.println(transactionResponse);
		if(transactionResponse.isExist("errorCode")) throw new Exception(transactionResponse.toString());
		
		return transactionResponse.getLong("minimumFeeFQT");
	}
	
	private long getBestBundlingFee(TransactionParams params, long minBundlerBalanceFXT) {
		 JO response = GetBundlerRatesCall.create().minBundlerBalanceFXT(minBundlerBalanceFXT).remote(params.url).call();
		 List<JO> rates = response.getArray("rates").objects();
		 Long bestRate = rates.stream().filter(r -> r.getInt("chain") == params.chain).map(r -> r.getLong("minRateNQTPerFXT")).sorted().findFirst().orElse(null);
		 
		 if (bestRate == null) throw new IllegalStateException("Best bundling fee cannot be determined");
		 return bestRate;
	}
	
	private long calculateFeeNQT(TransactionParams params, long minBundlerBalanceFXT, long feeRateNQTPerFXT) {
		long oneCoin = Chain.getChain(params.chain).ONE_COIN;
		return BigDecimal.valueOf(minBundlerBalanceFXT).multiply(BigDecimal.valueOf(feeRateNQTPerFXT)).divide(BigDecimal.valueOf(oneCoin), RoundingMode.HALF_EVEN).longValue();
	}
}
