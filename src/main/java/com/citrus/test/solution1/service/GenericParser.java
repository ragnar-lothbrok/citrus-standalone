package com.citrus.test.solution1.service;

import java.io.Serializable;
import java.util.Set;

import com.citrus.test.solution1.bean.Record;

public interface GenericParser extends Serializable{
    
    Set<Record> parse(String filePath);
    
}
