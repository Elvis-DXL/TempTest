package test.demopg.vo.cmd;

import com.zx.core.base.form.Query;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:56
 */
@Data
public class DemoListCmd extends Query implements Serializable {

    private String name;

    public DemoListCmd name(String name) {
        this.name = name;
        return this;
    }
}
