package context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserContext implements Serializable {
    private Long id;
    private String name;
    private String account;
    private List<String> roleCodes;
    private List<String> permissions;
    private Map<String, String> extMap;

    public UserContext id(Long id) {
        this.id = id;
        return this;
    }

    public UserContext name(String name) {
        this.name = name;
        return this;
    }

    public UserContext account(String account) {
        this.account = account;
        return this;
    }

    public UserContext roleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
        return this;
    }

    public UserContext permissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public UserContext extMap(Map<String, String> extMap) {
        this.extMap = extMap;
        return this;
    }
}