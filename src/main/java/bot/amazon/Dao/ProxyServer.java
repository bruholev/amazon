package bot.amazon.Dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProxyServer {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean available = true;
    private LocalDateTime localDateTime;
}
