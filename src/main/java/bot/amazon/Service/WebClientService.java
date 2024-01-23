package bot.amazon.Service;

import bot.amazon.Dao.ProxyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Service
@Slf4j
public class WebClientService {


    public WebClient createWebClient(ProxyServer proxyServer) {
        HttpClient httpClient = HttpClient.create()
                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                        .host(proxyServer.getHost())
                        .port(proxyServer.getPort())
                        .username(proxyServer.getUsername())
                        .password(proxyPassword -> proxyServer.getPassword()));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public  String sendGetRequest(String url, ProxyServer proxyServer) {
        try {

        String response = createWebClient(proxyServer).get().uri(url).retrieve().bodyToMono(String.class).block();
            log.info("WebClientService sendGetRequest response "+response);

        return response;}
    catch (Exception e){e.printStackTrace();}
        return "response";
    }

    public  String sendPostRequest(String url, String body, ProxyServer proxyServer) {

        String response = createWebClient(proxyServer).post().uri(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(Mono.just(body), String.class).retrieve().bodyToMono(String.class).block();
        log.info("WebClientService postGetRequest response "+response);
        return response;
    }

}
