package com.elvis.test.lnglat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 经纬度对象
 *
 * @author : Elvis
 * @since : 2023/2/3 17:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("LngLat|经纬度对象")
public final class LngLat implements Serializable {

    @ApiModelProperty("经度")
    private double lng;

    @ApiModelProperty("纬度")
    private double lat;
}
