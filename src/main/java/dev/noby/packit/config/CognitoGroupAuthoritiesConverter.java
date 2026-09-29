package dev.noby.packit.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/** Maps Cognito user-pool group names to Spring Security roles. */
public class CognitoGroupAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> groups = jwt.getClaimAsStringList("cognito:groups");
        if (groups == null || groups.isEmpty()) {
            return List.of();
        }

        return groups.stream()
                .filter(group -> group.equals("ADMIN") || group.equals("SUPERVISOR"))
                .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                .collect(Collectors.toUnmodifiableSet());
    }
}
