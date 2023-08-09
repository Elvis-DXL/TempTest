package com.elvis.test.encrypt;

import com.elvis.commons.utils.AnnoUtil;

/**
 * @author : Elvis
 * @since : 2022/11/29 09:44
 */
public class EncryptTest {
    public static void main(String args[]) {
        EncryptPojo encryptPojo = new EncryptPojo();
        encryptPojo.setName("12312");
        encryptPojo.setId("adfasdfs");
        encryptPojo.setCard("afdfasdfs");


        System.out.println(encryptPojo.toString());

        AnnoUtil.encodeEncryption(encryptPojo);

        System.out.println(encryptPojo.toString());

        AnnoUtil.decodeEncryption(encryptPojo);

        System.out.println(encryptPojo.toString());
    }
}
