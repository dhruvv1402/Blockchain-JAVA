import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockchainLedger {
    private List<BlockData> chain;
    private List<TransactionRecord> pendingTransactions;
    private int difficulty;
    private double miningReward;

    public BlockchainLedger() {
        this.chain = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.difficulty = 4;
        this.miningReward = 100;

        // Create genesis block
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        List<TransactionRecord> transactions = new ArrayList<>();
        transactions.add(new TransactionRecord("genesis", "genesis", 0));
        BlockData genesisBlock = new BlockData(0, transactions, "0");
        genesisBlock.mineBlock(difficulty);
        chain.add(genesisBlock);
    }

    public BlockData getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void createTransaction(TransactionRecord transaction) {
        pendingTransactions.add(transaction);
    }

    public void minePendingTransactions(String minerAddress) {
        BlockData block = new BlockData(getLatestBlock().getIndex() + 1, pendingTransactions, getLatestBlock().getHash());
        block.mineBlock(difficulty);

        System.out.println("Block mined successfully!");
        chain.add(block);

        // Reset the pending transactions and give miner their reward
        pendingTransactions = new ArrayList<>();
        pendingTransactions.add(new TransactionRecord("System", minerAddress, miningReward));
    }

    public double getBalanceOfAddress(String address) {
        double balance = 0;

        for (BlockData block : chain) {
            for (TransactionRecord transaction : block.getTransactions()) {
                if (transaction.getFromAddress().equals(address)) {
                    balance -= transaction.getAmount();
                }

                if (transaction.getToAddress().equals(address)) {
                    balance += transaction.getAmount();
                }
            }
        }

        return balance;
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            BlockData currentBlock = chain.get(i);
            BlockData previousBlock = chain.get(i - 1);

            // Verify the current block's hash
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Verify the previous hash reference
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            // Verify the nonce produces a valid hash (proof of work)
            String hashTarget = new String(new char[difficulty]).replace('\0', '0');
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                return false;
            }
        }

        return true;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setMiningReward(double miningReward) {
        this.miningReward = miningReward;
    }

    public List<BlockData> getChain() {
        return chain;
    }
}