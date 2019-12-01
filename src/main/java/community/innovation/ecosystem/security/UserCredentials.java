package community.innovation.ecosystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserCredentials implements UserDetails {

    private String username;
    private String password;
    private Boolean verification;

    public UserCredentials() { }

    public UserCredentials(String username, String password, Boolean verification) {
        this.username = username;
        this.password = password;
        this.verification=verification;
    }

    // needed when role will applicable
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return verification;
    }

    @Override
    public boolean isAccountNonLocked() {
        return verification;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return verification;
    }

    @Override
    public boolean isEnabled() {
        return verification;
    }
}
