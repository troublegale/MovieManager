package letsgo.lab6.server.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolStorage {

    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return cachedThreadPool;
    }

}
