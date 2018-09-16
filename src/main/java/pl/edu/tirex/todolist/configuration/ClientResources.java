package pl.edu.tirex.todolist.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.Map;
import java.util.function.Function;

public class ClientResources
{
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    private PrincipalExtractor principalExtractor;

    private AuthoritiesExtractor authoritiesExtractor;

    private ClientHttpRequestFactory requestFactory;

    private Function<Map<String, Object>, String> functionGetId;

    public AuthorizationCodeResourceDetails getClient()
    {
        return client;
    }

    public ResourceServerProperties getResource()
    {
        return resource;
    }

    public PrincipalExtractor getPrincipalExtractor()
    {
        return principalExtractor;
    }

    public void setPrincipalExtractor(PrincipalExtractor principalExtractor)
    {
        this.principalExtractor = principalExtractor;
    }

    public AuthoritiesExtractor getAuthoritiesExtractor()
    {
        return authoritiesExtractor;
    }

    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor)
    {
        this.authoritiesExtractor = authoritiesExtractor;
    }

    public ClientHttpRequestFactory getRequestFactory()
    {
        return requestFactory;
    }

    public void setRequestFactory(ClientHttpRequestFactory requestFactory)
    {
        this.requestFactory = requestFactory;
    }

    public Function<Map<String, Object>, String> getFunctionGetId()
    {
        return functionGetId;
    }

    public void setFunctionGetId(Function<Map<String, Object>, String> functionGetId)
    {
        this.functionGetId = functionGetId;
    }
}