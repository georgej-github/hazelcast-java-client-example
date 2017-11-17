package com.darylmathison.hazelcastjavaclient;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import java.util.Map;

import java.lang.System;

/**
 *
 * @author Daryl
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientConfig config = new XmlClientConfigBuilder(System.getProperty("hazelcast.xmlconfig")).build();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        //HazelcastInstance instance = HazelcastClient.newHazelcastClient();
        Map<Long, Long> cacheMap = instance.getMap("fibmap");
        
        for(long i = 1; i <= 10L; i++) {
            System.out.println("value is " + fibonacci(i, cacheMap));
        }
        instance.shutdown();
    }
    
    private static long fibonacci(long rounds, Map<Long, Long> cacheMap) {
        Long cached = cacheMap.get(rounds);
        if(cached != null) {
            System.out.print("cached ");
            return cached;
        }
        
        long[] lastTwo = new long[] {1, 1};
        
        for(int i = 0; i < rounds; i++) {
            long last = lastTwo[1];
            lastTwo[1] = lastTwo[0] + lastTwo[1];
            lastTwo[0] = last;
        }
        
        cacheMap.put(rounds, lastTwo[1]);
        return lastTwo[1];
     }

}
