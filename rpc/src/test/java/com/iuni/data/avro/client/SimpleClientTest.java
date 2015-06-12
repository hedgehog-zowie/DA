package com.iuni.data.avro.client;

import org.junit.*;

public class SimpleClientTest {

    private static Client httpAbstractClient, nettyAbstractClient;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

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
    public void testGetDataHttp() throws Exception {
        httpAbstractClient = new SimpleHttpClient();
        httpAbstractClient.getData();
    }

    @Test
    public void testGetDataNetty() throws Exception {
        nettyAbstractClient = new SimpleNettyClient();
        nettyAbstractClient.getData();
    }

    private class Athread implements Runnable{

        @Override
        public void run() {

        }
    }

}