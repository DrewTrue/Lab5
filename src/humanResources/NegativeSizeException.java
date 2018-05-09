package humanResources;

public class NegativeSizeException extends NegativeArraySizeException {
    public NegativeSizeException(){
        super("The size is negative!");
    }

    public NegativeSizeException(String message){
        super(message);
    }

    public String getMessageException(){
        return super.getMessage();
    }
}
