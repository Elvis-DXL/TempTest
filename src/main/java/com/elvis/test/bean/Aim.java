package com.elvis.test.bean;

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

    private Integer id;

    private String name;

    private String img;
}
