package basejpa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Elvis
 * @since : 2023/5/22 10:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePojo implements Serializable {
    private String fileUrl;
    private String fileName;
}