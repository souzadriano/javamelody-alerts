package com.souzadriano.javamelodyalert;

import java.io.IOException;

import org.jrobin.core.FetchData;
import org.jrobin.core.FetchRequest;
import org.jrobin.core.RrdDb;
import org.jrobin.core.RrdDbPool;
import org.jrobin.core.RrdException;

final class JRobin {

    private static RrdDbPool rrdPool;

    static {
        try {
            rrdPool = getRrdDbPool();
        } catch (IOException e) {
            // TODO log
            e.printStackTrace();
        }
    }

    private JRobin() {
    }

    static double getLastValue(String rrdFileName, String dataSourceName) throws IOException {
        try {
            final RrdDb rrdDb = rrdPool.requestRrdDb(rrdFileName);
            try {
                return rrdDb.getLastDatasourceValue(dataSourceName);
            } finally {
                rrdDb.close();
                rrdPool.release(rrdDb);
            }
        } catch (final RrdException e) {
            throw createIOException(e);
        }
    }

    static FetchData fetch(String rrdFileName, String operation, Long start, Long end) throws IOException {
        try {
            final RrdDb rrdDb = rrdPool.requestRrdDb(rrdFileName);
            try {
                FetchRequest fetchRequest = rrdDb.createFetchRequest(operation, start, end);
                return fetchRequest.fetchData();
            } finally {
                rrdDb.close();
                rrdPool.release(rrdDb);
            }
        } catch (final RrdException e) {
            throw createIOException(e);
        }
    }

    private static RrdDbPool getRrdDbPool() throws IOException {
        try {
            return RrdDbPool.getInstance();
        } catch (final RrdException e) {
            throw createIOException(e);
        }
    }

    private static IOException createIOException(Exception e) {
        return new IOException(e.getMessage(), e);
    }

}
