package XML;

import Domain.Position;
import Validator.IValidator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import Validator.RepositoryException;
import org.xml.sax.SAXException;

public class SaxSchemaXMLPositionRepository extends AbstractSaxXMLPositionRepository {
    private static final String schemaFile = "Positions.xsd";
    public SaxSchemaXMLPositionRepository(IValidator<Position> val, String fileName) {
        super(val, fileName);
    }

    protected SAXParser getParser() throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        try {
            factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource(schemaFile)}));
        } catch (SAXException e) {
            throw new RepositoryException("Error setting XML Schema "+ e, e);
        }

        SAXParser parser=factory.newSAXParser();
        return parser;
    }

    @Override
    protected String startRootElement() {
        return "<positions xmlns=\"urn:Positions.namespace\"\n" +
                "              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "              xsi:schemaLocation=\"urn:Positions.namespace "+schemaFile+"\">";
    }
}
