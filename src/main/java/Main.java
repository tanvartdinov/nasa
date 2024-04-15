import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod" +
                "?api_key=w0bUmEc7sjrKiuqsswbRp2q6E1Tz90EDesaAxxfk" +
                "&date=2024-04-09";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

//        Scanner scanner = new Scanner(response.getEntity().getContent());
//        System.out.println(scanner.nextLine());
        NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);
        String[] urlSeparated = answer.url.split("/");
        String filename = "/Users/timur/Documents/netology-teaching/JAVAFREE/JAVAFREE-23/NASA/Image/" + urlSeparated[urlSeparated.length - 1];

        HttpGet httpGetImage = new HttpGet(answer.url);
        CloseableHttpResponse image = httpClient.execute(httpGetImage);

        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        image.getEntity().writeTo(fileOutputStream);
    }
}
