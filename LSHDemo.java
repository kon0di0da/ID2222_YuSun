package LSH;

import java.util.*;

public class LSHDemo {

    private final List<List<Integer>> minHashSignatures;
    private final int bands;
    private final int rowsPerBand;

    public LSHDemo(List<List<Integer>> minHashSignatures, int bands) {
        this.minHashSignatures = minHashSignatures;
        this.bands = bands;
        this.rowsPerBand = minHashSignatures.get(1).size() / bands;
    }

//    public List<List<Integer>> findCandidates(double threshold) {
//        List<List<Integer>> candidates = new ArrayList<>();
//
//        for (int band = 0; band < bands; band++) {
//            Map<Integer, List<Integer>> bucketMap = new HashMap<>();
//
//            for (int row = 0; row < minHashSignatures.get(0).size(); row++) {
//                int hash = computeHash(band, row);
//                List<Integer> bucket = bucketMap.computeIfAbsent(hash, k -> new ArrayList<>());
//                bucket.add(row);
//            }
//
//            for (List<Integer> bucket : bucketMap.values()) {
//                if (bucket.size() > 1) {
//                    for (int i = 0; i < bucket.size() - 1; i++) {
//                        for (int j = i + 1; j < bucket.size(); j++) {
//                            List<Integer> signature1 = minHashSignatures.get(bucket.get(i));
//                            List<Integer> signature2 = minHashSignatures.get(bucket.get(j));
//
//                            double similarity = computeSimilarity(signature1, signature2);
//                            if (similarity >= threshold) {
//                                // Add candidate pair to the result
//                                List<Integer> candidatePair = new ArrayList<>();
//                                candidatePair.add(bucket.get(i));
//                                candidatePair.add(bucket.get(j));
//                                candidates.add(candidatePair);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return candidates;
//    }

    public List<Set<Integer>> findCandidatePairs (){
        Map<List<Integer>,Integer> bucketMap = new LinkedHashMap<>();
        for (int i = 0; i < bands; i++) {
            bucketMap.clear();
            for (int i1 =0; i1 < minHashSignatures.size(); i1++) {
                bucketMap.put(minHashSignatures.get(i1).subList(computeStartIndex(i),computeStartIndex(i)+rowsPerBand),i1);

            }
        }
        List<Set<Integer>> res = new ArrayList<>();
        return res;
    }

    private int computeStartIndex(int band) {
        return (band * rowsPerBand );
    }

    private static List<Long> calculateBucketKey(List<Integer> array) {
        long hash = 0L;
        for (Integer element : array) {
            // 采用简单的相加操作，你可以根据需要设计更复杂的算法
            hash += element;
        }

        // 取余数以确保哈希码在合理范围内
        long bucketKey = hash % 100000;  // 这里取 10 只是为了演示，实际中可以根据桶的数量调整
        return Arrays.asList(bucketKey);
    }



    private double computeSimilarity(List<Integer> signature1, List<Integer> signature2) {
        int agreeingComponents = 0;

        for (int i = 0; i < signature1.size(); i++) {
            if (signature1.get(i).equals(signature2.get(i))) {
                agreeingComponents++;
            }
        }

        return (double) agreeingComponents / signature1.size();
    }

//    public static void main(String[] args) {
//        // Example usage
//
//        //        // Example list of integer sets (hashed shingles)
//        List<Set<Integer>> sets = new ArrayList<>();
//        sets.add(Set.of(1, 2, 3, 4, 5));
//        sets.add(Set.of(3, 4, 5, 6, 7));
//        sets.add(Set.of(2, 3, 5, 8, 9));
//
//        // Specify the length of the minHash signatures
//        int n = 3;
//
//        // Generate and print the minHash signature matrix
//        List<List<Integer>> signatureMatrix = ((new MinHashing())).generateSignatureMatrix(sets, n);
//        List<List<Integer>> minHashSignatures = signatureMatrix;
//        // Populate minHashSignatures with your actual minhash signatures
//
//        int bands = 5; // Adjust the number of bands
//        LSHDemo lsh = new LSHDemo(minHashSignatures, bands);
//
//        double threshold = 0.1; // Adjust the similarity threshold
//        List<List<Integer>> candidatePairs = lsh.findCandidates(threshold);
//
//        System.out.println("Candidate Pairs:");
//        for (List<Integer> pair : candidatePairs) {
//            System.out.println(pair);
//        }
//    }
}

