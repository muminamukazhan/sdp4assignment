package mediaconversion.subsystem;

import java.util.Locale;
import java.util.Set;

public class CodecFactory {

    private static final Set<String> SUPPORTED_FORMATS = Set.of("WAV", "MP3", "AAC", "OGG");

    public Codec createCodec(String format) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Target format must not be empty");
        }
        String normalizedFormat = format.toUpperCase(Locale.ROOT);
        if (!SUPPORTED_FORMATS.contains(normalizedFormat)) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        return new Codec(normalizedFormat);
    }
}