package com.elvis.test.desensitization;

import com.elvis.commons.anno.Desensitization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aim implements Serializable {
    private String name;
    @Desensitization(start = 1, mid = 6, end = 2)
    private String code;
}
