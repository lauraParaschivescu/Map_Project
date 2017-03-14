package Repository;

import Domain.Position;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidatorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import Exception.CustomException;

import javax.annotation.processing.FilerException;

public class FilePositionRepository extends AbstractRepository<Position, Integer> {
    private String fileName;

    public FilePositionRepository(IValidator<Position> val, String fileName) {
        super(val);
        this.fileName = fileName;
        loadFromFile();
    }

    private void loadFromFile() {
        try
        {
            try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
            {
                String linie;
                while((linie = br.readLine()) != null)
                {
                    String tmp[] = linie.split("[|]");
                    if(tmp.length < 3)
                        throw new ValidatorException("nu avem date corecte in fisier\n");

                    super.add(new Position(Integer.parseInt(tmp[0]), tmp[1], tmp[2]) {});
                }
            }
        }
        catch(IOException e)
        {
            throw new RepositoryException(e);
        }
    }

    private void saveToFile() {
        try
        {
            try(PrintWriter pw = new PrintWriter(fileName))
            {
                for(Position p : entities) {
                    pw.println(p.getId() + "|" + p.getName() + "|" + p.getType());
                }
            }
        }
        catch(IOException e)
        {
            throw new RepositoryException(e);
        }
    }

    public void add(Position position) {
        super.add(position);
        saveToFile();
    }

    public void delete(Integer id) {
        super.delete(id);
        saveToFile();
    }

    public void update(Position position)
    {
        super.update(position);
        saveToFile();
    }
}
