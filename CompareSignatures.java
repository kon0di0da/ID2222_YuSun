package LSH;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompareSignatures {
    public double estimateSimilarity(List<Integer> l1,List<Integer> l2){
        double same = 0.0;
        l1.sort((i1,i2)->{return i1-i2;});
        System.out.println(l1);
        l2.sort((i1,i2)->{return i1-i2;});
        System.out.println(l2);

        if(l1.size()!=l2.size()){
//            System.out.println("haha");
            throw new IllegalArgumentException("Signatures must have the same length for comparison.");

        }
        else{
            for (int i = 0; i < l1.size(); i++) {
//                System.out.println(l1.get(i) );
//                System.out.println(l2.get(i) );
                if(l1.get(i).equals(l2.get(i))){
                    same++;
                }
            }
        }
        return (double) (same/l1.size());
    }
    public static void main(String[] args) {
        // Example usage
        CompareSignatures compareSignatures = new CompareSignatures();

        // Example minHash signatures (integer vectors)
        List<Set<Integer>> sets = new ArrayList<>();
        sets.add(Set.of(1, 2, 3, 4, 5,6,100,19,23,24));
        sets.add(Set.of(1,2,3,4,5,6,7,8,9,10,11,12));
        MinHashing minHashing = new MinHashing();
        List<List<Integer>> signatureMatrix = minHashing.buildMinHashSignature(sets, 3);
        // Estimate and print the similarity
        double similarity = compareSignatures.estimateSimilarity(signatureMatrix.get(0),signatureMatrix.get(1));
        System.out.println("MinHash Signature Similarity: " + similarity);
    }
}
