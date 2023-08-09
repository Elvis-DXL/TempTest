package com.elvis.test.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Elvis
 * @since : 2023/1/6 08:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MultipleSheetExport|多Sheet导出")
public final class MultipleSheetExport<T> implements Serializable {
    @ApiModelProperty("数据")
    private List<T> data;
    @ApiModelProperty("类")
    private Class<T> clazz;
    @ApiModelProperty("Sheet名称")
    private String sheetName;
}
