package basejpa.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Elvis
 * @since : 2023/5/22 10:38
 */
@Data
public class FilePojo implements Serializable {
    private String fileUrl;
    private String fileName;
}