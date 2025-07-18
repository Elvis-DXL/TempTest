package test.ai;

import basejpa.interfaces.BaseThrowBizEx;
import basejpa.util.DSUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.util.JsonTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIBusiness implements BaseThrowBizEx {
    private final Logger log = LoggerFactory.getLogger(AIBusiness.class);

    private final String baseUrl;
    private final String model;
    private final Double temperature;
    private final Boolean returnJson;
    private final Integer timeOutMilliseconds;

    private AIBusiness(String baseUrl, String model, Double temperature,
                       Boolean returnJson, Integer timeOutMilliseconds) {
        this.baseUrl = baseUrl;
        this.model = model;
        this.temperature = temperature;
        this.returnJson = returnJson;
        this.timeOutMilliseconds = timeOutMilliseconds;
    }

    public static AIBusiness newInstance(String baseUrl, String model) {
        return newInstance(baseUrl, model, null, null, 300000);
    }

    public static AIBusiness newInstance(String baseUrl, String model, Double temperature,
                                         Boolean returnJson, Integer timeOutMilliseconds) {
        DSUtil.trueThrow(DSUtil.EmptyTool.isEmpty(baseUrl),
                new IllegalArgumentException("baseUrl must not be empty"));
        DSUtil.trueThrow(DSUtil.EmptyTool.isEmpty(model),
                new IllegalArgumentException("model must not be empty"));
        return new AIBusiness(baseUrl, model, temperature, returnJson, timeOutMilliseconds);
    }

    /*****************************************************************************************/
    public String execute(String content) {
        JSONObject js = new JSONObject();
        js.set("model", this.model);
        List<Map<String, String>> msgList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        msgList.add(map);
        map.put("role", "user");
        map.put("content", content);
        js.set("messages", msgList);
        js.set("stream", false);
        if (null != this.temperature) {
            js.set("temperature", this.temperature);
        }
        if (null != this.returnJson && this.returnJson) {
            JSONObject inner = new JSONObject();
            inner.set("type", "json_object");
            js.set("response_format", inner);
        }
        log.info("AI服务请求参数【{}】", JsonTool.toJson(js));
        try {
            HttpResponse response = HttpRequest.post(this.baseUrl.concat("/api/chat"))
                    .body(JsonTool.toJson(js))
                    .contentType(ContentType.JSON.getValue())
                    .timeout(null == this.timeOutMilliseconds ? 300000 : this.timeOutMilliseconds)
                    .execute();
            if (!response.isOk()) {
                log.error(StrUtil.format("AI服务请求失败【{}:{}】", response.getStatus(), response.body()));
                throwBizEx("AI服务请求失败");
            }
            String body = response.body();
            log.info("AI服务请求结果为【{}】", body);
            return JSONUtil.parseObj(body).getJSONObject("message").getStr("content");
        } catch (Exception e) {
            log.error("AI服务请求失败", e);
            throw bizEx("AI服务请求失败");
        }
    }
}