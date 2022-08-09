import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestClientPost {

    public static final String[] type = {"main-type", "secondary-types"};

    public static final String[] type_possibility = {"main-type-possibility", "secondary-type-possibility"};

    private static final Logger logger = Logger.getLogger(TestClientPost.class.getName());

    private static final String path = System.getProperty("user.dir") + "\\src\\test\\java\\ClientPost.log";

    @Test
    public void rtPostObject() throws JSONException, IOException {

        FileHandler fileHandler = new FileHandler(path);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.18.8.43:18101/textClassify";
        String url2 = "http://localhost:8080/textClassify";
        String url3 = "http://119.3.24.91:80/textClassify";
        String url4 = "http://192.168.0.165:8080/textClassify";
        String request = "森林火情";
        request = URLEncoder.encode(request, "UTF-8");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("text", request);
        map.add("accessToken", "TEXTCLASSIFYFORHANLP");

        assertNotNull(restTemplate.postForEntity(url2, map, String.class));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2, map, String.class);
        fileHandler.setFormatter(new SimpleFormatter());//官设格式

        logger.addHandler(fileHandler);

        logger.log(Level.INFO, "Start Writing to File..");
        logger.info(getResponseHeaders(responseEntity));
        logger.info("Write Successfully.");
    }

    public static String getResponseHeaders(ResponseEntity<String> responseEntity) throws JSONException {
        String ResponseHeaders = "";
        ResponseHeaders = ResponseHeaders.concat("Response Headers:\n");
        ResponseHeaders = ResponseHeaders.concat(responseEntity.getStatusCode() + "\n");
        ResponseHeaders = ResponseHeaders.concat("Content-Type:" + responseEntity.getHeaders().get("Content-Type") + "\n");
        ResponseHeaders = ResponseHeaders.concat("Transfer-Encoding:" + responseEntity.getHeaders().get("Transfer-Encoding") + "\n");
        ResponseHeaders = ResponseHeaders.concat("Date:" + responseEntity.getHeaders().get("Date") + "\n");
        ResponseHeaders = ResponseHeaders.concat("Keep-Alive:" + responseEntity.getHeaders().get("Keep-Alive") + "\n");
        ResponseHeaders = ResponseHeaders.concat("Connection:" + responseEntity.getHeaders().get("Connection") + "\n");
        String result = responseEntity.getBody();
        JSONObject json = new JSONObject(result);
        ResponseHeaders = ResponseHeaders.concat("response\n{\n");
        boolean flag = false;
        JSONArray jsonarray = json.getJSONArray("type");
        JSONArray jsonarrayposs = json.getJSONArray("type-possibility");
        for (int i = 0; i < jsonarray.length(); i++) {
            if (i == 1) {
                ResponseHeaders = ResponseHeaders.concat("        {\n");
            }
            ResponseHeaders = ResponseHeaders.concat("        \"" + type[i > 0 ? 1 : 0] + "\":" + IntToString((String) jsonarray.get(i)) + "\n");
            ResponseHeaders = ResponseHeaders.concat("        \"" + type_possibility[i > 0 ? 1 : 0] + "\":" + jsonarrayposs.get(i) + "\n");
            if (i == jsonarray.length() - 1 && i > 0) {
                ResponseHeaders = ResponseHeaders.concat("        }\n");
            }
            flag = true;
        }
        if (!flag) {
            ResponseHeaders = ResponseHeaders.concat("        accessToken:" + json.get("Token") + "\n");
        }
        ResponseHeaders = ResponseHeaders.concat("}\n");
        return ResponseHeaders;
    }

    public static String IntToString(String label) {
        return switch (label) {
            case "0001" -> "安全生产";
            case "0002" -> "社会安全";
            case "0003" -> "自然灾害";
            case "0004" -> "公共卫生";
            case "0005" -> "通知";
            case "0006" -> "其他";
            default -> "null";
        };
    }
}
