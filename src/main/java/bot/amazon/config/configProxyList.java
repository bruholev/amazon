package bot.amazon.config;

import bot.amazon.Dao.ProxyServer;
import bot.amazon.Service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@Service
public class configProxyList {
    @Bean
    public List<ProxyServer> fillList() {

        MessageService.proxyServerList=  new CopyOnWriteArrayList<>();
            MessageService.proxyServerList.add(new ProxyServer("46.37.103.75", 12323, "pack3use3", "e9290078d3", true, LocalDateTime.now()));
            MessageService.proxyServerList.add(new ProxyServer("181.177.114.87", 12323, "pack3use3", "e9290078d3", true, LocalDateTime.now()));
            MessageService.proxyServerList.add(new ProxyServer("104.234.230.216", 12323, "pack3use3", "e9290078d3", true, LocalDateTime.now()));
            MessageService.proxyServerList.add(new ProxyServer("152.89.249.250", 12323, "pack3use3", "e9290078d3", true, LocalDateTime.now()));
            MessageService.proxyServerList.add(new ProxyServer("102.129.149.26", 12323, "pack3use3", "e9290078d3", true, LocalDateTime.now()));

        return MessageService.proxyServerList;

    }
}
