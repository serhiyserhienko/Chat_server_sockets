package server;

public class ChatServerException extends Exception {

    private String message;

    public ChatServerException(String message) {
        this.message = message;
    }

    @Override
    public void printStackTrace(){
        System.err.print("Exception happened: " + message);
    }
}
