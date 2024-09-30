package basejpaold.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 14:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpUser implements Serializable {
    private Long userId;
    private String userName;
    private String userEx1;
    private String userEx2;
    private String userEx3;
    private String userEx4;
    private String userEx5;
}