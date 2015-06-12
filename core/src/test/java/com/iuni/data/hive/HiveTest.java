package com.iuni.data.hive;

import com.iuni.data.exceptions.IuniDADateFormatException;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class HiveTest {

    private static HiveConnector connector;
    private static HiveOperator operator;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        connector = new HiveConnector("jdbc:hive2://18.8.0.245:10000/default", "hive", "");
        operator = new HiveOperator(connector);
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

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Test
    public void testAddPartition() throws IuniDADateFormatException {
        operator.parseAndAddPartitions("iuniLog", "20150122000000", "20150123000000");
    }

    @Test
    public void testDelPartition() throws IuniDADateFormatException {
        operator.parseAndDelPartitions("iuniLog", "20150120000000", "20150121000000");
    }

}
