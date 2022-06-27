package com.rooftop.challenge.Service;

import com.rooftop.challenge.DTO.CheckSortDTO;
import com.rooftop.challenge.DTO.MasterBlockDTO;
import com.rooftop.challenge.DTO.SortMesageDTO;
import com.rooftop.challenge.Helpers.CircuitBreakerHelper;
import com.rooftop.challenge.Helpers.HttpHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Compare
        implements CompareService {
    final static HttpHelper httpHelper = new HttpHelper();

    final CircuitBreakerRegistryProviderService cbService;

    String checkUrl;
    public Compare(CircuitBreakerRegistryProviderService cbService, @Value("${rooftop_checkUrl}") String checkUrl) {
        this.cbService = cbService;
        this.checkUrl = checkUrl;
    }

    @Override
    public boolean compare(String token, String blockFrom, String blockTarget)
             {

        CheckSortDTO sortDto = new CheckSortDTO();
        sortDto.setBlocks(new String[]{
                blockFrom,
                blockTarget
        });

        String sortUrl = String.format(checkUrl, token);
        SortMesageDTO result = CircuitBreakerHelper.apply("checkBlockOrder",
                () -> httpHelper.post(sortUrl, SortMesageDTO.class, sortDto),
                cbService);

        boolean returnValue =  result.getMessage();
        return returnValue;
    }

    @Override
    public boolean compare(String token, String masterBlock) {
        String sortUrl = String.format(checkUrl, token);
        MasterBlockDTO dto = new MasterBlockDTO();
        dto.setEncoded(masterBlock);
        SortMesageDTO result = httpHelper.post(sortUrl, SortMesageDTO.class, dto);
        boolean returnValue =  result.getMessage();
        return returnValue;
    }
}
