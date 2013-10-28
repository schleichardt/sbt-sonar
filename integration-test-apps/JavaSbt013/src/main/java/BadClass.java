public class BadClass {
    public static void main(String[] args) {
        try {
            System.out.println(3);
        } catch (Throwable throwable) {
            //ignore
        }
    }
}
