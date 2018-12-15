import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.utils.BlockFileLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Example {

    public static void main(String[] args) {
        // Arm the blockchain file loader.
        NetworkParameters mainNetParams = new MainNetParams();
        List<File> blockChainFiles = new ArrayList<>();
        blockChainFiles.add(new File("./btc-data/blocks/blk00000.dat"));
        BlockFileLoader loader = new BlockFileLoader(mainNetParams, blockChainFiles);

        // Data structures to keep the statistics.
        Map<String, Integer> monthlyTxCount = new HashMap<>();
        Map<String, Integer> monthlyBlockCount = new HashMap<>();

        Context.getOrCreate(MainNetParams.get());

        // Iterate over the blocks in the dataset.
        for (Block block : loader) {

            // Extract the month keyword.
            String month = new SimpleDateFormat("yyyy-MM").format(block.getTime());

            // Make sure there exists an entry for the extracted month.
            if (!monthlyBlockCount.containsKey(month)) {
                monthlyBlockCount.put(month, 0);
                monthlyTxCount.put(month, 0);
            }

            // Update the statistics.
            monthlyBlockCount.put(month, 1 + monthlyBlockCount.get(month));
            monthlyTxCount.put(month, block.getTransactions().size() + monthlyTxCount.get(month));
        }

        // Compute the average number of transactions per block per month.
        Map<String, Float> monthlyAvgTxCountPerBlock = new HashMap<>();
        for (String month : monthlyBlockCount.keySet())
            monthlyAvgTxCountPerBlock.put(
                    month, (float) monthlyTxCount.get(month) / monthlyBlockCount.get(month));

        System.out.println(monthlyAvgTxCountPerBlock);
    }

}
