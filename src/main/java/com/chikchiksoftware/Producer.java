package com.chikchiksoftware;

import com.chikchiksoftware.service.Timer;

import java.sql.Timestamp;


/**
 * Created by
 *
 * @authors Anton Nagornyi
 * on 17.02.2018.
 */
public class Producer implements Runnable {

    private final FifoFileBuffer buffer;
    private final long generateFrequencySeconds;
    private final long timeToWork;

    public Producer(FifoFileBuffer buffer, long generateFrequencySeconds, long timeToWork) {
        this.buffer = buffer;
        this.generateFrequencySeconds = generateFrequencySeconds;
        this.timeToWork = timeToWork;
    }

    public void run() {
        final long start = System.currentTimeMillis();
        long end = 0;

        try {
            while((end - start) <= timeToWork) {
                buffer.put(new Timestamp(System.currentTimeMillis()));
                Timer.incProducedItems();
                Thread.sleep(generateFrequencySeconds);
                end = System.currentTimeMillis();
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
