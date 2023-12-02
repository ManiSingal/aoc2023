package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private static final String FILE_PATH = "/home/mani/AOC2023/Day2/input.txt";

    public static void main(final String[] args) {
        try {
            System.out.println("Sum of the power of the minimum sets: " + sumOfThePowerOfMinimumSets());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static long sumOfThePowerOfMinimumSets() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            long sumOfPower = 0;
            while ((line = reader.readLine()) != null) {
                sumOfPower += minimumPowerOfGame(line);
            }

            return sumOfPower;
        }
    }

    private static long minimumPowerOfGame(String gameData) {
        Pattern pattern = Pattern.compile("(\\d+) (red|green|blue)");
        Matcher matcher = pattern.matcher(gameData);

        long minimumRed = 1;
        long minimumGreen = 1;
        long minimumBlue = 1;

        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);

            if ("red".equalsIgnoreCase(color) && count > minimumRed) {
                minimumRed = count;
            }

            if ("green".equalsIgnoreCase(color) && count > minimumGreen) {
                minimumGreen = count;
            }

            if ("blue".equalsIgnoreCase(color) && count > minimumBlue) {
                minimumBlue = count;
            }
        }
        return minimumRed * minimumGreen * minimumBlue;
    }
}