package com.hhxy.utils;

public class IdWorker {
    private final long workerId = 1L;
    private final long epoch = 1704067200000L;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) throw new RuntimeException("Clock moved backwards!");

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & 4095;
            if (sequence == 0) timestamp = tilNextMillis(lastTimestamp);
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return ((timestamp - epoch) << 22) | (workerId << 12) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) timestamp = System.currentTimeMillis();
        return timestamp;
    }
}
