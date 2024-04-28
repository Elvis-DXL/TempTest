package com.elvis.test.excel;

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
public final class MultipleSheetExport<T> implements Serializable {
    private List<T> data;
    private Class<T> clazz;
    private String sheetName;
}
