package com.tomwallace.adventofcode2024.java.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtility {

    public static String testDataPath = "src/test/resources/data/";
    public static String dataPath = "src/main/resources/data/";

    /***
     * Splits a file into values of type T by carriage return, using a defined parser function.
     * Returns the values as a List of type T
     * @param filePath - The path of the file, relative to the root of the main project
     * @param parser - The parser function to be applied to each row to turn it into type T
     * @return - A list of type T from each line of the file
     * @param <T> - The type to cast each line into
     */
    public static <T> List<T> parseFileToList(String filePath, Function<String, T> parser) {
        var splits = new ArrayList<T>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> splits.add(parser.apply(line)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return splits;
    }

    /***
     * Splits a file into a List of List of Characters, which is often used to make a map or grid of puzzle inputs
     * @param filePath - The path of the file, relative to the root of the main project
     * @return a List of List of Characters
     */
    public static List<List<Character>> parseFileToListListCharacter(String filePath) {
        return parseFileToList(filePath, line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    }
}
