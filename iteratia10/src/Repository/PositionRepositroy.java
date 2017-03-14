package Repository;
import Domain.Position;
import Validator.IValidator;
import Validator.PositionValidator;

// Stocarea datelor
public class PositionRepositroy extends AbstractRepository<Position, Integer>{
    public PositionRepositroy(IValidator<Position> val)
    {
        super(val);
    }

}
