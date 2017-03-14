package Repository;
import Domain.Position;
import Validator.IValidator;
import Exception.CustomException;
import Validator.PositionValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionRepositroyTest {
    Position p1 = new Position(11, "a", "b");
    Position p2 = new Position(12, "aa", "bb");
    IValidator val = new PositionValidator();
    PositionRepositroy repo;

    @Before
    public void setUp() throws Exception {
        repo = new PositionRepositroy(val);
        try
        {
            repo.add(p1);
            repo.add(p2);
        } catch(CustomException e)
        {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {
        assertTrue(repo.entities.size() == 2);
    }

    @Test
    public void getItem() throws Exception {
        assertTrue(repo.getItem(11) == p1);
    }

    @Test
    public void delete() throws Exception {
        repo.delete(11);
        assertTrue(repo.entities.size() == 1);
    }

    @Test
    public void update() throws Exception {
        Position newPos = new Position(11, "schimbat", "schimbat");
        repo.update(newPos);
        assertTrue(repo.entities.get(1).getName() == "schimbat");
    }
}