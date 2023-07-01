public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int acc = 0;
        while (x < 10) {
            System.out.print(acc + " ");
            x = x + 1;
            acc += x;
        }
    }
}
