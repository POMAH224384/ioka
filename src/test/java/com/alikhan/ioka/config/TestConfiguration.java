package com.alikhan.ioka.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:test-config.properties"})
public interface TestConfiguration extends Config{

    @Key("ui.baseUrl")
    String uiBaseUrl();

    @Key("ui.productsForAllUrl")
    String uiProductsForAllUrl();

    @Key("api.baseUrl")
    String apiBaseUrl();

    @Key("api.key")
    String apiKey();

    @Key("api.timeoutMillis")
    Long apiTimeoutMillis();
}
