package epf.auth.config;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class Config {

	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() { 
		return (context) -> {
			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) { 
				context.getClaims().claims((claims) -> { 
					final Set<String> groups = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
							.stream()
							.map(c -> c.replaceFirst("^ROLE_", ""))
							.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet)); 
					claims.put("groups", groups); 
				});
			}
		};
	}
}
