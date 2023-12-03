package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {
    private static final int SIZE = 140;
    private static final char[][] ENGINE_SCHEMATIC = new char[SIZE][SIZE];
    private static final String FILE_PATH = "/home/mani/AOC/aoc2023/Day3/input.txt";

    public static void main(final String[] args) {
        try {
            System.out.println("Sum of the part numbers in the engine schematic: " + calculateSumOfPartNumbers());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateSumOfPartNumbers() throws IOException {
        loadSchematic();

        long totalSum = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (Character.isDigit(ENGINE_SCHEMATIC[row][col])) {
                    String partNumber = getPartNumber(row, col);
                    col += partNumber.length() - 1;

                    if (isAdjacentToSymbol(row, col - partNumber.length() + 1, col)) {
                        totalSum += Integer.parseInt(partNumber);
                    }
                }
            }
        }
        return totalSum;
    }

    private static String getPartNumber(final int row, int col) {
        final StringBuilder partNumber = new StringBuilder();
        while (col < SIZE && Character.isDigit(ENGINE_SCHEMATIC[row][col])) {
            partNumber.append(ENGINE_SCHEMATIC[row][col]);
            col++;
        }
        return partNumber.toString();
    }

    private static void loadSchematic() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                ENGINE_SCHEMATIC[row++] = line.toCharArray();
            }
        }
    }

    private static boolean isAdjacentToSymbol(final int row, final int colStart, int colEnd) {
        int start = Math.max(colStart - 1, 0);
        int end = Math.min(colEnd + 1, SIZE - 1);

        if (isAdjacentInRow(row - 1, start, end) || isAdjacentInRow(row + 1, start, end)) {
            return true;
        }

        if (colStart > 0 && isSymbol(ENGINE_SCHEMATIC[row][colStart - 1])) {
            return true;
        }
        if (colEnd < SIZE - 1 && isSymbol(ENGINE_SCHEMATIC[row][colEnd + 1])) {
            return true;
        }

        return false;
    }

    private static boolean isAdjacentInRow(int row, int startCol, int endCol) {
        if (row < 0 || row >= SIZE)
            return false;
        for (int col = startCol; col <= endCol; col++) {
            if (isSymbol(ENGINE_SCHEMATIC[row][col])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSymbol(char ch) {
        return !Character.isDigit(ch) && ch != '.';
    }
}