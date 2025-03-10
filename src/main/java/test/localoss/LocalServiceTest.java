package test.localoss;

import java.io.File;
import java.io.FileInputStream;

public class LocalServiceTest {

    public static void main(String[] args) throws Exception {
        LocalService localService = new LocalService("E:/TDDOWN");
        FileInputStream fis = new FileInputStream(new File("E:/TDDOWN/default.md"));
        localService.putFile(null, "default2.md", fis);
    }
}