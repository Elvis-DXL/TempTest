package context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserContext implements Serializable {
    private String userId;
    private String userName;
    private boolean superAdmin;
    private List<String> roleCodes;
    private List<String> permissions;
    private Map<String, String> extMap;

    public UserContext id(String userId) {
        this.userId = userId;
        return this;
    }

    public UserContext name(String userName) {
        this.userName = userName;
        return this;
    }

    public UserContext superAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
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