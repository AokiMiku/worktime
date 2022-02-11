public class Program {
    public static void main (String[] args) {
        boolean start = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--start") || args[i].equals("-s")) {
                start = true;
            }
        }
        new GUI(start);
    }
}
