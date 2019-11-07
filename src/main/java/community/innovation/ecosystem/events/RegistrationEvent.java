package community.innovation.ecosystem.events;

import community.innovation.ecosystem.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {

    private static final long SERIAL_VERSION_UID=1L;
    private String appUrl;
    private Locale locale;
    private User user;

    public RegistrationEvent(String appUrl, Locale locale, User user) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    // getters and setters by lombok
}
