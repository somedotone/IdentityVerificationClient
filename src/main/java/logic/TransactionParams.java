package logic;

import java.net.URL;

import nxt.crypto.EncryptedData;

public class TransactionParams {
	public URL url;
	public String recipient;
	public double amount;
	public double fee;
	public String passphrase;
	public String message;
	public int chain;
	public boolean isEncryptedMessage;
	EncryptedData encryptedMessage;
}
