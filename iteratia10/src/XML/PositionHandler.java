package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import Domain.Position;
import Validator.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class PositionHandler extends DefaultHandler {
    private String current;
    private Position position;
    private List<Position> result;

    public PositionHandler() {
        current = null;
        position = null;
        result = null;
    }

    public List<Position> getPositions(){
        return result;
    }

    private int getIntegerNumber(String val){
        try{
            return Integer.parseInt(val);
        }catch (NumberFormatException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String elName=localName;
        if ("".equals(elName))
            elName=qName;
        current=elName;
        if ("positions".equals(current)){
            result=new ArrayList<Position>();
            return;
        }
        if ("position".equals(current)){
            position = new Position();
            String id=attributes.getValue("id");
            position.setId(getIntegerNumber(id));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s=new String(ch,start,length);
        if ("name".equals(current)){
            position.setName(s);
        }
        if ("type".equals(current)){
            position.setType(s.trim());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String elName=localName;
        if ("".equals(elName))
            elName=qName;
        if ("position".equals(elName))
            result.add(position);
        current=null;
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        throw new RepositoryException(e);
    }
}
