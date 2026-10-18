package mediaconversion.subsystem;

import java.util.Arrays;

public class Codec {

    private static final int MAX_BITRATE = 1411;

    private final String format;

    public Codec(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public byte[] encode(byte[] samples, int bitrate) {
        if (samples == null) {
            throw new IllegalArgumentException("Samples must not be null");
        }
        if (bitrate <= 0) {
            throw new IllegalArgumentException("Bitrate must be positive: " + bitrate);
        }
        int encodedLength = Math.max(1, samples.length * Math.min(bitrate, MAX_BITRATE) / MAX_BITRATE);
        return Arrays.copyOf(samples, encodedLength);
    }
}