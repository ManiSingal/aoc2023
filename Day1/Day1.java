package Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {
    private static final String FILE_PATH = "/home/mani/AOC2023/Day1/input.txt";

    public static void main(final String[] args) {
        try {
            System.out.println("Total calibration sum: " + calculateSum(FILE_PATH));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static int calculateSum(String filePath) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int totalSum = 0;

            while ((line = reader.readLine()) != null) {
                totalSum += extractCalibrationValue(line);
            }

            return totalSum;
        }
    }

    private static int extractCalibrationValue(final String line) {
        final Pattern pattern = Pattern.compile("\\d");
        final Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            int firstDigit = Integer.parseInt(matcher.group());
            int lastDigit = firstDigit;

            while (matcher.find()) {
                lastDigit = Integer.parseInt(matcher.group());
            }

            return firstDigit * 10 + lastDigit;
        }

        return 0;
    }
}
