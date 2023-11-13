package LSH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompareSignatures {
    public double estimateSimilarity(List<Integer> l1,List<Integer> l2){
        double same = 0.0;
        l1.sort((i1,i2)->{return i1-i2;});
//        System.out.println(l1);
        l2.sort((i1,i2)->{return i1-i2;});
//        System.out.println(l2);

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
        Set<Integer> shingles1 = new HashSet<>();
        Set<Integer> shingles2 = new HashSet<>();
        Set<Integer> shingles3 = new HashSet<>();
        Set<Integer> shingles4 = new HashSet<>();
        Set<Integer> shingles5 = new HashSet<>();
        Set<Integer> shingles6 = new HashSet<>();

        Shingling shingling = new Shingling();
        int shingleSize = 4;// set Shingle Size
        shingles1 = shingling.getShingles("1.txt",shingleSize);
        shingles2 = shingling.getShingles("2.txt",shingleSize);
        shingles3 = shingling.getShingles("3.txt",shingleSize);
        shingles4 = shingling.getShingles("4.txt",shingleSize);
        shingles5 = shingling.getShingles("5.txt",shingleSize);
        shingles6 = shingling.getShingles("6.txt",shingleSize);


        sets.add(shingles1);
        sets.add(shingles2);
        sets.add(shingles3);
        sets.add(shingles4);
        sets.add(shingles5);
        sets.add(shingles6);
        //Threshold s = 0.6
        double s = 0.5;
        MinHashing minHashing = new MinHashing();
        List<List<Integer>> signatureMatrix = minHashing.buildMinHashSignature(sets, 6);
        // Estimate and print the similarity
        for (int i = 0; i < signatureMatrix.size(); i++) {
            for (int i1 = i+1; i1 < signatureMatrix.size(); i1++) {
                double similarity = compareSignatures.estimateSimilarity(signatureMatrix.get(i),signatureMatrix.get(i1));
//                System.out.println("MinHash Signature Similarity of " +(i+1) +".txt and " + (i1+1) + ".txt: " + similarity);
                if(similarity >= s){
                    System.out.println(i+1 +".txt and " + (i1+1) + ".txt is similar above threshold " +s+ "with a similarity of: " + similarity);
                }

            }
        }


    }
}
