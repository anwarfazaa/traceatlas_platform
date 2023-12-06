package org.traceatlas.platform.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RethinkDbConfiguration {

    @Value("${spring.data.rethinkdb.host}")
    private String host;

    @Value("${spring.data.rethinkdb.port}")
    private int port;

    @Value("${spring.data.rethinkdb.database}")
    private String database;

    @Value("${spring.data.rethinkdb.username}")
    private String username;

    @Value("${spring.data.rethinkdb.password}")
    private String password;

    public String getHost() { return host; }

    public int getPort() { return port; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getDatabase() { return  database; }
}
