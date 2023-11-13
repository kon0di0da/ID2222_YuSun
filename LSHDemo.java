package LSH;

import java.util.*;

public class LSHDemo {

    private final List<List<Integer>> minHashSignatures;
    private final int bands;
    private final int rowsPerBand;

    public LSHDemo(List<List<Integer>> minHashSignatures, int bands) {
        this.minHashSignatures = minHashSignatures;
        this.bands = bands;
        this.rowsPerBand = minHashSignatures.get(0).size() / bands;
//        System.out.println(rowsPerBand);
    }

    public class Pair{
        Integer i1;
        Integer i2;

        public Pair() {
        }

        public Pair(Integer i1, Integer i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        /**
         * 获取
         * @return i1
         */
        public Integer getI1() {
            return i1;
        }

        /**
         * 设置
         * @param i1
         */
        public void setI1(Integer i1) {
            this.i1 = i1;
        }

        /**
         * 获取
         * @return i2
         */
        public Integer getI2() {
            return i2;
        }

        /**
         * 设置
         * @param i2
         */
        public void setI2(Integer i2) {
            this.i2 = i2;
        }

        public String toString() {
            return "Pair{i1 = " + i1 + ", i2 = " + i2 + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return (Objects.equals(i1, pair.i1) && Objects.equals(i2, pair.i2))||(Objects.equals(i2, pair.i1) && Objects.equals(i1, pair.i2));
        }

        @Override
        public int hashCode() {
            return Objects.hash(i1, i2);
        }
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

    public Set<Pair> findCandidatePairs (){
        Map<Long,List<Integer>> bucketMap = new LinkedHashMap<>();
        Set<Pair> res = new HashSet<>();
        for (int i = 0; i < bands; i++) {
            bucketMap.clear();
            for (int i1 =0; i1 < minHashSignatures.size(); i1++) {
                long bucketKey = calculateBucketKey(minHashSignatures.get(i1).subList(computeStartIndex(i),computeStartIndex(i)+rowsPerBand));
//                System.out.println(bucketKey);
                if(bucketMap.containsKey(bucketKey)){
                    List<Integer> iList = new ArrayList<>();
                    iList = bucketMap.get(bucketKey);
                    iList.add(i1+1);
                    bucketMap.put(bucketKey,iList);
                }
                else{
                    List<Integer> iList = new ArrayList<>();
                    iList.add(i1+1);
                    bucketMap.put(bucketKey,iList);
                }


            }
            for (Long l : bucketMap.keySet()) {
                List<Integer> integerList = new ArrayList<>();
                List<Integer> list = bucketMap.get(l);
                int size = list.size();

                if(size>=2){
                    for (int k =0;k < size; k++){
                        for( int j = k+1; j< size; j++){
                            res.add(new Pair(list.get(k),list.get(j)));
                        }
                    }

                }

            }
        }

        return res;
    }

    private int computeStartIndex(int band) {
        return (band * rowsPerBand );
    }

    private static long calculateBucketKey(List<Integer> array) {
        long hash = 0L;
        for (Integer element : array) {
            // 采用简单的相加操作，你可以根据需要设计更复杂的算法
            hash += element;
        }

        // 取余数以确保哈希码在合理范围内
        long bucketKey = hash % 100000;  // 这里取 10 只是为了演示，实际中可以根据桶的数量调整
        return bucketKey;
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

    private static int findSuitableBands(int n,  double s){//find suitable band number


        int min = (int) Math.pow(n,0.5);//set minvalue of b
        int b = n/2;
        int r = n/b;
        double singleprobability = Math.pow(s, r);
        double fp = Math.pow((1 - singleprobability), b);
        while(b>min && fp<=0.05){ // if we have a false positive rate under 5%, we are satisfied.
            r = n/b;
            singleprobability = Math.pow(s, r);
//            System.out.println(singleprobability);
            fp = Math.pow((1 - singleprobability), b);
//            System.out.println("fp = "+fp);
            b--;
        }
        return b;

    }

    public static void main(String[] args) {
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
//        double s = 0.6;
        MinHashing minHashing = new MinHashing();
        int n= 100;
        double s =0.6;// Adjust the similarity threshold

        List<List<Integer>> signatureMatrix = minHashing.buildMinHashSignature(sets, n);
        // Populate minHashSignatures with your actual minhash signatures

        int bands = findSuitableBands(n,s); // Adjust the number of bands
        System.out.println("band number = "+ bands + ". Threshold s = "+ s + ". Hash function numbers = " + n+".");
        LSHDemo lsh = new LSHDemo(signatureMatrix, bands);


        Set<Pair> candidatePairs = lsh.findCandidatePairs();

        System.out.println("Candidate Pairs:");
        for (Pair pair : candidatePairs) {
            System.out.println(pair.getI1()+".txt and " + pair.getI2() + ".txt.");
        }
    }
}

