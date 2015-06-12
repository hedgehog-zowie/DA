package com.iuni.data.avro;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@org.apache.avro.specific.AvroGenerated
public enum Suit {

    PV, UV, IP, AREA, USERPAGE;

    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
            "{\"type\":\"enum\",\"name\":\"Suit\",\"namespace\":\"com.iuni.data.analyze\",\"symbols\":[\"PV\", \"UV\", \"IP\", \"AREA\", \"USERPAGE\"]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

}
