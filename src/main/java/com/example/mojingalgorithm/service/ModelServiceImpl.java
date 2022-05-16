package com.example.mojingalgorithm.service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mojingalgorithm.mapper.ModelMapper;
import com.example.mojingalgorithm.pojo.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl {
    @Autowired
    ModelMapper modelMapper;

    public Model findById(int id){
        return modelMapper.selectById(id);
    }

    public List<Model> findModels() {
        return modelMapper.selectList(new QueryWrapper<>());
    }
}
