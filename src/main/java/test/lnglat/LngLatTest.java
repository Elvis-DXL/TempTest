package test.lnglat;

/**
 * @author : Elvis
 * @since : 2023/2/3 16:59
 */
public class LngLatTest {

    public static void main(String args[]) {
        ThreeTypeLngLat threeTypeLngLat = LngLatUtil.allTypeLngLat(105.590637, 30.482488, LngLatTypeEnum.BD09);


        System.out.println(threeTypeLngLat);

    }
}
