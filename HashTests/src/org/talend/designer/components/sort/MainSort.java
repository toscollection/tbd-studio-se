package org.talend.designer.components.sort;

import java.util.Arrays;
import java.util.Random;

import org.talend.designer.components.sort.fastquicksort.FastQSortAlgorithm;
import org.talend.designer.components.thash.Sizeof;

public class MainSort {

    /**
     * DOC amaumont Comment method "main".
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        int[] dataFQS = generateData();
        int[] dataSun = dataFQS.clone();
        
//        long heap1 = Sizeof.usedMemory();
        long start = System.currentTimeMillis();
        
        FastQSortAlgorithm fastQSortAlgorithm = new FastQSortAlgorithm();
        fastQSortAlgorithm.sort(dataFQS);

        long end = System.currentTimeMillis();
        long delta = end - start; 
        
        System.out.println("Time FastQSortAlgorithm:" + delta + " ms");
        
        start = System.currentTimeMillis();
        
        Arrays.sort(dataFQS);
        
        end = System.currentTimeMillis();
        delta = end - start; 
        
        System.out.println("Sun Algorithm:" + delta + " ms");
        
//        long heap2 = Sizeof.usedMemory();


    }

    private static int[] generateData() {
        int[] randomArray = null;

        int nbItems = 5000000;
        
        randomArray = new int[nbItems];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = i;
        }
        int j = 0;
        Random rand = new Random(System.currentTimeMillis());
        // shiffle unique values
        for (int i = 0; i < randomArray.length; i++) {
            j = rand.nextInt(nbItems);
            int vj = randomArray[j];
            randomArray[j] = randomArray[i];
            randomArray[i] = vj;
        }
        return randomArray;
    }

}
