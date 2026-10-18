package mediaconversion.facade;

import java.util.Locale;

import mediaconversion.subsystem.AudioMixer;
import mediaconversion.subsystem.BitrateReader;
import mediaconversion.subsystem.Codec;
import mediaconversion.subsystem.CodecFactory;
import mediaconversion.subsystem.MetadataReader;

public class MediaConversionFacade {

    private final MetadataReader metadataReader = new MetadataReader();
    private final BitrateReader bitrateReader = new BitrateReader();
    private final CodecFactory codecFactory = new CodecFactory();
    private final AudioMixer audioMixer = new AudioMixer();

    public String convert(String fileName, String targetFormat) {
        try {
            String baseName = metadataReader.readBaseName(fileName);
            String sourceFormat = metadataReader.readFormat(fileName);
            int durationSeconds = metadataReader.readDurationSeconds(fileName);

            Codec codec = codecFactory.createCodec(targetFormat);
            int sourceBitrate = bitrateReader.readBitrate(sourceFormat);
            int targetBitrate = bitrateReader.limitBitrate(codec.getFormat(), sourceBitrate);

            byte[] samples = audioMixer.loadSamples(fileName, durationSeconds);
            byte[] normalizedSamples = audioMixer.normalize(samples);
            codec.encode(normalizedSamples, targetBitrate);

            return baseName + "." + codec.getFormat().toLowerCase(Locale.ROOT);
        } catch (IllegalArgumentException e) {
            throw new MediaConversionException("Conversion failed for '" + fileName + "': " + e.getMessage(), e);
        }
    }
}