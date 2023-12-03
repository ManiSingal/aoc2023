package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day3 {
    private static final int SIZE = 140;
    private static final char[][] ENGINE_SCHEMATIC = new char[SIZE][SIZE];
    private static final String FILE_PATH = "/home/mani/AOC/aoc2023/Day3/input.txt";

    private static final Map<Gear, List<Integer>> m_gearToAdjacentPartNumbers = new HashMap<>();

    public static void main(final String[] args) {
        try {
            System.out.println("Sum of the gear ratios in the engine schematic: " + calculateSumOfGearRatios());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateSumOfGearRatios() throws IOException {
        loadSchematic();

        long totalSum = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (Character.isDigit(ENGINE_SCHEMATIC[row][col])) {
                    String partNumber = getPartNumber(row, col);
                    col += partNumber.length() - 1;
                    checkIfAdjacentToGear(row, col - partNumber.length() + 1, col, partNumber);
                }
            }
        }

        for (final List<Integer> partNumbers : m_gearToAdjacentPartNumbers.values()) {
            if (partNumbers.size() == 2) {
                totalSum += partNumbers.get(0) * partNumbers.get(1);
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

    private static void checkIfAdjacentToGear(final int row, final int colStart, final int colEnd,
            final String partNumber) {
        final int start = Math.max(colStart - 1, 0);
        final int end = Math.min(colEnd + 1, SIZE - 1);

        if (isAdjacentInRow(row - 1, start, end, partNumber) || isAdjacentInRow(row + 1, start, end, partNumber)) {
            return;
        }

        if (colStart > 0 && isGear(row, colStart - 1, partNumber)) {
            return;
        }
        if (colEnd < SIZE - 1 && isGear(row, colEnd + 1, partNumber)) {
            return;
        }

        return;
    }

    private static boolean isAdjacentInRow(final int row, final int startCol, final int endCol,
            final String partNumber) {
        if (row < 0 || row >= SIZE)
            return false;
        for (int col = startCol; col <= endCol; col++) {
            if (isGear(row, col, partNumber)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isGear(final int row, final int col, final String partNumber) {
        if (ENGINE_SCHEMATIC[row][col] == '*') {
            final Gear gear = new Gear(row, col);
            if (m_gearToAdjacentPartNumbers.containsKey(gear)) {
                m_gearToAdjacentPartNumbers.get(gear).add(Integer.parseInt(partNumber));
            } else {
                final List<Integer> partNumbers = new ArrayList<>();
                partNumbers.add(Integer.parseInt(partNumber));
                m_gearToAdjacentPartNumbers.put(gear, partNumbers);
            }

            return true;
        }

        return false;
    }

    private static class Gear {
        private int m_row;
        private int m_col;

        public Gear(final int row, final int col) {
            m_row = row;
            m_col = col;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Gear)) {
                return false;
            }

            final Gear gear = (Gear) o;
            return m_row == gear.m_row && m_col == gear.m_col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(m_row, m_col);
        }
    }
}