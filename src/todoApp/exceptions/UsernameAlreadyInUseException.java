package todoApp.exceptions;

public class UsernameAlreadyInUseException extends Exception {
    public UsernameAlreadyInUseException(String s){
        super(s);
    }
}
