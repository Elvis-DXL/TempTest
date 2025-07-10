package test.localoss;

import basejpa.util.DSUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;

public class LocalService {

    private final String pathUrl;

    public LocalService(String pathUrl) {
        if (!pathUrl.endsWith("/")) {
            pathUrl = pathUrl.concat("/");
        }
        this.pathUrl = pathUrl;
    }


    public String putFile(String bucketName, String fileName, InputStream iStream) {
        if (null == bucketName) {
            bucketName = DSUtil.TimeTool.formatLD(LocalDate.now(), DSUtil.Pattern.yyyyMMdd);
        }
        File file = new File(pathUrl.concat(bucketName));
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(pathUrl.concat(bucketName).concat("/").concat(fileName)));
            DSUtil.IOTool.inToOut(iStream, fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "成功";
    }

    public InputStream getFile(String bucketName, String fileName) {


        return null;
    }

    public void deleteFile(String bucketName, String fileName) {

    }

    static class OssFile {
        private String relUrl;
        private String fullUrl;
        private String fullName;
    }
}