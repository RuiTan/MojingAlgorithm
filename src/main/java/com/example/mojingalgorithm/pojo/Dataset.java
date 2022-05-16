package com.example.mojingalgorithm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@TableName(value = "dataset")
public class Dataset {
    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "_dataset_name")
    private String datasetName;
    @TableField(value = "_upload_time")
    private Date uploadTime;
    @TableField(value = "_model_id")
    private Integer modelId;
    @TableField(value = "_categories")
    private String categories;
    @TableField(value = "_labels")
    private String labels;
}
