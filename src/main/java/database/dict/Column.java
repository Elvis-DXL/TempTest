package database.dict;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Column implements Serializable {
    private String table;
    private String name;
    private String comment;
    private String nullable;
    private String primaryKey;
    private String type;
    private Integer sort;
}