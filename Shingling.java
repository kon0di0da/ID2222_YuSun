package LSH;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Class representing a document shingling
public class Shingling {
    public Set<Integer> getShingles(String document, int k) {
        // Implement shingling logic here
        // Return a set of hashed k-shingles
        // Use a HashSet to ensure uniqueness
        Set<String> shingles = new HashSet<>();
        Set<Integer> intSet = new HashSet <>();
        String currentDirectory = "C:\\Users\\tianj\\Desktop\\Java_study\\untitled\\src\\LSH";
//        String currentDirectory = ".";
        File file = new File(currentDirectory,document);

        if(file.exists()){
//            System.out.println("hahaha");
            String content = readContentFromFile(file);
//            System.out.println(content.length());
            shingles = generateShingles(content, k);
            for (String shingle : shingles) {
                int hash = shingle.hashCode();
                intSet.add(hash);
            }
        }


        return intSet;
    }

    private String readContentFromFile(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private Set<String> generateShingles(String str, int k){
        Set<String> result = new HashSet<>();
        for (int i = 0; i < str.length()-k; i++) {
            String res = str.substring(i,i+k);
            result.add(res);
        }
        return result;
    }
//    public Set<Integer> getUnion(List<Set<Integer>> setList){
//        Set<Integer> union = new HashSet<>();
//        for (Set<Integer> integers : setList) {
//            union.addAll(integers);
//        }
//        return union;
//    }

//    public Integer[][] getShingledMatrix(List<Set<Integer>> setList){
//        Set<Integer> union = getUnion(setList);
//        Integer[][] result = new Integer[union.size()][setList.size()];
//        int count = 0;
//        for (Integer i : union) {
//
//            for (int i1 = 0; i1 < setList.size(); i1++) {
//                if(setList.get(i1).contains(i)){
//                    result[count][i1] = 1;
//                }
//                else{
//                    result[count][i1] = 0;
//                }
//            }
//            count++;
//        }
//        System.out.println(count);
//        for (int i = 0; i < result.length; i++) {
//            for (int i1 = 0; i1 < result[0].length; i1++) {
//                System.out.print(result[i][i1]);
//                System.out.print(" ");
//            }
//            System.out.println(" ");
//        }
//        return result;
//    }




}
