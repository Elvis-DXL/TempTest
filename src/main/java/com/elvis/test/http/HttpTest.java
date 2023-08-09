package com.elvis.test.http;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

/**
 * @author : Elvis
 * @since : 2022/9/7 14:57
 */
public class HttpTest {
    public static void main(String args[]) {
        HttpRequest request = HttpUtil
                .createPost("http://117.174.85.72:20688/admin/dvsc-cdsv-web/api/cdsv/exchange/getToken");
        request.form("encryptStr", "Jvq4t0NGyLik+TYh1+UYYYXaVDuPsGW1opop/9MEMUO3nQs5IoBTnOarC0u+kaEbImUvjaDfB1H6SEYGVcrR6myEtg9UzPQPVDM/BJv9XNAjHZTQ0nY2GrjXTZCiE4waV6WmjIBETS0lkxlZwxtKn+3txpsksozbkIuHVi62lVgQ3iULGcRr8FT11Jnc8t32t3saqKw0xw18IIG+HFFfVNxAA3tOLP+2CUo5f9trtXilh6HzvwWhiblR3rtnrzpxwVzNFh8toRJJ/aKprwWxUosHIqTS9RmrK78V6YPclEme+FEhQP0/cfOTyuAaWAH2vx23TC+ht92YWNRy1BB9Zw==");


        HttpResponse execute = request.execute();

        System.out.println(execute);
    }
}
