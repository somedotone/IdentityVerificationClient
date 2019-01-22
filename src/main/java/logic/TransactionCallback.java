package logic;

import nxt.http.responses.TransactionResponse;

public interface TransactionCallback {
	boolean processTransaction(TransactionResponse transaction);
}
