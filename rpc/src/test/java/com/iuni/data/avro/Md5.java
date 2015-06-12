package com.iuni.data.avro;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@org.apache.avro.specific.FixedSize(16)


@org.apache.avro.specific.AvroGenerated


public class Md5 extends org.apache.avro.specific.SpecificFixed {

    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
            "{\"type\":\"fixed\",\"name\":\"Md5\",\"namespace\":\"com.yqu.avro\",\"size\":16}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    public Md5() {
        super();
    }

    public Md5(byte[] bytes) {
        super(bytes);
    }

}

