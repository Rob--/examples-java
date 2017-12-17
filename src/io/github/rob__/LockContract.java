package io.github.rob__;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Blockchain;
import org.neo.smartcontract.framework.services.neo.Header;

public class LockContract extends SmartContract {

    public static boolean Main(int timestamp, byte[] publicKey, byte[] signature) {
        Header header = Blockchain.getHeader(Blockchain.height());

        if (header.timestamp() < timestamp) {
            return false;
        }

        return verifySignature(signature, publicKey);
    }
}
