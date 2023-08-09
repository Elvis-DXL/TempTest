package com.elvis.test.lnglat;

/**
 * 经纬度工具集
 *
 * @author : Elvis
 * @since : 2023/2/3 16:41
 */
public final class LngLatUtil {

    private static final double EARTH_RADIUS = 6371393;//地球平均半径(m)

    private LngLatUtil() {
    }

    /**
     * 计算两个经纬度之间距离
     *
     * @param srcLng 起点经度
     * @param srcLat 起点纬度
     * @param aimLng 终点经度
     * @param aimLat 终点纬度
     * @return 距离(m)
     */

    public static double getDistance(double srcLng, double srcLat, double aimLng, double aimLat) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(srcLng); // A经弧度
        double radiansAY = Math.toRadians(srcLat); // A纬弧度
        double radiansBX = Math.toRadians(aimLng); // B经弧度
        double radiansBY = Math.toRadians(aimLat); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos); // 反余弦值
        return EARTH_RADIUS * acos; // 最终结果
    }

    /**
     * 获取三种坐标系经纬度
     *
     * @param lng            经度
     * @param lat            纬度
     * @param lngLatTypeEnum 经纬度坐标系枚举
     * @return 三种坐标系经纬度
     */
    public static ThreeTypeLngLat allTypeLngLat(double lng, double lat, LngLatTypeEnum lngLatTypeEnum) {
        ThreeTypeLngLat threeTypeLngLat = new ThreeTypeLngLat();
        if (lngLatTypeEnum.equals(LngLatTypeEnum.WGS84)) {
            threeTypeLngLat.setWgs84(new LngLat(lng, lat));
            threeTypeLngLat.setGcj02(wgs84_To_Gcj02(lng, lat));
            threeTypeLngLat.setBd09(wgs84_To_Bd09(lng, lat));
        } else if (lngLatTypeEnum.equals(LngLatTypeEnum.GCJ02)) {
            threeTypeLngLat.setGcj02(new LngLat(lng, lat));
            threeTypeLngLat.setWgs84(gcj02_To_Wgs84(lng, lat));
            threeTypeLngLat.setBd09(gcj02_To_Bd09(lng, lat));
        } else {
            threeTypeLngLat.setBd09(new LngLat(lng, lat));
            threeTypeLngLat.setGcj02(bd09_To_Gcj02(lng, lat));
            threeTypeLngLat.setWgs84(bd09_To_Wgs84(lng, lat));
        }
        return threeTypeLngLat;
    }

    public static LngLat wgs84_To_Gcj02(double lng, double lat) {
        if (outOfChina(lat, lng)) {
            return new LngLat(lng, lat);
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLon = transformLng(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLon = lng + dLon;
        double mgLat = lat + dLat;
        return new LngLat(mgLon, mgLat);
    }

    public static LngLat wgs84_To_Bd09(double lng, double lat) {
        LngLat gcj02 = wgs84_To_Gcj02(lng, lat);
        return gcj02_To_Bd09(gcj02.getLng(), gcj02.getLat());
    }

    public static LngLat gcj02_To_Wgs84(double lng, double lat) {
        LngLat gps = transform(lng, lat);
        double longitude = lng * 2 - gps.getLng();
        double latitude = lat * 2 - gps.getLat();
        return new LngLat(longitude, latitude);
    }

    public static LngLat gcj02_To_Bd09(double lng, double lat) {
        double x = lng, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LngLat(tempLon, tempLat);
    }

    public static LngLat bd09_To_Wgs84(double lng, double lat) {
        LngLat gcj02 = bd09_To_Gcj02(lng, lat);
        return gcj02_To_Wgs84(gcj02.getLng(), gcj02.getLat());
    }

    public static LngLat bd09_To_Gcj02(double lng, double lat) {
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        return new LngLat(tempLon, tempLat);
    }

    public static double pi = 3.1415926535897932384626;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    private static LngLat transform(double lng, double lat) {
        if (outOfChina(lat, lng)) {
            return new LngLat(lng, lat);
        }
        double dLon = transformLng(lng - 105.0, lat - 35.0);
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lng + dLon;
        return new LngLat(mgLon, mgLat);
    }

    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
}
