package com.rooftop.challenge.Service;

import org.springframework.stereotype.Service;

@Service
public class SelectionSort
        implements SortService {

    final
    CompareService compareService;

    public SelectionSort(CompareService compareService) {
        this.compareService = compareService;
    }

    @Override
    public String[] check(String[] blocks, String token)  {

        for (int i = 1; i <= blocks.length -1 ; i++) {
            for (int j = i;j <blocks.length; j++){
                String from = blocks[i-1];
                String target = blocks[j];
                boolean continuous = compareService.compare(token, from, target );
                if (continuous){
                    String swap = blocks[j];
                    blocks[j] = blocks[i];
                    blocks[i] = swap;
                    break;
                }
            }
        }
        return blocks;
    }

    @Override
    public boolean verify(String[] blocks, String token)  {
        boolean res = compareService.compare(token, String.join("",blocks));
        return res;
    }
}
