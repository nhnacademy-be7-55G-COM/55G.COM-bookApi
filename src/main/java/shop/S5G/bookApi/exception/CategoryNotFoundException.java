package shop.S5G.bookApi.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){}
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
