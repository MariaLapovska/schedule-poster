package com.hackathon.sp.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;

public class CustomHttpClient {

    public BufferedReader get(URI uri) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);

        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    public BufferedReader postMultipartFile(URI uri, String filePath, String fileType) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(uri);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

        entityBuilder.addPart(fileType, new FileBody(new File(filePath)));
        httpPost.setEntity(entityBuilder.build());
        HttpResponse response = httpClient.execute(httpPost);

        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}
