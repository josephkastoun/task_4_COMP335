import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import SubClasses.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class XMLParser {


    public static SubClasses.Config parseXML(File inputFile) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();

        Document doc = builder.parse(inputFile);

        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        Config config = new Config(Integer.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("randomSeed").getNodeValue()));

        for (int i = 0; i < nodeList.getLength(); i++){
            Node n = nodeList.item(i);

            switch (n.getNodeName()){
                case "servers":{
                    for (int j = 0; j < n.getChildNodes().getLength(); j++){
                        Node lnode = n.getChildNodes().item(j);

                        if(lnode.getNodeName() == "server"){
                            Server s = new Server(lnode.getAttributes().getNamedItem("type").getNodeValue(),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("limit").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("bootupTime").getNodeValue()),
                                    Float.parseFloat(lnode.getAttributes().getNamedItem("hourlyRate").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("coreCount").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("memory").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("disk").getNodeValue()));
                            config.addServer(s);
                        }
                    }
                }
                case "jobs":{
                    for (int j = 0; j < n.getChildNodes().getLength(); j++){
                        Node lnode = n.getChildNodes().item(j);
                        if(lnode.getNodeName() == "job"){

                            Job x = new Job(
                                    lnode.getAttributes().getNamedItem("type").getNodeValue(),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("minRunTime").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("maxRunTime").getNodeValue()),
                                    Integer.parseInt(lnode.getAttributes().getNamedItem("populationRate").getNodeValue()));
                            config.addJob(x);
                        }
                    }
                }
                case "workload":{
                    if(!n.getNodeName().equals("workload")) continue;
                    Workload w = new Workload(n.getAttributes().getNamedItem("type").getNodeValue(),
                            Integer.parseInt(n.getAttributes().getNamedItem("minLoad").getNodeValue()),
                            Integer.parseInt(n.getAttributes().getNamedItem("maxLoad").getNodeValue()));
                    config.setWorkload(w);

                }

                case "termination":{
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        Node lnode = n.getChildNodes().item(j);
                        if(lnode.getNodeName().equals("condition")){

                            TerminationCondition t = new TerminationCondition(lnode.getAttributes().getNamedItem("type").getNodeValue(), Integer.parseInt(lnode.getAttributes().getNamedItem("value").getNodeValue()));
                            config.addTerminationConditions(t);
                        }
                    }
                }


            }


        }


        return config;

    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        if(args.length == 0)
            throw new RuntimeException("Name of the XML file is required!");

        File inputFile = new File(args[0]);

        Config c = parseXML(inputFile);

        c.printConfig();

    }


}
