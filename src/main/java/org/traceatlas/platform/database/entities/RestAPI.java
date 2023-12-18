package org.traceatlas.platform.database.entities;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
public class RestAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String requestMethod;

    @Column
    private String requestURL;

    @Column
    private String requestPayloadType;

    @Column
    private String getRequestPayloadContent;


}
