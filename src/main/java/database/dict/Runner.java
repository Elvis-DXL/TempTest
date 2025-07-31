package database.dict;

public class Runner {
    public static void main(String[] args) {
        MysqlDict.newInstance(new Config()
                        .setHost("192.168.2.203").setPort(3306)
                        .setUser("root").setPwd("Sansi@2024123")
                        .setTableSchema("smart_home"))
                .writeOut("E:/暂时存储集");
    }
}