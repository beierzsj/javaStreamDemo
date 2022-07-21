package com.stream.example.demo;

import com.stream.example.demo.Shoe;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JavaStreamDemoTest {

    private static final List<String> stringsList = Arrays.asList("a", "b", "c");

    private static final Shoe volkswagenGolf = new Shoe(0, "Volkswagen", "L", "blue");
    private static final Shoe skodaOctavia = new Shoe(1, "Skoda", "XL", "green");
    private static final Shoe renaultKadjar = new Shoe(2, "Renault", "XXL", "red");
    private static final Shoe volkswagenTiguan = new Shoe(3, "Volkswagen", "M", "red");

    @Test
    public void createStreamsFromCollection() {
        List<String> streamedStrings = stringsList.stream().collect(Collectors.toList());
        assertLinesMatch(stringsList, streamedStrings);
    }

    @Test
    public void createStreamsFromArrays() {
        List<String> streamedStrings = Arrays.stream(new String[]{"a", "b", "c"}).collect(Collectors.toList());
        assertLinesMatch(stringsList, streamedStrings);
    }

    @Test
    public void createStreamsFromStreamOf() {
        List<String> streamedStrings = Stream.of("a", "b", "c").collect(Collectors.toList());
        assertLinesMatch(stringsList, streamedStrings);
    }

    @Test
    public void createStreamsFromIntStream() {
        int[] streamedInts = IntStream.of(1, 2, 3).toArray();
        assertArrayEquals(new int[]{1, 2, 3}, streamedInts);
    }

    @Test
    public void createStreamsFromFile() {
        try {
            List<String> expectedLines = Arrays.asList("file1", "file2", "file3","file4");
            BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"/src/main/resources/file.txt")));
            List<String> streamedLines = reader.lines().collect(Collectors.toList());
            assertLinesMatch(expectedLines, streamedLines);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void filterStream() {
        List<Shoe> expectedShoes = Arrays.asList(volkswagenGolf, volkswagenTiguan);
        List<Shoe> filteredShoes = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .filter(car -> car.getBrand().equals("Volkswagen"))
                .collect(Collectors.toList());
        assertIterableEquals(expectedShoes, filteredShoes);
    }

    @Test
    public void mapStream() {
        List<String> expectedBrands = Arrays.asList("Volkswagen", "Skoda", "Renault", "Volkswagen");
        List<String> brands = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getBrand)
                .collect(Collectors.toList());
        assertIterableEquals(expectedBrands, brands);
    }

    @Test
    public void filterMapStream() {
        List<String> expectedColors = Arrays.asList("blue", "red");
        List<String> volkswagenColors = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .filter(car -> car.getBrand().equals("Volkswagen"))
                .map(Shoe::getColor)
                .collect(Collectors.toList());
        assertIterableEquals(expectedColors, volkswagenColors);
    }

    @Test
    public void distinctStream() {
        List<String> expectedBrands = Arrays.asList("Volkswagen", "Skoda", "Renault");
        List<String> brands = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getBrand)
                .distinct()
                .collect(Collectors.toList());
        assertIterableEquals(expectedBrands, brands);
    }

    @Test
    public void sortedStream() {
        List<String> expectedSortedBrands = Arrays.asList("Renault", "Skoda", "Volkswagen", "Volkswagen");
        List<String> brands = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getBrand)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(expectedSortedBrands, brands);
    }

    @Test
    public void peekStream() {
        List<String> expectedColors = Arrays.asList("blue", "red");
        List<String> volkswagenColors = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .filter(car -> car.getBrand().equals("Volkswagen"))
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(Shoe::getColor)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        assertIterableEquals(expectedColors, volkswagenColors);
    }

    @Test
    public void collectJoinStream() {
        String expectedBrands = "Volkswagen;Skoda;Renault;Volkswagen";
        String joinedBrands = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getBrand)
                .collect(Collectors.joining(";"));
        assertEquals(expectedBrands, joinedBrands);
    }

    @Test
    public void collectSummingIntStream() {
        int sumIds = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .collect(Collectors.summingInt(Shoe::getId));
        assertEquals(6, sumIds);
    }

    @Test
    public void collectGroupingByStream() {
        Map<String, List<Shoe>> expectedShoes = new HashMap<>();
        expectedShoes.put("Skoda", Arrays.asList(skodaOctavia));
        expectedShoes.put("Renault", Arrays.asList(renaultKadjar));
        expectedShoes.put("Volkswagen", Arrays.asList(volkswagenGolf, volkswagenTiguan));

        Map<String, List<Shoe>> groupedShoes = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .collect(Collectors.groupingBy(Shoe::getBrand));

        assertTrue(expectedShoes.equals(groupedShoes));
    }

    @Test
    public void reduceStream() {
        int sumIds = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getId)
                .reduce(0, Integer::sum);
        assertEquals(6, sumIds);
    }

    @Test
    public void forEachStream() {
        Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .forEach(System.out::println);
    }

    @Test
    public void countStream() {
        long count = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan).count();
        assertEquals(4, count);
    }

    @Test
    public void maxStream() {
        int maxId = Stream.of(volkswagenGolf, skodaOctavia, renaultKadjar, volkswagenTiguan)
                .map(Shoe::getId)
                .max((o1, o2) -> o1.compareTo(o2))
                .orElse(-1);
        assertEquals(3, maxId);
    }
}
