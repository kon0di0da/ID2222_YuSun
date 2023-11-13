package LSH;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<Integer> shingles = new HashSet<>();
        Shingling shingling = new Shingling();
        shingles = shingling.getShingles("1.txt",4);

//        System.out.println(shingles.size());
        for (Integer shingle : shingles) {
            System.out.println(shingle);
        }

    }
}
