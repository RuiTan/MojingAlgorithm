package com.example.mojingalgorithm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName(value = "label_count")
@Builder
public class LabelCount {
    @TableField(value = "label")
    private String label;

    @TableField(value = "count")
    private int count;

    @TableField(value = "tag")
    private int tag;
}
