package com.collectionFramework;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Hashtable<String, Object> ht = new Hashtable<String, Object>();
        ht.put("1", 1);
        ht.put("2", "2");
        ht.put("3", 0.3);
        
        System.out.println(ht);
        
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("a", "a");
        hm.put("d", "d");
        hm.put("b","b");
        hm.put("c", "c");
        hm.put("e", "e");
        
        System.out.println(hm);
    }
}
