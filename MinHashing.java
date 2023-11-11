package LSH;

import java.util.*;

public class MinHashing {
    private static class HashFunction {
        private final int a;
        private final int b;

        public HashFunction(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int evaluate(int x) {
            return (int) (((long) a * x + b) & 0xFFFFFFFFL); // Use bitwise AND to get an unsigned int
        }
    }
    public List<List<Integer>> buildMinHashSignature(List<Set<Integer>> hashedShinglesGroup,int n) {
        // Ensure that n is not greater than the size of the hashedShingles set
        n = Math.min(n, hashedShinglesGroup.get(0).size());

        // Generate random hash functions (represented as simple linear functions)
        List<HashFunction> hashFunctions = generateHashFunctions(n);

        // Initialize minHash signature with positive infinity for each hash function

        List<List<Integer>> res =new ArrayList<>();
        // Iterate through each hashed shingle
        for (Set<Integer> hashedShingles : hashedShinglesGroup) {
            Integer[] minHashSignature = new Integer[n];
            Arrays.fill(minHashSignature, Integer.MAX_VALUE);
            for (Integer hashedShingle : hashedShingles) {
                // Evaluate each hash function and update the minHash signature
                for (int i = 0; i < n; i++) {
                    minHashSignature[i] = Math.min(minHashSignature[i], hashFunctions.get(i).evaluate(hashedShingle));
                }
            }
            res.add(Arrays.asList(minHashSignature));
        }


        // Convert the minHash signature array to a set
        return res;
    }

    private List<HashFunction> generateHashFunctions(int n) {
        // Generate random hash functions (simple linear functions)
        List<HashFunction> hashFunctions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int a = random.nextInt(Integer.MAX_VALUE);
            int b = random.nextInt(Integer.MAX_VALUE);
            hashFunctions.add(new HashFunction(a, b));
        }

        return hashFunctions;
    }

    public static void main(String[] args) {
        // Example usage
        MinHashing minHashing = new MinHashing();

        // Example list of integer sets (hashed shingles)
        List<Set<Integer>> sets = new ArrayList<>();
        sets.add(Set.of(1, 2, 3, 4, 5));
        sets.add(Set.of(1,2,3,4,5));
//        sets.add(Set.of(2, 3, 5, 8, 9));

        // Specify the length of the minHash signatures
        int n = 3;

        // Generate and print the minHash signature matrix
        List<List<Integer>> signatureMatrix = minHashing.buildMinHashSignature(sets, n);
        System.out.println("MinHash Signature Matrix:");
        for (List<Integer> row : signatureMatrix) {
            System.out.println(row);
        }
    }
}

