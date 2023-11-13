package LSH;

import java.util.List;

public class LSH {
    private final List<List<Integer>> signatureMatrix;
    private final int b;
    private final int r;
    public LSH(List<List<Integer>> sm,int b){
        signatureMatrix = sm;
        this.b = b;
        this.r = sm.get(0).size()/b;
    }



}
