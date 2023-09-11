package com.elvis.test.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/11 16:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnumTranItemPo implements Serializable {
    private String itemName;
    private String tranStr;
}
