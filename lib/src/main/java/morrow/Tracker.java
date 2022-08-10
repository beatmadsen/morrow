package morrow;

import org.tinylog.Logger;

public class Tracker {

    public static void actionComplete(MetaData metaData) {
        Logger.debug("Successfully processed request");
    }

    public static void clientException(MetaData md, Exception e) {
        Logger.debug("Client exception occurred", e);
    }

    public static void serverException(MetaData md, Exception e) {
        Logger.error("Server exception occurred", e);
    }

    public record MetaData() {

    }
}
