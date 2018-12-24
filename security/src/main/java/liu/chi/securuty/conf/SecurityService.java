package liu.chi.securuty.conf;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author liuchi
 * @date 2018-11-08 13:44
 */
@Service
public class SecurityService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        if ("user".equalsIgnoreCase(username)) {
            user.setUsername("user");
            user.setPassword("12345");
            user.setRole("user");
            return user;
        }
        if ("admin".equalsIgnoreCase(username)) {
            user.setUsername("admin");
            user.setPassword("123456");
            user.setRole("admin");
            return user;
        }
        return null;
    }
}
