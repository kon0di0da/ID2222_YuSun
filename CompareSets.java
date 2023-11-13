package LSH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Set<Integer> getUnion(List<Set<Integer>> setList){
        Set<Integer> union = new HashSet<>();
        for (Set<Integer> integers : setList) {
            union.addAll(integers);
        }
        return union;
    }

    public Integer[][] getBooleanMatrix(List<Set<Integer>> setList){
        Set<Integer> union = getUnion(setList);
        Integer[][] result = new Integer[union.size()][setList.size()];
        int count = 0;
        for (Integer i : union) {

            for (int i1 = 0; i1 < setList.size(); i1++) {
                if(setList.get(i1).contains(i)){
                    result[count][i1] = 1;
                }
                else{
                    result[count][i1] = 0;
                }
            }
            count++;
        }
//        System.out.println(count);
//        for (int i = 0; i < result.length; i++) {
//            for (int i1 = 0; i1 < result[0].length; i1++) {
//                System.out.print(result[i][i1]);
//                System.out.print(" ");
//            }
//            System.out.println(" ");
//        }
        return result;
    }

    public static void main(String[] args) {
        // Example usage
        CompareSets compareSets = new CompareSets();

        // Create two sets of hashed shingles
        Set<Integer> shingles1 = new HashSet<>();
        Set<Integer> shingles2 = new HashSet<>();
        Shingling shingling = new Shingling();
        shingles1 = shingling.getShingles("1.txt",4);
        shingles2 = shingling.getShingles("2.txt",4);


//        List<Set<Integer>> setList = new ArrayList<>();
//        setList.add(shingles1);
//        setList.add(shingles2);
//        Integer[][] res = compareSets.getBooleanMatrix(setList);

//        for (int i = 0; i < res.length; i++) {
//            for (int i1 = 0; i1 < res[0].length; i1++) {
//                System.out.print(res[i][i1]);
//                System.out.print(" ");
//            }
//            System.out.println(" ");
//        }

        // Compute and print Jaccard similarity
        double jaccardSimilarity = compareSets.computeJaccardSimilarity(shingles1, shingles2);
        System.out.println("Jaccard Similarity: " + jaccardSimilarity);
    }
}

