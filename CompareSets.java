package LSH;

import java.util.HashSet;
import java.util.Set;

public class CompareSets {

    public double computeJaccardSimilarity(Set<Integer> set1, Set<Integer> set2) {
        // Compute Jaccard similarity of two sets of hashed shingles
        int intersectionSize = computeIntersectionSize(set1, set2);
        int unionSize = computeUnionSize(set1, set2);

        // Jaccard Similarity = (Intersection Size) / (Union Size)
        return (double) intersectionSize / unionSize;
    }

    private int computeIntersectionSize(Set<Integer> set1, Set<Integer> set2) {
        // Compute the size of the intersection between two sets
        int intersectionSize = 0;

        for (Integer element : set1) {
            if (set2.contains(element)) {
                intersectionSize++;
            }
        }

        return intersectionSize;
    }

    private int computeUnionSize(Set<Integer> set1, Set<Integer> set2) {
        // Compute the size of the union between two sets
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);

        return union.size();
    }

//    public static void main(String[] args) {
//        // Example usage
//        CompareSets compareSets = new CompareSets();
//
//        // Create two sets of hashed shingles
//        Set<Integer> set1 = Set.of(1, 2, 3, 4, 5);
//        Set<Integer> set2 = Set.of(3, 4, 5, 6, 7);
//
//        // Compute and print Jaccard similarity
//        double jaccardSimilarity = compareSets.computeJaccardSimilarity(set1, set2);
//        System.out.println("Jaccard Similarity: " + jaccardSimilarity);
//    }
}

