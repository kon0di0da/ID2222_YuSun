package LSH;

import java.util.List;

public class CompareSignatures {
    public double estimateSimilarity(List<Integer> l1,List<Integer> l2){
        int same = 0;
        if(l1.size()!=l2.size()){
            throw new IllegalArgumentException("Signatures must have the same length for comparison.");
        }
        else{
            for (int i = 0; i < l1.size(); i++) {
                if(l1.get(i)== l2.get(i)){
                    same++;
                }
            }
        }
        return (double) (same/l1.size());
    }
//    public static void main(String[] args) {
//        // Example usage
//        CompareSignatures compareSignatures = new CompareSignatures();
//
//        // Example minHash signatures (integer vectors)
//        List<Integer> signature1 = List.of(1, 2, 3, 4, 5);
//        List<Integer> signature2 = List.of(3, 4, 5, 6, 7);
//
//        // Estimate and print the similarity
//        double similarity = compareSignatures.estimateSimilarity(signature1, signature2);
//        System.out.println("MinHash Signature Similarity: " + similarity);
//    }
}
