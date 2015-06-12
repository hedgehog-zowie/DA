package com.iuni.data.avro;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@org.apache.avro.specific.AvroGenerated
public class TestAllType extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse(
            "{\"type\":\"record\",\"name\":\"Test\",\"namespace\":\"com.iuni.data.avro.common\",\"fields\":" +
                    "[{\"name\":\"stringVar\",\"type\":\"string\"}," +
                    "{\"name\":\"bytesVar\",\"type\":[\"bytes\",\"null\"]}," +
                    "{\"name\":\"booleanVar\",\"type\":\"boolean\"}," +
                    "{\"name\":\"intVar\",\"type\":\"int\",\"order\":\"descending\"}," +
                    "{\"name\":\"longVar\",\"type\":[\"long\",\"null\"]}," +
                    "{\"name\":\"floatVar\",\"type\":\"float\"}," +
                    "{\"name\":\"doubleVar\",\"type\":\"double\"}," +
                    "{\"name\":\"enumVar\",\"type\":{\"type\":\"enum\",\"name\":\"Suit\",\"symbols\":[\"SPADES\",\"HEARTS\",\"DIAMONDS\",\"CLUBS\"]}}," +
                    "{\"name\":\"strArrayVar\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}," +
                    "{\"name\":\"intArrayVar\",\"type\":{\"type\":\"array\",\"items\":\"int\"}}," +
                    "{\"name\":\"mapVar\",\"type\":{\"type\":\"map\",\"values\":\"long\"}}," +
                    "{\"name\":\"fixedVar\",\"type\":{\"type\":\"fixed\",\"name\":\"Md5\",\"size\":16}}]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    @Deprecated
    public CharSequence stringVar;
    @Deprecated
    public java.nio.ByteBuffer bytesVar;
    @Deprecated
    public boolean booleanVar;
    @Deprecated
    public int intVar;
    @Deprecated
    public Long longVar;
    @Deprecated
    public float floatVar;
    @Deprecated
    public double doubleVar;
    @Deprecated
    public Suit enumVar;
    @Deprecated
    public java.util.List<CharSequence> strArrayVar;
    @Deprecated
    public java.util.List<Integer> intArrayVar;
    @Deprecated
    public java.util.Map<CharSequence, Long> mapVar;
    @Deprecated
    public Md5 fixedVar;

    public TestAllType() {
    }

    public TestAllType(CharSequence stringVar, java.nio.ByteBuffer bytesVar, Boolean booleanVar, Integer intVar, Long longVar, Float floatVar, Double doubleVar, Suit enumVar, java.util.List<CharSequence> strArrayVar, java.util.List<Integer> intArrayVar, java.util.Map<CharSequence, Long> mapVar, Md5 fixedVar) {
        this.stringVar = stringVar;
        this.bytesVar = bytesVar;
        this.booleanVar = booleanVar;
        this.intVar = intVar;
        this.longVar = longVar;
        this.floatVar = floatVar;
        this.doubleVar = doubleVar;
        this.enumVar = enumVar;
        this.strArrayVar = strArrayVar;
        this.intArrayVar = intArrayVar;
        this.mapVar = mapVar;
        this.fixedVar = fixedVar;
    }

    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }

    // Used by DatumWriter.  Applications should not call.
    public Object get(int field$) {
        switch (field$) {
            case 0:
                return stringVar;
            case 1:
                return bytesVar;
            case 2:
                return booleanVar;
            case 3:
                return intVar;
            case 4:
                return longVar;
            case 5:
                return floatVar;
            case 6:
                return doubleVar;
            case 7:
                return enumVar;
            case 8:
                return strArrayVar;
            case 9:
                return intArrayVar;
            case 10:
                return mapVar;
            case 11:
                return fixedVar;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    public void put(int field$, Object value$) {
        switch (field$) {
            case 0:
                stringVar = (CharSequence) value$;
                break;
            case 1:
                bytesVar = (java.nio.ByteBuffer) value$;
                break;
            case 2:
                booleanVar = (Boolean) value$;
                break;
            case 3:
                intVar = (Integer) value$;
                break;
            case 4:
                longVar = (Long) value$;
                break;
            case 5:
                floatVar = (Float) value$;
                break;
            case 6:
                doubleVar = (Double) value$;
                break;
            case 7:
                enumVar = (Suit) value$;
                break;
            case 8:
                strArrayVar = (java.util.List<CharSequence>) value$;
                break;
            case 9:
                intArrayVar = (java.util.List<Integer>) value$;
                break;
            case 10:
                mapVar = (java.util.Map<CharSequence, Long>) value$;
                break;
            case 11:
                fixedVar = (Md5) value$;
                break;
            default:
                throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    public CharSequence getStringVar() {
        return stringVar;
    }

    public void setStringVar(CharSequence value) {
        this.stringVar = value;
    }

    public java.nio.ByteBuffer getBytesVar() {
        return bytesVar;
    }

    public void setBytesVar(java.nio.ByteBuffer value) {
        this.bytesVar = value;
    }

    public Boolean getBooleanVar() {
        return booleanVar;
    }

    public void setBooleanVar(Boolean value) {
        this.booleanVar = value;
    }

    public Integer getIntVar() {
        return intVar;
    }

    public void setIntVar(Integer value) {
        this.intVar = value;
    }

    public Long getLongVar() {
        return longVar;
    }

    public void setLongVar(Long value) {
        this.longVar = value;
    }

    public Float getFloatVar() {
        return floatVar;
    }

    public void setFloatVar(Float value) {
        this.floatVar = value;
    }

    public Double getDoubleVar() {
        return doubleVar;
    }

    public void setDoubleVar(Double value) {
        this.doubleVar = value;
    }

    public Suit getEnumVar() {
        return enumVar;
    }

    public void setEnumVar(Suit value) {
        this.enumVar = value;
    }

    public java.util.List<CharSequence> getStrArrayVar() {
        return strArrayVar;
    }

    public void setStrArrayVar(java.util.List<CharSequence> value) {
        this.strArrayVar = value;
    }

    public java.util.List<Integer> getIntArrayVar() {
        return intArrayVar;
    }

    public void setIntArrayVar(java.util.List<Integer> value) {
        this.intArrayVar = value;
    }

    public java.util.Map<CharSequence, Long> getMapVar() {
        return mapVar;
    }

    public void setMapVar(java.util.Map<CharSequence, Long> value) {
        this.mapVar = value;
    }

    public Md5 getFixedVar() {
        return fixedVar;
    }

    public void setFixedVar(Md5 value) {
        this.fixedVar = value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Builder other) {
        return new Builder(other);
    }

    public static Builder newBuilder(TestAllType other) {
        return new Builder(other);
    }

    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestAllType>
            implements org.apache.avro.data.RecordBuilder<TestAllType> {
        private CharSequence stringVar;
        private java.nio.ByteBuffer bytesVar;
        private boolean booleanVar;
        private int intVar;
        private Long longVar;
        private float floatVar;
        private double doubleVar;
        private Suit enumVar;
        private java.util.List<CharSequence> strArrayVar;
        private java.util.List<Integer> intArrayVar;
        private java.util.Map<CharSequence, Long> mapVar;
        private Md5 fixedVar;

        private Builder() {
            super(TestAllType.SCHEMA$);
        }

        private Builder(Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.stringVar)) {
                this.stringVar = data().deepCopy(fields()[0].schema(), other.stringVar);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.bytesVar)) {
                this.bytesVar = data().deepCopy(fields()[1].schema(), other.bytesVar);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.booleanVar)) {
                this.booleanVar = data().deepCopy(fields()[2].schema(), other.booleanVar);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.intVar)) {
                this.intVar = data().deepCopy(fields()[3].schema(), other.intVar);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.longVar)) {
                this.longVar = data().deepCopy(fields()[4].schema(), other.longVar);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.floatVar)) {
                this.floatVar = data().deepCopy(fields()[5].schema(), other.floatVar);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.doubleVar)) {
                this.doubleVar = data().deepCopy(fields()[6].schema(), other.doubleVar);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.enumVar)) {
                this.enumVar = data().deepCopy(fields()[7].schema(), other.enumVar);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.strArrayVar)) {
                this.strArrayVar = data().deepCopy(fields()[8].schema(), other.strArrayVar);
                fieldSetFlags()[8] = true;
            }
            if (isValidValue(fields()[9], other.intArrayVar)) {
                this.intArrayVar = data().deepCopy(fields()[9].schema(), other.intArrayVar);
                fieldSetFlags()[9] = true;
            }
            if (isValidValue(fields()[10], other.mapVar)) {
                this.mapVar = data().deepCopy(fields()[10].schema(), other.mapVar);
                fieldSetFlags()[10] = true;
            }
            if (isValidValue(fields()[11], other.fixedVar)) {
                this.fixedVar = data().deepCopy(fields()[11].schema(), other.fixedVar);
                fieldSetFlags()[11] = true;
            }
        }

        private Builder(TestAllType other) {
            super(TestAllType.SCHEMA$);
            if (isValidValue(fields()[0], other.stringVar)) {
                this.stringVar = data().deepCopy(fields()[0].schema(), other.stringVar);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.bytesVar)) {
                this.bytesVar = data().deepCopy(fields()[1].schema(), other.bytesVar);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.booleanVar)) {
                this.booleanVar = data().deepCopy(fields()[2].schema(), other.booleanVar);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.intVar)) {
                this.intVar = data().deepCopy(fields()[3].schema(), other.intVar);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.longVar)) {
                this.longVar = data().deepCopy(fields()[4].schema(), other.longVar);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.floatVar)) {
                this.floatVar = data().deepCopy(fields()[5].schema(), other.floatVar);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.doubleVar)) {
                this.doubleVar = data().deepCopy(fields()[6].schema(), other.doubleVar);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.enumVar)) {
                this.enumVar = data().deepCopy(fields()[7].schema(), other.enumVar);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.strArrayVar)) {
                this.strArrayVar = data().deepCopy(fields()[8].schema(), other.strArrayVar);
                fieldSetFlags()[8] = true;
            }
            if (isValidValue(fields()[9], other.intArrayVar)) {
                this.intArrayVar = data().deepCopy(fields()[9].schema(), other.intArrayVar);
                fieldSetFlags()[9] = true;
            }
            if (isValidValue(fields()[10], other.mapVar)) {
                this.mapVar = data().deepCopy(fields()[10].schema(), other.mapVar);
                fieldSetFlags()[10] = true;
            }
            if (isValidValue(fields()[11], other.fixedVar)) {
                this.fixedVar = data().deepCopy(fields()[11].schema(), other.fixedVar);
                fieldSetFlags()[11] = true;
            }
        }

        public CharSequence getStringVar() {
            return stringVar;
        }

        public Builder setStringVar(CharSequence value) {
            validate(fields()[0], value);
            this.stringVar = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        public boolean hasStringVar() {
            return fieldSetFlags()[0];
        }

        public Builder clearStringVar() {
            stringVar = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        public java.nio.ByteBuffer getBytesVar() {
            return bytesVar;
        }

        public Builder setBytesVar(java.nio.ByteBuffer value) {
            validate(fields()[1], value);
            this.bytesVar = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        public boolean hasBytesVar() {
            return fieldSetFlags()[1];
        }

        public Builder clearBytesVar() {
            bytesVar = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        public Boolean getBooleanVar() {
            return booleanVar;
        }

        public Builder setBooleanVar(boolean value) {
            validate(fields()[2], value);
            this.booleanVar = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        public boolean hasBooleanVar() {
            return fieldSetFlags()[2];
        }

        public Builder clearBooleanVar() {
            fieldSetFlags()[2] = false;
            return this;
        }

        public Integer getIntVar() {
            return intVar;
        }

        public Builder setIntVar(int value) {
            validate(fields()[3], value);
            this.intVar = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        public boolean hasIntVar() {
            return fieldSetFlags()[3];
        }

        public Builder clearIntVar() {
            fieldSetFlags()[3] = false;
            return this;
        }

        public Long getLongVar() {
            return longVar;
        }

        public Builder setLongVar(Long value) {
            validate(fields()[4], value);
            this.longVar = value;
            fieldSetFlags()[4] = true;
            return this;
        }

        public boolean hasLongVar() {
            return fieldSetFlags()[4];
        }

        public Builder clearLongVar() {
            longVar = null;
            fieldSetFlags()[4] = false;
            return this;
        }

        public Float getFloatVar() {
            return floatVar;
        }

        public Builder setFloatVar(float value) {
            validate(fields()[5], value);
            this.floatVar = value;
            fieldSetFlags()[5] = true;
            return this;
        }

        public boolean hasFloatVar() {
            return fieldSetFlags()[5];
        }

        public Builder clearFloatVar() {
            fieldSetFlags()[5] = false;
            return this;
        }

        public Double getDoubleVar() {
            return doubleVar;
        }

        public Builder setDoubleVar(double value) {
            validate(fields()[6], value);
            this.doubleVar = value;
            fieldSetFlags()[6] = true;
            return this;
        }

        public boolean hasDoubleVar() {
            return fieldSetFlags()[6];
        }

        public Builder clearDoubleVar() {
            fieldSetFlags()[6] = false;
            return this;
        }

        public Suit getEnumVar() {
            return enumVar;
        }

        public Builder setEnumVar(Suit value) {
            validate(fields()[7], value);
            this.enumVar = value;
            fieldSetFlags()[7] = true;
            return this;
        }

        public boolean hasEnumVar() {
            return fieldSetFlags()[7];
        }

        public Builder clearEnumVar() {
            enumVar = null;
            fieldSetFlags()[7] = false;
            return this;
        }

        public java.util.List<CharSequence> getStrArrayVar() {
            return strArrayVar;
        }

        public Builder setStrArrayVar(java.util.List<CharSequence> value) {
            validate(fields()[8], value);
            this.strArrayVar = value;
            fieldSetFlags()[8] = true;
            return this;
        }

        public boolean hasStrArrayVar() {
            return fieldSetFlags()[8];
        }

        public Builder clearStrArrayVar() {
            strArrayVar = null;
            fieldSetFlags()[8] = false;
            return this;
        }

        public java.util.List<Integer> getIntArrayVar() {
            return intArrayVar;
        }

        public Builder setIntArrayVar(java.util.List<Integer> value) {
            validate(fields()[9], value);
            this.intArrayVar = value;
            fieldSetFlags()[9] = true;
            return this;
        }

        public boolean hasIntArrayVar() {
            return fieldSetFlags()[9];
        }

        public Builder clearIntArrayVar() {
            intArrayVar = null;
            fieldSetFlags()[9] = false;
            return this;
        }

        public java.util.Map<CharSequence, Long> getMapVar() {
            return mapVar;
        }

        public Builder setMapVar(java.util.Map<CharSequence, Long> value) {
            validate(fields()[10], value);
            this.mapVar = value;
            fieldSetFlags()[10] = true;
            return this;
        }

        public boolean hasMapVar() {
            return fieldSetFlags()[10];
        }

        public Builder clearMapVar() {
            mapVar = null;
            fieldSetFlags()[10] = false;
            return this;
        }

        public Md5 getFixedVar() {
            return fixedVar;
        }

        public Builder setFixedVar(Md5 value) {
            validate(fields()[11], value);
            this.fixedVar = value;
            fieldSetFlags()[11] = true;
            return this;
        }

        public boolean hasFixedVar() {
            return fieldSetFlags()[11];
        }

        public Builder clearFixedVar() {
            fixedVar = null;
            fieldSetFlags()[11] = false;
            return this;
        }

        @Override
        public TestAllType build() {
            try {
                TestAllType record = new TestAllType();
                record.stringVar = fieldSetFlags()[0] ? this.stringVar : (CharSequence) defaultValue(fields()[0]);
                record.bytesVar = fieldSetFlags()[1] ? this.bytesVar : (java.nio.ByteBuffer) defaultValue(fields()[1]);
                record.booleanVar = fieldSetFlags()[2] ? this.booleanVar : (Boolean) defaultValue(fields()[2]);
                record.intVar = fieldSetFlags()[3] ? this.intVar : (Integer) defaultValue(fields()[3]);
                record.longVar = fieldSetFlags()[4] ? this.longVar : (Long) defaultValue(fields()[4]);
                record.floatVar = fieldSetFlags()[5] ? this.floatVar : (Float) defaultValue(fields()[5]);
                record.doubleVar = fieldSetFlags()[6] ? this.doubleVar : (Double) defaultValue(fields()[6]);
                record.enumVar = fieldSetFlags()[7] ? this.enumVar : (Suit) defaultValue(fields()[7]);
                record.strArrayVar = fieldSetFlags()[8] ? this.strArrayVar : (java.util.List<CharSequence>) defaultValue(fields()[8]);
                record.intArrayVar = fieldSetFlags()[9] ? this.intArrayVar : (java.util.List<Integer>) defaultValue(fields()[9]);
                record.mapVar = fieldSetFlags()[10] ? this.mapVar : (java.util.Map<CharSequence, Long>) defaultValue(fields()[10]);
                record.fixedVar = fieldSetFlags()[11] ? this.fixedVar : (Md5) defaultValue(fields()[11]);
                return record;
            } catch (Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }
}
