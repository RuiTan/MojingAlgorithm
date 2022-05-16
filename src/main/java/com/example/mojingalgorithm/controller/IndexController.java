package com.example.mojingalgorithm.controller;

import com.example.mojingalgorithm.pojo.Dataset;
import com.example.mojingalgorithm.pojo.Job;
import com.example.mojingalgorithm.pojo.Model;
import com.example.mojingalgorithm.service.DatasetServiceImpl;
import com.example.mojingalgorithm.service.JobServiceImpl;
import com.example.mojingalgorithm.service.ModelServiceImpl;
import com.example.mojingalgorithm.util.ClothesDistribution;
import com.example.mojingalgorithm.util.ResponseData;
import com.example.mojingalgorithm.util.ResponseDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Administrator
 */
@RestController
public class IndexController {
    @Autowired
    ModelServiceImpl modelService;
    @Autowired
    JobServiceImpl jobService;
    @Autowired
    DatasetServiceImpl datasetService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/model")
    public ResponseData model(@RequestParam int id){
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(modelService.findById(id));
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/models")
    public ResponseData models(){
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(modelService.findModels());
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/jobs")
    public ResponseData jobs() {
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.findJobs());
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/allJobs")
    public ResponseData allJobs() {
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.findAllJobs());
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/job")
    public ResponseData job(@RequestParam int modelId) {
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.findByModelId(modelId));
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }


    @PostMapping("/run")
    public ResponseData run(@RequestParam int modelId) {
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.insertJobByModelId(modelId));
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/labelCount")
    public ResponseData labelCount(@RequestParam int tag) {
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.getLabelCount(tag));
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @GetMapping("/metrics")
    public ResponseData metrics(@RequestParam int modelId){
        ResponseData responseData = null;
        try {
            responseData = ResponseDataFactory.generate(jobService.findMetricsByModel(modelId));
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }

    @PostMapping("/uploadDataset")
    public ResponseData uploadDataset(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        ResponseData responseData = null;
        try {
            List<Dataset> datasets = new LinkedList<>();
            for (MultipartFile file : files) {
                System.out.println(file.getOriginalFilename());
                int count = new Random().nextInt(50) + 20;
                Dataset dataset = datasetService.insertDataset(Dataset.builder()
                        .datasetName(file.getOriginalFilename())
                        .uploadTime(new Date())
                        .modelId(2)
                        .categories(Arrays.toString(ClothesDistribution.randomCategoryDistribution(count)))
                        .labels(Arrays.toString(ClothesDistribution.randomLabelDistribution(count)))
                        .build());
                datasets.add(dataset);
            }
            List<String[]> names = new LinkedList<>();
            names.add(ClothesDistribution.CLOTH_CATEGORIES);
            names.add(ClothesDistribution.CLOTH_LABELS);
            List<Object> result = new LinkedList<>();
            result.add(datasets);
            result.add(names);
            responseData = ResponseDataFactory.generate(result);
        } catch (Exception e) {
            responseData = ResponseDataFactory.generate(e);
        }
        return responseData;
    }


}
