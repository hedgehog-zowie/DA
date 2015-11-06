package com.iuni.data.app;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test2 {

    public static void main(String args[]){
        System.out.println("=================================");
        StringBuilder SbdAppend = new StringBuilder("sbd");
        System.out.println(SbdAppend);
        testSbdAppend(SbdAppend);
        System.out.println(SbdAppend);

        System.out.println("=================================");
        StringBuilder SbdAppendAfterNew = new StringBuilder("sbd");
        System.out.println(SbdAppendAfterNew);
        testSbdAppendAfterNew(SbdAppendAfterNew);
        System.out.println(SbdAppendAfterNew);

    }

    private static void testSbdAppend(StringBuilder sbd){
        sbd.append("-testSbdAppend");
    }

    private static void testSbdAppendAfterNew(StringBuilder sbd){
        sbd = new StringBuilder("newSbd");
        System.out.println(sbd);
        sbd.append("-testSbdAppendAfterNew");
        System.out.println(sbd);
    }

}
