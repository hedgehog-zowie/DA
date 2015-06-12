package comliuni.data.flume;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {
    public static void main(String args[]){
        String str = "a b  c";
        String[] strs = str.split("\\s+");
        System.out.println("strs.length" + strs.length);
        System.out.println(strs[0]);
        System.out.println(strs[1]);
        System.out.println(strs[2]);
    }
}
