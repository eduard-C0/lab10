package en.ubb.pet_shop.web.repository.file;

import en.ubb.pet_shop.web.domain.BaseEntity;
import en.ubb.pet_shop.web.domain.validators.Validator;
import en.ubb.pet_shop.web.repository.BaseFileRepository;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XmlRepository <ID, T extends BaseEntity<ID>> extends BaseFileRepository<ID, T> {
    public XmlRepository(Validator<T> validator, String fileName, Class<T> classReference) throws SQLException {
        super(validator, fileName, classReference);
    }

    @Override
    public void loadData() throws XmlException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            boolean success = new File(fileName).createNewFile();
            if (success) {
                System.out.println("The file " + fileName + " did not exist and was created instead.");
                // prepare the empty content..
                saveData();
                return;
            }
            Document document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .forEach(node ->
                        Optional.ofNullable(node)
                                .filter(e -> e instanceof Element)
                                .ifPresent(item -> {
                                    NamedNodeMap nodeAttributes = item.getAttributes();
                                    Map<String, String> attributes = IntStream.range(0, nodeAttributes.getLength())
                                            .mapToObj(nodeAttributes::item)
                                            .collect(Collectors.toMap(Node::getNodeName, Node::getNodeValue));
                                    save(loadEntity(attributes));
                                })
            );

        } catch (ParserConfigurationException parserConfigurationException) {
            throw new XmlException("The document builder couldn't be created successfully: " + parserConfigurationException.getMessage(), parserConfigurationException.getCause());
        } catch (SAXException saxException) {
            throw new XmlException("The document couldn't have been parsed successfully: " + saxException.getMessage(), saxException.getCause());
        } catch (IOException ioException) {
            throw new XmlException("The document couldn't have been parsed successfully because of an input/output error: " + ioException.getMessage(), ioException.getCause());
        }
    }

    @Override
    public void saveData() throws XmlException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            document.appendChild(document.createElement("root"));

            elements.values()
                    .forEach(element -> {
                        Element elementDOM = document.createElement("element");
                        element.getAttributes().forEach(elementDOM::setAttribute);
                        document.getDocumentElement().appendChild(elementDOM);
                    });


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(fileName))
            );
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new XmlException("The document builder couldn't be created successfully: " + parserConfigurationException.getMessage(), parserConfigurationException.getCause());
        } catch (TransformerConfigurationException transformerConfigurationException) {
            throw new XmlException("The XML transformer couldn't be created successfully: " + transformerConfigurationException.getMessage(), transformerConfigurationException.getCause());
        } catch (TransformerException transformerException) {
            throw new XmlException("The XML transformer couldn't write to the file successfully: " + transformerException.getMessage(), transformerException.getCause());
        }

    }
}
