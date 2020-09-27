import java.util.*;

/**
 * CLass used to analyze the performance of different hashsets
 * @author guyna25
 */

public class SimpleSetPerformanceAnalyzer {
    private String[] firstTestData = Ex4Utils.file2array("src/data1.txt");
    private String[] secondTestData = Ex4Utils.file2array("src/data2.txt");
    private String[] FIRST_TEST_STRINGS = {"hi", "-13170890158"};
    private String[] SECOND_TEST_STRINGS = {"23", "hi"};
    private Map<SimpleSet, String> setMap = new HashMap<>();


    private SimpleSetPerformanceAnalyzer() {
        setMap.put(new OpenHashSet(), "OpenHashSet");
        setMap.put(new ClosedHashSet(), "ClosedHashSet");
        setMap.put(new CollectionFacadeSet(new TreeSet<>()), "TreeSet");
        setMap.put(new CollectionFacadeSet(new LinkedHashSet<>()), "LinkedHashSet");
        setMap.put(new CollectionFacadeSet(new HashSet<>()), "HashSet");

    }

    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer simpleAnalyzer = new SimpleSetPerformanceAnalyzer();
        simpleAnalyzer.runAnalysis(simpleAnalyzer.firstTestData,
                "data1", simpleAnalyzer.FIRST_TEST_STRINGS );
        simpleAnalyzer = new SimpleSetPerformanceAnalyzer();
        simpleAnalyzer.runAnalysis(simpleAnalyzer.secondTestData, "data2",
                simpleAnalyzer.SECOND_TEST_STRINGS );
    }

    private void warmUp(SimpleSet warmUpSet, String warmUpString) {
        int warmupLength = 70000;
        for (int i = 0;i<warmupLength;i++) {
            warmUpSet.contains(warmUpString);
        }
    }

    private long testAdd(SimpleSet testedSet, String[] data) {
        long beginning = System.nanoTime();
        for (String string: data) {
            testedSet.add(string);
        }
        return (System.nanoTime() - beginning) / 1000000;
    }

    private long testContains(SimpleSet testedSet, String testString) {
        if (!setMap.get(testedSet).equals("LinkedHashSet")) {
            warmUp(testedSet, testString);
        }
        long iterationsSum = 0;
        for (int i = 0;i<70000;i++) {
            long beginning = System.nanoTime();
            testedSet.contains(testString);
            iterationsSum += System.nanoTime() - beginning;
        }
        return iterationsSum/70000;
    }

    private void runAnalysis(String[] data, String dataName, String[] testStrings) {
        for (SimpleSet set: setMap.keySet()) {
            System.out.println(setMap.get(set) + "'s add " + dataName + " time " + testAdd(set,
                    data)+ " ms");
        }

        System.out.println();

        for (SimpleSet set: setMap.keySet()) {
            for (String string: testStrings) {
                System.out.println(setMap.get(set) + "'s contain (" + string + ") " +dataName +  " time " +
                                String.valueOf(testContains(set, string)) + " ns");
            }
        }

        System.out.println();
    }
}
