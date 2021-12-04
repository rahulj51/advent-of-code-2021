package aoc.d1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fj.data.List.list;
import fj.data.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Input {

    public static List<Integer> read() throws IOException {
        List<String> result;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/java/aoc/d1/input.dat"))) {
            result = List.list(lines.collect(Collectors.toList()));
            return result.map(e -> Integer.valueOf(e));
        }
    }
}
