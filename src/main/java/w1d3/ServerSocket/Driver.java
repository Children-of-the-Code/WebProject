package w1d3.ServerSocket;

public class Driver {

    public static void main(String[] args) {
        String msg = "Hello server!";
        TedsServer server = new TedsServer(5000, msg);
        server.start();
    }

}
