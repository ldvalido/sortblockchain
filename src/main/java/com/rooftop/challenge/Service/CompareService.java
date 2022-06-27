package com.rooftop.challenge.Service;


public interface CompareService {
    boolean compare(String token, String blockFrom, String blockTarget);
    boolean compare(String token, String masterBlock);
}
