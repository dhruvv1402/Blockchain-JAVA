import java.util.Date;
public class BlockchainUsageExample {
    public static void main(String[] args) {
        // Create a new blockchain
        BlockchainLedger cryptoCoin = new BlockchainLedger();

        System.out.println("Mining genesis block...");

        // Create some transactions
        System.out.println("Creating transactions...");
        cryptoCoin.createTransaction(new TransactionRecord("address1", "address2", 100));
        cryptoCoin.createTransaction(new TransactionRecord("address2", "address1", 50));

        System.out.println("Mining block 1...");
        cryptoCoin.minePendingTransactions("miner-address");

        System.out.println("Balance of miner: " + cryptoCoin.getBalanceOfAddress("miner-address"));

        // Create more transactions
        cryptoCoin.createTransaction(new TransactionRecord("address1", "address2", 200));
        cryptoCoin.createTransaction(new TransactionRecord("address2", "address1", 75));

        System.out.println("Mining block 2...");
        cryptoCoin.minePendingTransactions("miner-address");

        System.out.println("Balance of miner: " + cryptoCoin.getBalanceOfAddress("miner-address"));

        // Verify blockchain integrity
        System.out.println("Is blockchain valid? " + cryptoCoin.isChainValid());

        // Print the blockchain
        System.out.println("\nBlockchain: ");
        for (BlockData block : cryptoCoin.getChain()) {
            System.out.println("Index: " + block.getIndex());
            System.out.println("Timestamp: " + new Date(block.getTimestamp()));
            System.out.println("Hash: " + block.getHash());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Nonce: " + block.getNonce());
            System.out.println("Transactions: " + block.getTransactions().size());
            System.out.println("------------------------");
        }
    }
}