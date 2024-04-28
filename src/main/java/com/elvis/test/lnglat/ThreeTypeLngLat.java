package com.elvis.test.lnglat;

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
public final class ThreeTypeLngLat implements Serializable {
    private LngLat gcj02;
    private LngLat bd09;
    private LngLat wgs84;
}
