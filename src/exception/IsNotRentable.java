package exception;

public class IsNotRentable extends RuntimeException {
    public IsNotRentable(String message) {
        super(message);
    }
}
