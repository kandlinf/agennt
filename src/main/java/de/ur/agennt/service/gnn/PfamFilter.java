package de.ur.agennt.service.gnn;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.gnn.FullGnn;
import de.ur.agennt.entity.gnn.Neighborhood;
import de.ur.agennt.entity.gnn.Pfam;

import java.io.*;
import java.util.List;
import java.util.Set;

public class PfamFilter {
    private DataFacade dataFacade;
    private Set<String> whiteList;

    public PfamFilter() {
        dataFacade = DataFacade.getInstance();
        whiteList = dataFacade.getPfamWhitelist();
    }

    public PfamFilter(File filter) throws IOException {
        dataFacade = DataFacade.getInstance();
        whiteList = dataFacade.getPfamWhitelist(filter);
    }

    public void writeFilter(File outputFile) throws IOException {
        dataFacade.writePfamWhitelistToFile(whiteList, outputFile);
    }

    public int getWhiteListSize() {
        return whiteList.size();
    }

    public FullGnn filter(FullGnn input) {
        FullGnn result = new FullGnn(input);

        for (String pfamId : input.getPfamMap().keySet()) {
            Pfam pfam = input.getPfamMap().get(pfamId);
            if(!whiteList.contains(pfam.getPfam())) {
                result.getPfamMap().remove(pfamId);
                List<Neighborhood> neighborhoodList =  input.getPfamNeighborhoodMap().get(pfamId);
                for(Neighborhood neighborhood: neighborhoodList) {
                    String clusterId = neighborhood.getTarget();
                    result.getClusterMap().remove(clusterId);
                    result.getClusterNeighborhoodMap().remove(clusterId);
                }
                result.getPfamNeighborhoodMap().remove(pfamId);
            }
        }

        return  result;
    }

}
