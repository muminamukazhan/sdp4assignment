# Media Conversion Facade

 **Author:** Mumina Mukazhan
 **Group:** SE-2530


## Idea

Converting a media file takes several dependent steps spread over several classes. Instead of making the client create these classes and call them in the right order, the project provides a single entry point:

```java
MediaConversionFacade converter = new MediaConversionFacade();
String result = converter.convert("song.wav", "mp3");   // "song.mp3"
```

Internally, the facade coordinates the subsystem in this order:

1. `MetadataReader` – reads the base name, source format and duration.
2. `CodecFactory` / `Codec` – validates the target format and creates a codec.
3. `BitrateReader` – reads the source bitrate and limits it for the target format.
4. `AudioMixer` – loads and normalizes the audio samples.
5. `Codec` – encodes the samples; the facade returns the output file name.

## Project structure

```
media-conversion-facade/
├── .gitignore
├── README.md
└── src/mediaconversion/
    ├── client/
    │   └── Main.java
    ├── facade/
    │   ├── MediaConversionFacade.java
    │   └── MediaConversionException.java
    └── subsystem/
        ├── MetadataReader.java
        ├── BitrateReader.java
        ├── CodecFactory.java
        ├── Codec.java
        └── AudioMixer.java
```

| Package | Contents |
|---|---|
| `mediaconversion.subsystem` | Five subsystem classes. They know nothing about the facade and can be used directly. |
| `mediaconversion.facade` | `MediaConversionFacade` (single public method `convert`) and `MediaConversionException`. |
| `mediaconversion.client` | `Main`, which uses only the facade and never imports subsystem classes. |

## Pattern requirements

- **Complex subsystem:** 5 classes, each responsible for one step of the conversion.
- **Facade:** one class with one simple method, `convert(fileName, targetFormat)`.
- **Client:** `Main` imports only `MediaConversionFacade` and `MediaConversionException`.
- **No back-references:** no class in `subsystem` imports anything from `facade`.
- **Direct access remains possible:** advanced clients can use the subsystem classes without the facade.

## Error handling

All `IllegalArgumentException`s thrown by the subsystem (empty name, missing extension, unknown or unsupported format) are wrapped by the facade into one `MediaConversionException` with a clear message that includes the file name. The original exception is kept as the cause.

## Note on simulation

This project is a demonstration model of media conversion. It does not read or write real audio files: the format is taken from the file extension, the duration is simulated, audio samples are generated, and the codec only shortens the byte array according to the bitrate. The goal is to demonstrate the Facade pattern over a multi-class subsystem, not to build a real converter.

## Run

Requirements: JDK 17.

In IntelliJ IDEA, open the project, mark `src` as the sources root and run `mediaconversion.client.Main`.

From the command line:

```bash
javac -d out $(find src -name "*.java")
java -cp out mediaconversion.client.Main
```

### Expected output

```
OK: song.wav -> song.mp3
OK: podcast.mp3 -> podcast.ogg
ERROR: Conversion failed for 'track.aac': Unsupported format: flac
ERROR: Conversion failed for 'noextension': File has no valid extension: noextension
```
