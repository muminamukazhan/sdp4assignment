package mediaconversion.subsystem;

public class AudioMixer {

    private static final int SAMPLES_PER_SECOND = 10;

    public byte[] loadSamples(String fileName, int durationSeconds) {
        byte[] samples = new byte[durationSeconds * SAMPLES_PER_SECOND];
        int seed = fileName.hashCode();
        for (int i = 0; i < samples.length; i++) {
            samples[i] = (byte) (Math.sin((i + seed) * 0.05) * 40);
        }
        return samples;
    }

    public byte[] normalize(byte[] samples) {
        int peak = 1;
        for (byte sample : samples) {
            peak = Math.max(peak, Math.abs(sample));
        }
        byte[] normalized = new byte[samples.length];
        for (int i = 0; i < samples.length; i++) {
            normalized[i] = (byte) (samples[i] * 127 / peak);
        }
        return normalized;
    }
}