package Validator;

/**
 * Created by laura on 10/28/2016.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(Exception e)
    {
        super(e);
    }
    public RepositoryException(String m,Exception e){super(m,e);}
}
