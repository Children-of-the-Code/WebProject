package w1d3.ServerSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class TedsServer {

    /*
    what should a web server do?
     */
    /*
    it should host web pages?
    not all web servers host pages (some web servers just exist to shuffle data around internally)
     */

    /*
    we should have something that waits for a request, and upon recieving some request,
    returns a meaningful response

    we don't know when we will get info, but when we do, we need to know what to do with it
     */
//    what port does the server exist on?
    int port;
//    how long should a server wait before it gives up?
    int timeoutMS;
//    how big should the buffer for storing incoming information be?
    int buffersize;
//    what message might we send back as a response?
    String msg;

    public TedsServer(int port, String msg){
        this.port = port;
        this.msg = msg;
        timeoutMS = 1000;
        buffersize = 1000;
    }

    public void start(){
        /*
        i also know that we need some java object that is able to make connections over the web

        i know that the server needs to be always waiting for something to come in..
        which is to say i know that we need a loop
         */
        try{
//            we need to try catch this in case there is some issue with setting up a server
            ServerSocket server = new ServerSocket(port);
//            a loop that is always waiting for some web request to come in
            while(true){
                try(Socket socket = server.accept()){
//                    if we got a connection, we should get input in some way
                    try(InputStream input = socket.getInputStream()){
//                        likewise, we should prepare some way of producing output
                        OutputStream output = socket.getOutputStream();
//                        the request will come to us as bytes, small numbers
                        byte[] buffer = new byte[buffersize];
//                        input.read will fill the buffer with input information, and fill total with its length
                        int total = input.read(buffer);
//                        convert the buffer to a string now that we have both buffer and its size, start from 0
                        String request = new String(Arrays.copyOfRange(buffer, 0, total));
                        System.out.println(request);
                        /*
                        status responses
                        404 not found
                        500 internal error
                        200 success
                         */
                        String response = new String("HTTP/1.1 200 OK\r\n\r\n")+new String(msg);
                        output.write(response.getBytes(response));
                    }
                } catch(SocketTimeoutException e){
//
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
