package com.example.mojingalgorithm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "job")
public class Job {

    @Getter
    @Setter
    @TableId(value = "_id", type = IdType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @TableField(value = "_name")
    private String name;

    @Getter
    @Setter
    @TableField(value = "_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Getter
    @Setter
    @TableField(value = "_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @Getter
    @Setter
    @TableField(value = "_status")
    private Integer status;

    @Getter
    @Setter
    @TableField(value = "_model_id")
    private Integer modelId;

    @Getter
    @Setter
    @TableField(value = "_oa")
    private Float overallAccuracy;

    @Getter
    @Setter
    @TableField(value = "_pre")
    private Float pre;

    @Getter
    @Setter
    @TableField(value = "_recall")
    private Float recall;

    @Getter
    @Setter
    @TableField(value = "_mse")
    private Float mse;

    @Getter
    @Setter
    @TableField(value = "_tpr")
    private String truePositiveRate;

    @Getter
    @Setter
    @TableField(value = "_fpr")
    private String falsePositiveRate;

    @Getter
    @Setter
    @TableField(value = "_c0rate")
    private Float c0Rate;

    @Getter
    @Setter
    @TableField(value = "_c1rate")
    private Float c1Rate;

    @Getter
    @Setter
    @TableField(value = "_c2rate")
    private Float c2Rate;

    @Getter
    @Setter
    @TableField(value = "_c3rate")
    private Float c3Rate;

    @Getter
    @Setter
    @TableField(value = "_kaf")
    private Float kaf;

    @Getter
    @Setter
    @TableField(value = "_p")
    private Float p;

    @Getter
    @Setter
    @TableField(value = "_designerRate")
    private Float designerRate;

    @Getter
    @Setter
    @TableField(value = "_interRate")
    private Float interRate;

    @Getter
    @Setter
    @TableField(value = "_intraRate")
    private Float intraRate;

}
