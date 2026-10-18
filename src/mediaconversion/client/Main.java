package mediaconversion.client;

import mediaconversion.facade.MediaConversionException;
import mediaconversion.facade.MediaConversionFacade;

public class Main {

    public static void main(String[] args) {
        MediaConversionFacade converter = new MediaConversionFacade();

        convertAndPrint(converter, "song.wav", "mp3");
        convertAndPrint(converter, "podcast.mp3", "ogg");
        convertAndPrint(converter, "track.aac", "flac");
        convertAndPrint(converter, "noextension", "mp3");
    }

    private static void convertAndPrint(MediaConversionFacade converter, String fileName, String targetFormat) {
        try {
            String outputFileName = converter.convert(fileName, targetFormat);
            System.out.println("OK: " + fileName + " -> " + outputFileName);
        } catch (MediaConversionException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}