package com.learn.mvc.excel.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: cntytz
 * @version:
 * @description: 基础信息导入
 * @author: ling
 * @create: 2020-09-15 10:43
 **/
@Data
public class BaseImportEntity {
    /**
     * 小区名称
     */
    @NotBlank(message = "小区名称不能为空")
    @ExcelProperty("小区名称（必填）")
    // @ExcelProperty(index = 0, value = "小区名称（必填）")
    private String communityName;
    /**
     * 省份
     */
    @NotBlank(message = "所属省份不能为空")
    @ExcelProperty("省份（必填）")
    // @ExcelProperty(index = 1, value = "省份（必填）")
    private String province;
    /**
     * 市
     */
    @NotBlank(message = "所属地级市不能为空")
    @ExcelProperty("城市（必填）")
    // @ExcelProperty(index = 2, value = "城市（必填）")
    private String city;
    /**
     * 区县
     */
    @NotBlank(message = "所属区县不能为空")
    @ExcelProperty("区县（必填）")
    // @ExcelProperty(index = 3, value = "区县（必填）")
    private String county;
    /**
     * 街道
     */
    @NotBlank(message = "所属区县不能为空")
    @ExcelProperty("街道（必填）")
    // @ExcelProperty(index = 3, value = "区县（必填）")
    private String court;
    /**
     * 区县
     */
    @NotBlank(message = "所属区县不能为空")
    @ExcelProperty("社区（必填）")
    // @ExcelProperty(index = 3, value = "区县（必填）")
    private String settlement;
    /**
     * 具体地址
     */
    @NotBlank(message = "小区地址不能为空")
    @ExcelProperty("地址（必填）")
    // @ExcelProperty(index = 4, value = "地址（必填）")
    private String address;

    /**
     * 全地址信息 格式为:省_市_区_具体地址_小区名
     */
    @ExcelProperty("详细地址（非必填）")
    // @ExcelProperty(index = 5, value = "详细地址（非必填）")
    private String addressDetail;

    /**
     * 小区住房面积
     */
    @ExcelProperty("小区住房面积㎡（非必填）")
    // @ExcelProperty(index = 6, value = "小区住房面积㎡（非必填）")
    private BigDecimal area;
    /**
     * 小区经度
     */
    @ExcelProperty("小区经度（非必填）")
    // @ExcelProperty(index = 7, value = "小区经度（非必填）")
    private BigDecimal longitude;
    /**
     * 维度
     */
    @ExcelProperty("小区纬度（非必填）")
    // @ExcelProperty(index = 8, value = "小区纬度（非必填）")
    private BigDecimal latitude;

    /**
     * 楼号
     */
    @NotBlank(message = "房屋所属楼号不能为空")
    @ExcelProperty("楼号（必填）")
    // @ExcelProperty(index = 9, value = "楼号（必填）")
    private String floorNum;

    /**
     * 单元
     */
    @NotBlank(message = "房屋所属单元不能为空")
    @ExcelProperty("单元（必填）")
    // @ExcelProperty(index = 10, value = "单元（必填）")
    private String unitNum;

    /**
     * 房号
     */
    @NotBlank(message = "房屋编号不能为空")
    @ExcelProperty("房号（必填）")
    // @ExcelProperty(index = 11, value = "房号（必填）")
    private String roomNum;

    /**
     * 房屋面积 平方米
     */
    @ExcelProperty("房屋面积㎡（必填）")
    // @ExcelProperty(index = 12, value = "房屋面积㎡（必填）")
    @NotNull(message = "房屋面积不能为空")
    private BigDecimal houseArea;

    /**
     * 产权编码
     */
    @ExcelProperty("产权编码（必填）")
    // @ExcelProperty(index = 13, value = "产权编码（非必填）")
    @NotNull(message = "产权编码不能为空")
    private String propertyCode;
    /**
     * 房屋性质 数据字典 house_type
     */
    @ExcelProperty("房屋性质（非必填；商品住房）")
    // @ExcelProperty(index = 14, value = "房屋性质（非必填；商品住房）")
    private String type;
    /**
     * 用途 数据字典 house_user_type
     */
    @ExcelProperty("土地用途（非必填、居住用地）")
    // @ExcelProperty(index = 15, value = "土地用途（非必填、居住用地）")
    private String useType;

    /**
     * 业主名称
     */
    @NotBlank(message = "业主姓名不能为空")
    @ExcelProperty("业主姓名（必填）")
    // @ExcelProperty(index = 16, value = "业主姓名（必填）")
    private String name;
    /**
     * 证件号码
     */
    @NotBlank(message = "业主身份证号不能为空")
    @ExcelProperty("身份证号（必填）")
    // @ExcelProperty(index = 17, value = "身份证号（必填）")
    private String idCode;

    /**
     * 所有类型 数据字典 house_own_type 1.产权 2.共有权
     */
    @NotBlank(message = "房屋产权类型（产权、共有权）不能为空")
    @ExcelProperty("所有类型（必填；产权、共有权）")
    // @ExcelProperty(index = 18, value = "所有类型（必填；产权、共有权）")
    private String ownType;
}
