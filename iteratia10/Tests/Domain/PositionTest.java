package Domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    Position p = new Position(1, "a", "b");
    @Test
    public void getId() throws Exception {
        assertEquals(1, (int)p.getId());
    }

    @Test
    public void setId() throws Exception {
        p.setId(2);
        assertEquals(2, (int)p.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("a", p.getName());
    }

    @Test
    public void setName() throws Exception {
        p.setName("aa");
        assertEquals("aa", p.getName());
    }

    @Test
    public void getType() throws Exception {
        assertEquals("b", p.getType());
    }

    @Test
    public void setType() throws Exception {
        p.setType("bb");
        assertEquals("bb", p.getType());
    }
}