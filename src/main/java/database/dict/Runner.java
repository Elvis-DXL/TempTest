package database.dict;

public class Runner {
    public static void main(String[] args) {
        MysqlDict.newInstance(new Config()
                        .setHost("192.168.2.203").setPort(3306)
                        .setUser("root").setPwd("Sansi@2024123")
                        .setTableSchema("avis"))
                .get("D://数据字典.docx");
    }
}