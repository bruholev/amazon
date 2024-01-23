package bot.amazon.Controller;

import bot.amazon.Service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.*;

@RestController
@Slf4j
public class ControllerMessage {

    ExecutorService service = Executors.newCachedThreadPool();
    @Autowired
    MessageService messageService;


    @GetMapping(value = "/proxy/{url}")
    public String getRequest(@PathVariable("url") String url) {

        try {
            Callable<String> call = () -> {
                log.info(" ControllerMessage getRequest url " + url);
                String response = messageService.request(url,"get","");
                log.info(" ControllerMessage shoot response " + response);
                return response;
            };
            return service.submit(call).get(10,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("Ошибка ControllerMessage");
            return "";
        }

    }

    @PostMapping("/proxy/{url}")
    public String postRequest(@PathVariable("url") String url, @RequestParam String body) {
        try {
            Callable<String> call = () -> {
                log.info(" ControllerMessage postRequest url " + url);
                String response = messageService.request(url,"post", body);
                log.info(" ControllerMessage shoot response " + response);
                return response;
            };
            return service.submit(call).get(10,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("Ошибка ControllerMessage");
            return "Error";
        }
    }
}
