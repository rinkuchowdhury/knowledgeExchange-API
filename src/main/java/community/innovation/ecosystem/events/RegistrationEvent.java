package community.innovation.ecosystem.events;

import community.innovation.ecosystem.entities.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

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

    public String getAppUrl() { return appUrl; }

    public void setAppUrl(String appUrl) { this.appUrl = appUrl; }

    public Locale getLocale() { return locale; }

    public void setLocale(Locale locale) { this.locale = locale; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
