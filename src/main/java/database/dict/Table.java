package database.dict;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Table implements Serializable {
    private String name;
    private String comment;
}
