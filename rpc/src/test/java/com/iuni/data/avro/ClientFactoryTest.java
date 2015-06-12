package com.iuni.data.avro;

import com.iuni.data.avro.client.Client;
import org.apache.avro.Protocol;
import org.junit.*;

public class ClientFactoryTest {

    private final String name_http = "avro http client";
    private final String name_netty = "avro netty client";
    private static Protocol protocol;
    private static final ClientFactory clientFactory = new DefaultClientFactory();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        protocol = ProtocolFactory.create();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateHttp() throws Exception {
        Client httpClient = clientFactory.create(name_http, "http");
        httpClient.getData();
        httpClient.close();
    }

    @Test
    public void testCreateNetty() throws Exception {
        Client nettyClient = clientFactory.create(name_netty, "netty");
        nettyClient.getData();
        nettyClient.close();
    }
}