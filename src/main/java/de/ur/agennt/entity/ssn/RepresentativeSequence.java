package de.ur.agennt.entity.ssn;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeSequence implements SsnEntity {
    private String id;
    private String label;

    private List<String> acc;
    private String clusterSize;

    private List<String> uniprotId;
    private List<String> status;
    private List<String> sequenceLength;
    private List<String> taxonomyId;
    private List<String> gdna;
    private List<String> description;
    private List<String> swissprotDescription;
    private List<String> organism;
    private List<String> domain;
    private List<String> gn;
    private List<String> pfam;
    private List<String> pdb;
    private List<String> ipro;
    private List<String> go;
    private List<String> gi;
    private List<String> hmpBodySite;
    private List<String> hmpOxygen;
    private List<String> efiId;
    private List<String> ec;
    private List<String> phylum;
    private List<String> clazz;
    private List<String> order;
    private List<String> family;
    private List<String> genus;
    private List<String> species;
    private List<String> cazy;

    public RepresentativeSequence(String id, String label) {
        this.id = id;
        this.label = label;
        this.acc = new ArrayList<>();
        this.uniprotId = new ArrayList<>();
        this.status = new ArrayList<>();
        this.sequenceLength = new ArrayList<>();
        this.taxonomyId = new ArrayList<>();
        this.gdna = new ArrayList<>();
        this.description = new ArrayList<>();
        this.swissprotDescription = new ArrayList<>();
        this.organism = new ArrayList<>();
        this.domain = new ArrayList<>();
        this.gn = new ArrayList<>();
        this.pfam = new ArrayList<>();
        this.pdb = new ArrayList<>();
        this.ipro = new ArrayList<>();
        this.go = new ArrayList<>();
        this.gi = new ArrayList<>();
        this.hmpBodySite = new ArrayList<>();
        this.hmpOxygen = new ArrayList<>();
        this.efiId = new ArrayList<>();
        this.ec = new ArrayList<>();
        this.phylum = new ArrayList<>();
        this.clazz = new ArrayList<>();
        this.order = new ArrayList<>();
        this.family = new ArrayList<>();
        this.genus = new ArrayList<>();
        this.species = new ArrayList<>();
        this.cazy = new ArrayList<>();
    }

    public void removeEntry(int i) {
        if(acc.size() > i) acc.remove(i);
        clusterSize = ((Integer)(Integer.parseInt(clusterSize) - 1)).toString();
        if(uniprotId.size() > i) uniprotId.remove(i);
        if(status.size() > i) status.remove(i);
        if(sequenceLength.size() > i) sequenceLength.remove(i);
        if(taxonomyId.size() > i) taxonomyId.remove(i);
        if(gdna.size() > i) gdna.remove(i);
        if(description.size() > i) description.remove(i);
        if(swissprotDescription.size() > i) swissprotDescription.remove(i);
        if(organism.size() > i) organism.remove(i);
        if(domain.size() > i) domain.remove(i);
        if(gn.size() > i) gn.remove(i);
        if(pfam.size() > i)pfam.remove(i);
        if(pdb.size() > i) pdb.remove(i);
        if(ipro.size() > i) ipro.remove(i);
        if(go.size() > i) go.remove(i);
        if(gi.size() > i) gi.remove(i);
        if(hmpBodySite.size() > i) hmpBodySite.remove(i);
        if(hmpOxygen.size() > i) hmpOxygen.remove(i);
        if(efiId.size() > i) efiId.remove(i);
        if(ec.size() > i) ec.remove(i);
        if(phylum.size() > i) phylum.remove(i);
        if(clazz.size() > i) clazz.remove(i);
        if(order.size() > i) order.remove(i);
        if(family.size() > i) family.remove(i);
        if(genus.size() > i) genus.remove(i);
        if(species.size() > i) species.remove(i);
        if(cazy.size() > i) cazy.remove(i);
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getAcc() {
        return acc;
    }

    public String getClusterSize() {
        return clusterSize;
    }

    public List<String> getUniprotId() {
        return uniprotId;
    }

    public List<String> getStatus() {
        return status;
    }

    public List<String> getSequenceLength() {
        return sequenceLength;
    }

    public List<String> getTaxonomyId() {
        return taxonomyId;
    }

    public List<String> getGdna() {
        return gdna;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<String> getSwissprotDescription() {
        return swissprotDescription;
    }

    public List<String> getOrganism() {
        return organism;
    }

    public List<String> getDomain() {
        return domain;
    }

    public List<String> getGn() {
        return gn;
    }

    public List<String> getPfam() {
        return pfam;
    }

    public List<String> getPdb() {
        return pdb;
    }

    public List<String> getIpro() {
        return ipro;
    }

    public List<String> getGo() {
        return go;
    }

    public List<String> getGi() {
        return gi;
    }

    public List<String> getHmpBodySite() {
        return hmpBodySite;
    }

    public List<String> getHmpOxygen() {
        return hmpOxygen;
    }

    public List<String> getEfiId() {
        return efiId;
    }

    public List<String> getEc() {
        return ec;
    }

    public List<String> getPhylum() {
        return phylum;
    }

    public List<String> getClazz() {
        return clazz;
    }

    public List<String> getOrder() {
        return order;
    }

    public List<String> getFamily() {
        return family;
    }

    public List<String> getGenus() {
        return genus;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getCazy() {
        return cazy;
    }

    public void setAcc(List<String> acc) {
        this.acc = acc;
    }

    public void setClusterSize(String clusterSize) {
        this.clusterSize = clusterSize;
    }

    public void setUniprotId(List<String> uniprotId) {
        this.uniprotId = uniprotId;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public void setSequenceLength(List<String> sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public void setTaxonomyId(List<String> taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public void setGdna(List<String> gdna) {
        this.gdna = gdna;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setSwissprotDescription(List<String> swissprotDescription) {
        this.swissprotDescription = swissprotDescription;
    }

    public void setOrganism(List<String> organism) {
        this.organism = organism;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }

    public void setGn(List<String> gn) {
        this.gn = gn;
    }

    public void setPfam(List<String> pfam) {
        this.pfam = pfam;
    }

    public void setPdb(List<String> pdb) {
        this.pdb = pdb;
    }

    public void setIpro(List<String> ipro) {
        this.ipro = ipro;
    }

    public void setGo(List<String> go) {
        this.go = go;
    }

    public void setGi(List<String> gi) {
        this.gi = gi;
    }

    public void setHmpBodySite(List<String> hmpBodySite) {
        this.hmpBodySite = hmpBodySite;
    }

    public void setHmpOxygen(List<String> hmpOxygen) {
        this.hmpOxygen = hmpOxygen;
    }

    public void setEfiId(List<String> efiId) {
        this.efiId = efiId;
    }

    public void setEc(List<String> ec) {
        this.ec = ec;
    }

    public void setPhylum(List<String> phylum) {
        this.phylum = phylum;
    }

    public void setClazz(List<String> clazz) {
        this.clazz = clazz;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public void setFamily(List<String> family) {
        this.family = family;
    }

    public void setGenus(List<String> genus) {
        this.genus = genus;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public void setCazy(List<String> cazy) {
        this.cazy = cazy;
    }
}
