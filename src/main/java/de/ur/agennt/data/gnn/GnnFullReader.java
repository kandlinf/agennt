package de.ur.agennt.data.gnn;

import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.gnn.FullGnn;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GnnFullReader {
    private GnnEventReader reader;
    private FullGnn fullGnn;

    public GnnFullReader(File gnnFile, Integer neighborhoodSize, Integer coocurrence) throws FileNotFoundException, XMLStreamException {
        reader = new GnnEventReader(gnnFile);
        fullGnn = new FullGnn();
        fullGnn.setNeighborhoodSize(neighborhoodSize);
        fullGnn.setCoocurrence(coocurrence);
    }

    public FullGnn parse() throws XMLStreamException {
        while (reader.hasNext()) {
            GnnEntity entity = reader.next();
            if(entity instanceof Gnn) {
                fullGnn.setGnn((Gnn) entity);
            } else if(entity instanceof Cluster) {
                Cluster cluster = (Cluster) entity;
                fullGnn.getClusterMap().put(cluster.getId(), cluster);
            } else if(entity instanceof Neighborhood) {
                Neighborhood neighborhood = (Neighborhood) entity;
                fullGnn.getClusterNeighborhoodMap().put(neighborhood.getTarget(),neighborhood);
                if(!fullGnn.getPfamNeighborhoodMap().containsKey(neighborhood.getSource())) {
                    fullGnn.getPfamNeighborhoodMap().put(neighborhood.getSource(), new ArrayList<>());
                }
                fullGnn.getPfamNeighborhoodMap().get(neighborhood.getSource()).add(neighborhood);
            } else if(entity instanceof Pfam) {
                Pfam pfam = (Pfam) entity;
                fullGnn.getPfamMap().put(pfam.getId(), pfam);
            }
        }
    reader.close();
    return fullGnn;
    }

}
