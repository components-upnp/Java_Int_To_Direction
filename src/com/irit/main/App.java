package com.irit.main;

import com.irit.upnp.IntToDirectionAdapterServer;

/**
 * Hello world!
 *
 */
public class App 
{
   public static void main(String[] args) {
       new Thread(new IntToDirectionAdapterServer()).run();
   }
}
