package com.iuni.data.app;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test3 {

    private String par = "par";

    public IMyInner destination() {
        class MyInner implements IMyInner{
            private String label;

            private MyInner() {
                label = par;
            }

            public String readLabel() {
                return label;
            }
        }
        return new MyInner();
    }

    interface IMyInner{

    }

}
