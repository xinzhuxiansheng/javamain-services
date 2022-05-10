package com.javamain.longpolling.client;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConfigClientV1 {
    private static final Logger logger = LoggerFactory.getLogger(ConfigClientV1.class);

    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    public ConfigClientV1() {
        this.httpClient = HttpClientBuilder.create().build();
        this.requestConfig = RequestConfig.custom().setSocketTimeout(60000).build();
    }

    public void longPolling(String url, String key) {
        while (true) {
            try {
                String path = String.format("%s?key=%s", url, key);
                HttpGet request = new HttpGet(path);
                request.setConfig(requestConfig);
                CloseableHttpResponse response = httpClient.execute(request);
                switch (response.getStatusLine().getStatusCode()) {
                    case 200: {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
                                .getContent()));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        response.close();
                        String value = result.toString();
                        logger.info("key: {} changed, receive value: {}", key, value);
                        break;
                    }
                    case 304: {
                        logger.info("longPolling key: {} once finished, value is unchanged, longPolling again", key);
                        break;
                    }
                    default: {
                        System.out.println("default ： " + response.getStatusLine().getStatusCode());
                    }
                }
            } catch (Exception e) {
                logger.info("", e);
            }
        }

    }

    public static void main(String[] args) {
        ConfigClientV1 configClient = new ConfigClientV1();
        configClient.longPolling("http://127.0.0.1:8851/notifications/v1", "v1");
    }
}
