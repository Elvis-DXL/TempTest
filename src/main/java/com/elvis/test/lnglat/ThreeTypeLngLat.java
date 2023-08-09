package com.elvis.test.lnglat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 三种坐标系经纬度
 *
 * @author : Elvis
 * @since : 2023/2/3 17:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ThreeTypeLngLat|三种坐标系经纬度")
public final class ThreeTypeLngLat implements Serializable {

    @ApiModelProperty("GCJ02坐标系")
    private LngLat gcj02;

    @ApiModelProperty("BD09坐标系")
    private LngLat bd09;

    @ApiModelProperty("WGS84坐标系")
    private LngLat wgs84;

}
