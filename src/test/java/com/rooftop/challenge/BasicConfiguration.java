package com.rooftop.challenge;

import com.google.gson.Gson;
import com.rooftop.challenge.DTO.SortMesageDTO;
import com.rooftop.challenge.Service.CircuitBreakerRegistryProvider;
import com.rooftop.challenge.Service.Compare;
import com.rooftop.challenge.Service.SelectionSort;
import com.rooftop.challenge.Service.SortService;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class BasicConfiguration {
    static Gson gson = new Gson();

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
        String url = createWebServer();

        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();

        properties.setProperty("rooftop_checkUrl", url);
        properties.setProperty("rooftop_tokenUrl", "https://rooftop-career-switch.herokuapp.com/token?email=leandrovalido@gmail.com");
        properties.setProperty("rooftop_blockUrl", "https://rooftop-career-switch.herokuapp.com/blocks?token=%s");
        pspc.setProperties(properties);
        return pspc;
    }

    @Bean
    public  static SortService sortService() throws IOException {

        String webServerUrl = createWebServer();
        CircuitBreakerRegistryProvider cb = new CircuitBreakerRegistryProvider();
        cb.createRegistry();
        return new SelectionSort(new Compare(cb, webServerUrl));
    }
     static String createWebServer() throws IOException {

        MockWebServer webServer = new MockWebServer();

        webServer.start();
        HttpUrl url = webServer.url("/check");

        SortMesageDTO dto = new SortMesageDTO();
        dto.setMessage(false);

        webServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
                return new MockResponse().setBody(gson.toJson(dto));
            }
        });
        return url.toString();
    }
}
