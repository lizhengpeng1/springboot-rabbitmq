package com.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lizhengpeng
 * @create 2021/2/23 - 10:37
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    private String goodsId; //商品ID
    private String goodsName; //商品名称

}
