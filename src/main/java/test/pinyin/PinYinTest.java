package test.pinyin;

import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author : Elvis
 * @since : 2023/7/21 15:01
 */
public class PinYinTest {
    public static void main(String[] args) throws Exception {
        System.out.println(py("复方甘草口服溶液"));
        System.out.println(PinyinHelper.getShortPinyin("左氧氟沙星片（可乐必妥）").toUpperCase());
    }

    public static String py(String src) throws Exception {
        if (null == src || src.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < src.length(); index++) {
            sb.append(PinyinHelper.getShortPinyin(src.substring(index, index + 1)));
        }
        return sb.toString().toUpperCase();
    }
}
