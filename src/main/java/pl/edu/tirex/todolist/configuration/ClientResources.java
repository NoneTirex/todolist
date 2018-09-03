package pl.edu.tirex.todolist.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class ClientResources
{
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    private PrincipalExtractor principalExtractor;

    private ClientHttpRequestFactory requestFactory;

    public ClientResources(PrincipalExtractor principalExtractor)
    {
        this.principalExtractor = principalExtractor;
    }

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

    public ClientHttpRequestFactory getRequestFactory()
    {
        return requestFactory;
    }

    public void setRequestFactory(ClientHttpRequestFactory requestFactory)
    {
        this.requestFactory = requestFactory;
    }
}