package com.elvis.test.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/11/3 16:31
 */
public class PdfTest {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File("D:/TDDOWN/temp.pdf"));

        Map<String,Object> map = new HashMap<>();
        map.put("pkcompany","111");
        map.put("psnname","123123");



        fos.write(PdfUtil.createPDF(map, "temp.ftl").toByteArray());
    }
}
