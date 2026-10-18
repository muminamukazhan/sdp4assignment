package mediaconversion.subsystem;

import java.util.Locale;

public class BitrateReader {

    public int readBitrate(String format) {
        return switch (format.toUpperCase(Locale.ROOT)) {
            case "WAV" -> 1411;
            case "MP3" -> 320;
            case "AAC" -> 256;
            case "OGG" -> 192;
            default -> throw new IllegalArgumentException("Unknown source format: " + format);
        };
    }

    public int limitBitrate(String targetFormat, int bitrate) {
        int maxBitrate = switch (targetFormat.toUpperCase(Locale.ROOT)) {
            case "WAV" -> 1411;
            case "MP3" -> 320;
            case "AAC" -> 256;
            case "OGG" -> 500;
            default -> throw new IllegalArgumentException("Unknown target format: " + targetFormat);
        };
        return Math.min(bitrate, maxBitrate);
    }
}