package com.iuni.data.avro.server;

import org.junit.*;

public class SimpleServerTest {

    private static Server httpServer, nettyServer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
//        httpServer = new HttpServer();
//        nettyServer = new NettyServer();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        httpServer = null;
        nettyServer = null;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRespond() throws Exception {

    }
}