package com.example.mojingalgorithm.service;

import com.example.mojingalgorithm.mapper.LabelCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RuiTan
 * @date 2022/6/23 13:00
 */
@Service
public class LabelCountService {
    @Autowired
    LabelCountMapper labelCountMapper;

    public void updateLabels(){}
}
