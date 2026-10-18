package mediaconversion.subsystem;

import java.util.Locale;

public class MetadataReader {

    public String readBaseName(String fileName) {
        return fileName.substring(0, findExtensionIndex(fileName));
    }

    public String readFormat(String fileName) {
        return fileName.substring(findExtensionIndex(fileName) + 1).toUpperCase(Locale.ROOT);
    }

    public int readDurationSeconds(String fileName) {
        findExtensionIndex(fileName);
        return 60 + Math.floorMod(fileName.hashCode(), 240);
    }

    private int findExtensionIndex(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name must not be empty");
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("File has no valid extension: " + fileName);
        }
        return dotIndex;
    }
}