package util;

import java.util.concurrent.atomic.AtomicLong;

public class SequentialIdGenerator implements IdGenerator {
    private final String prefix;
    private final AtomicLong counter = new AtomicLong();

    public SequentialIdGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String next() {
        return  prefix + "-" + counter.incrementAndGet();
    }
}
