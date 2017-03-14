package XML;

import Domain.Position;
import Repository.AbstractRepository;
import Validator.IValidator;
import Validator.RepositoryException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class AbstractSaxXMLPositionRepository extends AbstractRepository<Position, Integer> {
    private String fileName;

    public AbstractSaxXMLPositionRepository(IValidator<Position> val, String fileName) {
        super(val);
        this.fileName = fileName;
        readFromXML();
    }

    protected abstract SAXParser getParser() throws SAXException, ParserConfigurationException;

    protected void readFromXML() {
        try {
            SAXParser parser = getParser();
            PositionHandler handler = new PositionHandler();
            File file = new File(fileName);
            parser.parse(file, handler);
            List<Position>  result = handler.getPositions();
            for(Position p : result)
            {
                super.add(p);
            }
        } catch (SAXException|IOException |ParserConfigurationException e) {
            throw new RepositoryException(e);
        }
    }

    protected void writeToFile(){
        try(PrintWriter pw=new PrintWriter(fileName)){
            pw.println("<?xml version=\"1.0\"?>");
            pw.println(startRootElement());
            pw.println(writeEntities());
            pw.println(endRootElement());
        }catch (IOException ex){
            throw new RepositoryException(ex);
        }

    }

    protected String startRootElement(){
        return "<positions>";
    }

    protected String writeEntities(){
        StringBuffer result=new StringBuffer();
        for(Position p : this.getAll()){
            String mes=String.format("<position id=\"%d\">\n<name>%s</name>\n<type>%s</type>\n</position>\n", p.getId(), p.getName(), p.getType());
            result.append(mes);
        }
        return result.toString();
    }

    protected String endRootElement(){
        return "</positions>";
    }

    @Override
    public void add(Position entity) {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        writeToFile();
    }

    public void update(Position p) {
        super.update(p);
        writeToFile();
    }
}
