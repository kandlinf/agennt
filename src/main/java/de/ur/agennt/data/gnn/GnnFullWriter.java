package de.ur.agennt.data.gnn;

import de.ur.agennt.entity.gnn.Cluster;
import de.ur.agennt.entity.gnn.FullGnn;
import de.ur.agennt.entity.gnn.Neighborhood;
import de.ur.agennt.entity.gnn.Pfam;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;

public class GnnFullWriter {
    private FullGnn fullGnn;

    public GnnFullWriter(FullGnn fullGnn) {
        this.fullGnn = fullGnn;
    }

    public void write(File outputFile) throws FileNotFoundException, XMLStreamException {
        GnnEventWriter eventWriter = new GnnEventWriter(outputFile);
        eventWriter.add(fullGnn.getGnn());
        for(Pfam pfam : fullGnn.getPfamMap().values()) {
            eventWriter.add(pfam);
        }
        for(Cluster cluster : fullGnn.getClusterMap().values()) {
            eventWriter.add(cluster);
        }
        for(Neighborhood neighborhood : fullGnn.getClusterNeighborhoodMap().values()) {
            eventWriter.add(neighborhood);
        }
        eventWriter.close();
    }

}
