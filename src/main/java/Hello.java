import com.google.gson.Gson;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.utils.BlockFileLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {

    public static void main(String[] args) {
        // Arm the blockchain file loader.
        NetworkParameters mainNetParams = new MainNetParams();
        List<File> blockChainFiles = new ArrayList<>();
        blockChainFiles.add(new File("./btc-data/blocks/blk00000.dat"));
        BlockFileLoader loader = new BlockFileLoader(mainNetParams, blockChainFiles);

        Context.getOrCreate(MainNetParams.get());

        // Iterate over the blocks in the dataset.
        for (Block block : loader) {
            System.out.println(block);
            break;
        }

    }

}
