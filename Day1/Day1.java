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
                final int value = extractCalibrationValue(line);
                System.out.println("Calibration value: " + value);
                totalSum += value;
            }

            return totalSum;
        }
    }

    private static int extractCalibrationValue(String line) {

        line = replaceSpeltOutWords(line);

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

    private static String replaceSpeltOutWords(String line) {
        line = line.replaceAll("one", "o1e");
        line = line.replaceAll("two", "t2o");
        line = line.replaceAll("three", "t3e");
        line = line.replaceAll("four", "f4r");
        line = line.replaceAll("five", "f5e");
        line = line.replaceAll("six", "s6x");
        line = line.replaceAll("seven", "s7n");
        line = line.replaceAll("eight", "e8t");
        line = line.replaceAll("nine", "n9e");
        line = line.replaceAll("zero", "z0o");

        return line;
    }
}
