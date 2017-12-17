package io.github.rob__;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Blockchain;
import org.neo.smartcontract.framework.services.neo.Runtime;
import org.neo.smartcontract.framework.services.neo.Storage;

public class Domain extends SmartContract {

    public static Object Main(String operation, Object[] args) {
        String domain = (String) args[0];

        if (operation == "query") {
            return Query(domain);
        }

        if (operation == "register") {
            return Register(domain, (byte[]) args[1]);
        }

        if (operation == "transfer") {
            return Transfer(domain, (byte[]) args[1]);
        }

        if (operation == "delete") {
            return Delete(domain);
        }

        return false;
    }

    private static byte[] Query(String domain) {
        return Storage.get(Storage.currentContext(), domain);
    }

    private static boolean Register(String domain, byte[] owner) {
        if (!Runtime.checkWitness(owner)) {
            return false;
        }

        byte[] value = Storage.get(Storage.currentContext(), domain);
        if (value != null) {
            return false;
        }

        Storage.put(Storage.currentContext(), domain, owner);
        return true;
    }

    private static boolean Transfer(String domain, byte[] to) {
        if (!Runtime.checkWitness(to)) {
            return false;
        }

        byte[] from = Storage.get(Storage.currentContext(), domain);

        if (from == null) {
            return false;
        }

        if (!Runtime.checkWitness(from)) {
            return false;
        }

        Storage.put(Storage.currentContext(), domain, to);
        return true;
    }

    private static boolean Delete(String domain) {
        byte[] owner = Storage.get(Storage.currentContext(), domain);

        if (owner == null) {
            return false;
        }

        if (!Runtime.checkWitness(owner)) {
            return false;
        }

        Storage.delete(Storage.currentContext(), domain);
        return true;
    }
}
