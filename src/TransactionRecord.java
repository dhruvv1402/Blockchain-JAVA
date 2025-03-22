import java.util.Date;

public class TransactionRecord {
    private String fromAddress;
    private String toAddress;
    private double amount;
    private long timestamp;

    public TransactionRecord(String fromAddress, String toAddress, double amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.timestamp = new Date().getTime();
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}