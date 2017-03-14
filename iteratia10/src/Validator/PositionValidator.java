package Validator;
import Domain.Position;
import Exception.CustomException;

public class PositionValidator implements IValidator<Position> {
    public void validate(Position pos) {
        StringBuilder err = new StringBuilder();

        if(pos.getId() < 0)
            err.append("\nid-ul jobului nu poate fi negativ\n");
        if(pos.getName().isEmpty())
            err.append("\nn-ati introdus nici un nume de job\n");

        if(pos.getType().isEmpty())
            err.append("\nn-ati introdus nici un tip de job\n");

        if(err.length() > 0)
            throw new CustomException(err.toString());
    }
}
