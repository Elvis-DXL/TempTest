package test.sms;

import com.zx.core.tool.utils.JsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SmsTest {

    public static void main(String[] args) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(dealHttpPost("123452", "15196605197,17623373906"))) {
            String string = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("返回结果：" + string);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static HttpPost dealHttpPost(String msg, String phone) throws Exception {
        //请参考帮助文档填写以下内容
        Map<String, Object> params = new HashMap<>();
        //固定参数
        params.put("action", "SendSms");
        //请填写您在控制台上申请并通过的短信签名。
        params.put("signName", "天翼云测试");
        //请填写接收短信的目标手机号，多个手机号使用英文逗号分开
        params.put("phoneNumber", phone);
        //请填写您在控制台上申请并通过的短信模板，此模板为测试专用模板，可直接进行测试
        params.put("templateCode", "SMS92484184885");
        //请填写短信模板对应的模板参数和值。此值为测试模板的变量及参数，可直接使用
        params.put("templateParam", "{\"code\":\"123456\"}");
        String body = JsonUtil.toJson(params);
        // SETUP1:获取AccessKey和SecurityKey
        String accessKey = "432687b52ea94cb6b90deb175fc24a77";  // 填写控制台->个人中心->安全设置->查看->AccessKey
        String securityKey = "1cf592f622564fd3ab55cafe8790ed36";// 填写控制台->个人中心->安全设置->查看->SecurityKey
        // SETUP2:构造时间戳
        SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
        Date nowdate = new Date();
        String singerDate = TIME_FORMATTER.format(nowdate);
        String singerDd = DATE_FORMATTER.format(nowdate);
        System.out.println("singerDate:" + singerDate);
        System.out.println("singerDd:" + singerDd);
        // SETUP3:构造请求流水号
        String uuId = UUID.randomUUID().toString();
        System.out.println("uuId:" + uuId);
        // SETUP4:构造待签名字符串
        String campmocalHeader = String.format("ctyun-eop-request-id:%s\neop-date:%s\n", uuId, singerDate);
        // header的key按照26字母进行排序, 以&作为连接符连起来
        URL url = new URL("https://sms-global.ctapi.ctyun.cn/sms/api/v1");
        String query = url.getQuery();
        String afterQuery = "";
        if (query != null) {
            String param[] = query.split("&");
            Arrays.sort(param);
            for (String str : param) {
                if (afterQuery.length() < 1)
                    afterQuery = afterQuery + str;
                else
                    afterQuery = afterQuery + "&" + str;
            }
        }
        String calculateContentHash = getSHA256(body); // 报文原封不动进行sha256摘要
        String sigtureStr = campmocalHeader + "\n" + afterQuery + "\n" + calculateContentHash;
        System.out.println("calculateContentHash：" + calculateContentHash);
        System.out.println("sigtureStr：" + sigtureStr);
        // SETUP5:构造签名
        byte[] ktime = HmacSHA256(singerDate.getBytes(), securityKey.getBytes());
        byte[] kAk = HmacSHA256(accessKey.getBytes(), ktime);
        byte[] kdate = HmacSHA256(singerDd.getBytes(), kAk);
        String Signature = Base64.getEncoder().encodeToString(HmacSHA256(sigtureStr.getBytes("UTF-8"), kdate));
        // SETUP6:构造请求头
        HttpPost httpPost = new HttpPost(String.valueOf(url));
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("ctyun-eop-request-id", uuId);
        httpPost.setHeader("Eop-date", singerDate);
        String signHeader = String.format("%s Headers=ctyun-eop-request-id;eop-date Signature=%s", accessKey, Signature);
        httpPost.setHeader("Eop-Authorization", signHeader);
        System.out.println("Signature" + Signature);
        System.out.println("signHeader" + signHeader);
        httpPost.setEntity(new StringEntity(body, ContentType.create("application/json", "utf-8")));
        return httpPost;
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        byte[] var2 = data;
        int var3 = data.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return sb.toString().toLowerCase(Locale.getDefault());
    }

    private static String getSHA256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            return toHex(md.digest());
        } catch (NoSuchAlgorithmException var3) {
            return null;
        }
    }

    private static byte[] HmacSHA256(byte[] data, byte[] key) throws Exception {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }
}