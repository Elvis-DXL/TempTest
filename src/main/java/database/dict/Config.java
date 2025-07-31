package database.dict;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Config implements Serializable {
    private String host;
    private Integer port;
    private String user;
    private String pwd;
    private String tableSchema;
}