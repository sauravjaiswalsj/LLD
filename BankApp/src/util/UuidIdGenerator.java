package util;

import java.util.UUID;

public class UuidIdGenerator implements IdGenerator {
    @Override
    public String next() {
        return UUID.randomUUID().toString(); // 2f4g2344242
    }
}
