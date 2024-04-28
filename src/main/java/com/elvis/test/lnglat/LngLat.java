package com.elvis.test.lnglat;

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
public final class LngLat implements Serializable {
    private double lng;
    private double lat;
}
