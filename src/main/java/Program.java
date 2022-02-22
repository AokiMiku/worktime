public class Program {
    public static void main (String[] args) {

        boolean start = false;
        for (String arg : args) {
            if (arg.equals("--start") || arg.equals("-s")) {
                start = true;
                break;
            }
        }
        new GUI(start);
    }
}
