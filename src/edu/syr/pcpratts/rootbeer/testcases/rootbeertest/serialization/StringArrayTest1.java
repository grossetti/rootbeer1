/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package edu.syr.pcpratts.rootbeer.testcases.rootbeertest.serialization;

import edu.syr.pcpratts.rootbeer.runtime.Kernel;
import edu.syr.pcpratts.rootbeer.test.TestSerialization;
/* 
 * Copyright 2012 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 * 
 * See the file LICENSE for copying permission.
 */

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class StringArrayTest1 implements TestSerialization {
  
  public List<Kernel> create() {
    List<String[]> arrays = new ArrayList<String[]>();
    for(int i = 0; i < 50; ++i) {
      String[] array = new String[512];
      for(int j = 0; j < array.length; ++j) {
        array[j] = "new";
      }	    	
		  arrays.add(array);
    }
    
    List<Kernel> jobs = new ArrayList<Kernel>();
    String [] ret = new String[arrays.size()];
    for(int i = 0; i < arrays.size(); ++i)
    {
      jobs.add(new StringArrayTest1RunOnGpu(arrays.get(i), ret, i));
    }
    return jobs;
  }

  public boolean compare(Kernel original, Kernel from_heap){
    StringArrayTest1RunOnGpu typed_original = (StringArrayTest1RunOnGpu) original;
    StringArrayTest1RunOnGpu typed_from_heap = (StringArrayTest1RunOnGpu) from_heap;
    String[] lhs = typed_original.getResult();
    String[] rhs = typed_from_heap.getResult();
    if(lhs.length != rhs.length){
      return false;
    }
    for(int i = 0; i < lhs.length; ++i) {
      if(lhs[i].equals(rhs[i]) == false){
      	return false;
      }
    }
    return true;
  }
}