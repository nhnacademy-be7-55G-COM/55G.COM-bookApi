package shop.s5g.bookApi.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){}
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
