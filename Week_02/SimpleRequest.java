package com.mini.gw;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zoujintao
 * @Date: 2020/10/28 16:29
 */

@Slf4j
public class SimpleRequest {

    public  static String requestToLocal(String url) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // 发送get请求
        HttpGet request = new HttpGet(url);
        try {
            response = client.execute(request);
            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                return strResult;
            }else{
                return "请求失败！";
            }
        }catch (ClientProtocolException e) {
           log.error("客户端访问协议异常:{]",e);
        } catch (IOException e) {
            log.error("IO异常:{}",e);
        }finally {
            response.close();
            client.close();
        }
        return "";
    }

    public static void okRequestToLocal(String url){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(1,3000,TimeUnit.MILLISECONDS))
                .build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("请求失败：{}",e);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    log.info("响应成功:{}",response.body().string());
                }else{
                    log.info("响应失败:{}",response.body().string());
                }
                response.close();
                call.cancel();
            }
        });

    }

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8801";
        okRequestToLocal(url);
//        log.info("响应结果 : " +  requestToLocal(url));
    }

}