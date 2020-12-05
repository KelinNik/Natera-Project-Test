package utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;

public class CommonUtils {

    private CommonUtils() {
        throw new IllegalAccessError("Utility class. Do not instantiate!");
    }

    public static String getResourceAsString(String fileName) {
        try (final InputStreamReader in = new InputStreamReader(
                Objects.requireNonNull(CommonUtils.class.getClassLoader().getResourceAsStream(fileName))
        )) {
            return IOUtils.toString(in);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
