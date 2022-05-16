package com.example.mojingalgorithm.service;

import com.example.mojingalgorithm.mapper.DatasetMapper;
import com.example.mojingalgorithm.pojo.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetServiceImpl {
    @Autowired
    DatasetMapper datasetMapper;

    public Dataset findById(int id){
        return datasetMapper.selectById(id);
    }

    public Dataset insertDataset(Dataset dataset) {
        datasetMapper.insert(dataset);
        return dataset;
    }
}
