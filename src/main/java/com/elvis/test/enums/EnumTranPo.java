package com.elvis.test.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/11 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnumTranPo implements Serializable {
    private String enumName;
    private List<EnumTranItemPo> itemList;
}
