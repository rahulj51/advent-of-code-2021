package aoc.d1;

import java.io.IOException;
import static fj.data.List.list;

import fj.data.List;

public class SonarSweep {



    public static void main(String[] args) throws IOException {

        List<Integer> readings = Input.read();
        System.out.println(readings);
        System.out.println(getIncrementingMeasures2(readings));




    }

    private static Integer getIncrementingMeasures1(List<Integer> readings) {
        Integer incrementingMeasures = readings
                .zipWith(readings.tail(), (i, j) -> List.list(i,j))
                .map(l ->  (l.head() < l.tail().head() ? 1 : 0))
                .foldLeft((s,e) -> s+e, 0);
        return incrementingMeasures;
    }

    private static Integer getIncrementingMeasures2(List<Integer> readings) {
        //first zip into sets of three
        List<Integer> threes = readings
                .zipWith(readings.tail(), (i,j)-> List.list(i,j))
                .zipWith(readings.tail().tail(), (i, j) -> i.snoc(j))
                .map(l -> sum(l));

        System.out.println(threes);

        return getIncrementingMeasures1(threes);


    }

    private static Integer sum(List<Integer> l) {
        return l.foldLeft((s,e) -> s+ e, 0);
    }


}
