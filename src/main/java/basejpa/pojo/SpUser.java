package basejpa.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 14:14
 */
@Data
public class SpUser implements Serializable {
    private Long userId;
    private String userName;
}
