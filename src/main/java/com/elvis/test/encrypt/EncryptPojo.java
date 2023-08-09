package com.elvis.test.encrypt;

import com.elvis.commons.anno.Encryption;
import com.elvis.test.constant.Constant;
import lombok.Data;
import lombok.ToString;

/**
 * @author : Elvis
 * @since : 2022/11/29 09:44
 */
@Data
@ToString
public class EncryptPojo {

    @Encryption(secretKey = Constant.secretKey)
    private String name;

    @Encryption(secretKey = Constant.secretKey)
    private String id;

    private String card;
}
