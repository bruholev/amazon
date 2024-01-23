package bot.amazon.Service;

import bot.amazon.Dao.ProxyServer;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Service
public class MessageService {
    public   static CopyOnWriteArrayList<ProxyServer> proxyServerList;
    @Autowired
    WebClientService webClientService;

    @Value("${proxy.timeout}")
    private int proxyTimeout;

    Boolean getAvailable(ProxyServer proxyServer){
        if(proxyServer.isAvailable()==true||(proxyServer.isAvailable()==false&&(Duration.between(proxyServer.getLocalDateTime(),LocalDateTime.now()).toHours()>proxyTimeout)))
        {proxyServer.setAvailable(true);
            return true;}
        else {return false;}
    }

    public String request( String url,String format,String body ) {
        try {String response ;
            Boolean status5xx=false,status30x=false;

            for(ProxyServer proxyServer:proxyServerList){
                log.info("proxyServer "+proxyServer.getHost());
                    if(getAvailable(proxyServer)){
                        log.info("MessageService  request "+url);
                        response= format.equals("get")?webClientService.sendGetRequest(url,proxyServer)
                                :webClientService.sendPostRequest(url, body,proxyServer);
                        Element titleTag = Jsoup.parse(response).select("title").first();
                        if (titleTag != null) {
                            status5xx=titleTag.text().matches(".*(5).*");
                            status30x=titleTag.text().matches(".*(30[127]).*");
                        }
                        log.info("MessageService status5xx " +status5xx+" status30x "+status30x);
                        if(status5xx){
                            proxyServer.setAvailable(false);
                            proxyServer.setLocalDateTime(LocalDateTime.now());
                            log.info("MessageService status5xx "+status5xx);
                            log.info("MessageService proxyServer Ip "+proxyServer.getHost()+" status false 1 hour");
                            continue;
                        } else if (status30x) {
                            log.info("MessageService status30x "+status30x);
                           continue;
                        }else if((status5xx==false)&&(status30x==false)){
                            log.info("MessageService final response " );
                            return response;
                        }
                    }

}
 return "418";
        } catch (Exception e) {
            log.info("Ошибка в MessageService ");
            e.printStackTrace();  return "Error";        }

    }
}
