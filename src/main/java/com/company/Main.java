package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "https://api.nasa.gov/planetary/apod?api_key=a1QzRHaPbkOoGBiyLOixAmH8JyZznUvQ9xCgK3Ry";

        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper();

        Nasa answer = mapper.readValue(response.getEntity().getContent(), Nasa.class);

        String urlImage = answer.getUrl();

        String[] separatedUrl = urlImage.split("/");
        String fileName = separatedUrl[separatedUrl.length - 1];

        CloseableHttpResponse responseImage = httpClient.execute(new HttpGet(urlImage));

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        responseImage.getEntity().writeTo(fileOutputStream);
    }
}
