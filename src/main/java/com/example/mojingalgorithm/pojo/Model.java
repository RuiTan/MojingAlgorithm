package com.example.mojingalgorithm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
public class Model {

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
    @TableField(value = "_desc")
    private String desc;

}
