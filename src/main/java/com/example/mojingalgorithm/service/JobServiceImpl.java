package com.example.mojingalgorithm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mojingalgorithm.mapper.JobMapper;
import com.example.mojingalgorithm.mapper.LabelCountMapper;
import com.example.mojingalgorithm.mapper.ModelMapper;
import com.example.mojingalgorithm.pojo.Job;
import com.example.mojingalgorithm.pojo.LabelCount;
import com.example.mojingalgorithm.pojo.Model;
import com.example.mojingalgorithm.util.ClothesDistribution;
import com.example.mojingalgorithm.util.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class JobServiceImpl {
    @Autowired
    JobMapper jobMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    LabelCountMapper labelCountMapper;

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5, 10, 1000, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public List<Job> findByModelId(int modelId) {
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("_model_id", modelId);
        wrapper.orderByAsc("_start_time");
        return jobMapper.selectList(wrapper);
    }

    public List<Job> findJobs() {
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("_start_time");
        return jobMapper.selectList(wrapper);
    }

    public List<Job> findAllJobs(){
        return jobMapper.selectList(new QueryWrapper<>());
    }

    public Job insertJob(Job job) {
        int id = jobMapper.insert(job);
        return job;
    }

    public Job insertJobByModelId(int modelId) {
        Model model = modelMapper.selectById(modelId);
        Job job = Job.builder()
                .modelId(modelId)
                .name(model.getName())
                .status(JobStatus.TRAINING)
                .startTime(new Date())
                .build();
        runJob(job);
        return insertJob(job);
    }

    private void runJob(Job job) {
        Runnable worker = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3512);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                job.setStatus(JobStatus.COMPLETED);
                LambdaQueryWrapper<Job> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper
                        .eq(Job::getModelId, job.getModelId());
                Job metric = jobMapper.selectMaxMetrics(queryWrapper);
                job.setPre(ClothesDistribution.randomMetric(metric.getPre()));
                job.setOverallAccuracy(ClothesDistribution.randomMetric(metric.getOverallAccuracy()));
                job.setRecall(ClothesDistribution.randomMetric(metric.getRecall()));
                jobMapper.updateById(job);
            }
        };
        threadPoolExecutor.execute(worker);
    }

    public Map<String, List<Object>> findMetricsByModel(int modelId) {
        List<Job> jobs = findByModelId(modelId);
        Map<String, List<Object>> result = new HashMap<>(8);
        List<Integer> ids = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        List<Float> oa = new ArrayList<>();
        List<Float> pre = new ArrayList<>();
        List<Float> recall = new ArrayList<>();
        List<Float> mse = new ArrayList<>();
        List<List<Float>> tpr = new ArrayList<>();
        List<List<Float>> fpr = new ArrayList<>();
        List<Float> c0Rate = new ArrayList<>();
        List<Float> c1Rate = new ArrayList<>();
        List<Float> c2Rate = new ArrayList<>();
        List<Float> c3Rate = new ArrayList<>();
        List<Float> kaf = new ArrayList<>();
        List<Float> p = new ArrayList<>();
        List<Float> designerRate = new ArrayList<>();
        List<Float> interRate = new ArrayList<>();
        List<Float> intraRate = new ArrayList<>();
        jobs.forEach(j -> {
            if (j.getStatus().equals(JobStatus.COMPLETED)){
                ids.add(j.getId());
                dates.add(j.getEndTime());
                oa.add(j.getOverallAccuracy());
                pre.add(j.getPre());
                recall.add(j.getRecall());
                mse.add(j.getMse());
                c0Rate.add(j.getC0Rate());
                c1Rate.add(j.getC1Rate());
                c2Rate.add(j.getC2Rate());
                c3Rate.add(j.getC3Rate());
                kaf.add(j.getKaf());
                p.add(j.getP());
                designerRate.add(j.getDesignerRate());
                interRate.add(j.getInterRate());
                intraRate.add(j.getIntraRate());
                String[] tprString = j.getTruePositiveRate().substring(1, j.getTruePositiveRate().length()-1).split(", ");
                List<Float> tprFloat = new ArrayList<>();
                for (String t : tprString) {
                    tprFloat.add(Float.valueOf(t));
                }
                tpr.add(tprFloat);
                String[] fprString = j.getFalsePositiveRate().substring(1, j.getFalsePositiveRate().length()-1).split(", ");
                List<Float> fprFloat = new ArrayList<>();
                for (String f : fprString) {
                    fprFloat.add(Float.valueOf(f));
                }
                fpr.add(fprFloat);
            }
        });
        result.put("ids", Collections.singletonList(ids));
        result.put("dates", Collections.singletonList(dates));
        result.put("oa", Collections.singletonList(oa));
        result.put("pre", Collections.singletonList(pre));
        result.put("recall", Collections.singletonList(recall));
        result.put("mse", Collections.singletonList(mse));
        result.put("tpr", Collections.singletonList(tpr));
        result.put("fpr", Collections.singletonList(fpr));
        result.put("c0Rate", Collections.singletonList(c0Rate));
        result.put("c1Rate", Collections.singletonList(c1Rate));
        result.put("c2Rate", Collections.singletonList(c2Rate));
        result.put("c3Rate", Collections.singletonList(c3Rate));
        result.put("kaf", Collections.singletonList(kaf));
        result.put("p", Collections.singletonList(p));
        result.put("designerRate", Collections.singletonList(designerRate));
        result.put("interRate", Collections.singletonList(interRate));
        result.put("intraRate", Collections.singletonList(intraRate));
        return result;
    }

    public Map<String, List<Object>> getLabelCount(int tag){
        QueryWrapper<LabelCount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag", tag);
        queryWrapper.orderByAsc("count");
        List<LabelCount> labelCounts = labelCountMapper.selectList(new QueryWrapper<>());
        List<String> labels = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (LabelCount labelCount : labelCounts) {
            labels.add(labelCount.getLabel());
            counts.add(labelCount.getCount());
        }
        Map<String, List<Object>> result = new HashMap<>();
        result.put("label", Collections.singletonList(labels));
        result.put("count", Collections.singletonList(counts));
        return result;
    }
}
