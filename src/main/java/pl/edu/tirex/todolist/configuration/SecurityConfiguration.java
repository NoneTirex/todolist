package pl.edu.tirex.todolist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;
import pl.edu.tirex.todolist.board.BoardService;
import pl.edu.tirex.todolist.user.User;
import pl.edu.tirex.todolist.user.UserRepository;
import pl.edu.tirex.todolist.user.UserRole;
import pl.edu.tirex.todolist.util.HashHelper;

import javax.servlet.Filter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter
{
    private final OAuth2ClientContext oauth2ClientContext;

    private final UserRepository userRepository;

    private final BoardService boardService;

    @Autowired
    public SecurityConfiguration(OAuth2ClientContext oauth2ClientContext, UserRepository userRepository,
            BoardService boardService)
    {
        this.oauth2ClientContext = oauth2ClientContext;
        this.userRepository = userRepository;
        this.boardService = boardService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers("/login/**").anonymous()
            .antMatchers("/").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().hasRole("USER")
            .and().addFilterBefore(filter(), BasicAuthenticationFilter.class);

        http.headers().cacheControl().disable();
        // @formatter:on
    }

    private Filter filter()
    {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(filter(google(), "/login/google"));
        filters.add(filter(discord(), "/login/discord"));
        filter.setFilters(filters);
        return filter;
    }

    private Filter filter(ClientResources client, String path)
    {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);

        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);

        ClientHttpRequestFactory requestFactory = client.getRequestFactory();
        if (requestFactory != null)
        {
            AuthorizationCodeAccessTokenProvider authAccessProvider = new AuthorizationCodeAccessTokenProvider();
            authAccessProvider.setRequestFactory(requestFactory);

            AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(
                    Collections.singletonList(authAccessProvider));
            template.setAccessTokenProvider(accessTokenProvider);

            template.setRequestFactory(requestFactory);
        }

        filter.setRestTemplate(template);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
                client.getClient().getClientId());
        tokenServices.setPrincipalExtractor(client.getPrincipalExtractor());
        tokenServices.setAuthoritiesExtractor(client.getAuthoritiesExtractor());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google()
    {
        ClientResources resources = new ClientResources();
        resources.setFunctionGetId(map ->
        {
            boolean verified = (boolean) map.get("email_verified");
            if (!verified)
            {
                return null;
            }
            String sub = (String) map.get("sub");
            if (sub == null || sub.trim().isEmpty())
            {
                return null;
            }
            return HashHelper.md5("google:" + sub);
        });
        resources.setPrincipalExtractor(map ->
        {
            String hash = resources.getFunctionGetId().apply(map);
            if (hash == null)
            {
                return null;
            }
            User user = this.userRepository.findUserByHash(hash);
            if (user == null)
            {
                user = new User(hash);
            }
            user.setLastLogin(ZonedDateTime.now());
            user.getRoles().add(UserRole.ROLE_USER);
            this.userRepository.save(user);
            if (user.getBoard() == null)
            {
                user.setBoard(this.boardService.createBoard(user));
                this.userRepository.save(user);
            }
            return user;
        });
        resources.setAuthoritiesExtractor(authorizationExtractor(resources));
        return resources;
    }

    @Bean
    @ConfigurationProperties("discord")
    public ClientResources discord()
    {
        ClientResources resources = new ClientResources();
        resources.setFunctionGetId(map ->
        {
            boolean verified = (boolean) map.get("verified");
            if (!verified)
            {
                return null;
            }
            String id = (String) map.get("id");
            if (id == null || id.trim().isEmpty())
            {
                return null;
            }
            return HashHelper.md5("discord:" + id);
        });
        resources.setPrincipalExtractor(map ->
        {
            String hash = resources.getFunctionGetId().apply(map);
            if (hash == null)
            {
                return null;
            }
            User user = this.userRepository.findUserByHash(hash);
            if (user == null)
            {
                user = new User(hash);
            }
            user.setLastLogin(ZonedDateTime.now());
            user.getRoles().add(UserRole.ROLE_USER);
            this.userRepository.save(user);
            if (user.getBoard() == null)
            {
                user.setBoard(this.boardService.createBoard(user));
                this.userRepository.save(user);
            }
            return user;
        });
        resources.setAuthoritiesExtractor(authorizationExtractor(resources));
        resources.setRequestFactory(new DiscordHttpRequestFactory());
        return resources;
    }

    private AuthoritiesExtractor authorizationExtractor(ClientResources resources)
    {
        return map ->
        {
            String hash = resources.getFunctionGetId().apply(map);
            if (hash == null)
            {
                return null;
            }
            User user = this.userRepository.findUserByHash(hash);
            if (user == null)
            {
                return Collections.emptyList();
            }
            return AuthorityUtils.createAuthorityList(user.getRoles().stream().map(Enum::name).toArray(String[]::new));
        };
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter)
    {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}