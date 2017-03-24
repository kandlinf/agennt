package de.ur.agennt.entity.colored;

import de.ur.agennt.entity.ssn.SsnEntity;

import java.util.ArrayList;
import java.util.List;

public class ColoredSequence implements ColoredSsnEntity {
    private String id;
    private String label;

    private String uniprotId;
    private String status;
    private String supercluster;
    private String sequenceLength;
    private String taxonomyId;
    private String gdna;
    private String description;
    private String swissprotDescription;
    private String organism;
    private String domain;
    private String gn;
    private List<String> pfam;
    private List<String> pdb;
    private List<String> ipro;
    private List<String> go;
    private List<String> gi;
    private List<String> hmpBodySite;
    private String hmpOxygen;
    private String efiId;
    private String ec;
    private String phylum;
    private String clazz;
    private String order;
    private String family;
    private String genus;
    private String species;
    private List<String> cazy;

    public ColoredSequence(String id, String label) {
        this.id = id;
        this.label = label;
        this.pfam = new ArrayList<>();
        this.pdb  = new ArrayList<>();
        this.ipro  = new ArrayList<>();
        this.go  = new ArrayList<>();
        this.gi = new ArrayList<>();
        this.hmpBodySite = new ArrayList<>();
        this.cazy = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getUniprotId() {
        return uniprotId;
    }

    public String getStatus() {
        return status;
    }

    public String getSequenceLength() {
        return sequenceLength;
    }

    public String getTaxonomyId() {
        return taxonomyId;
    }

    public String getGdna() {
        return gdna;
    }

    public String getDescription() {
        return description;
    }

    public String getSwissprotDescription() {
        return swissprotDescription;
    }

    public String getOrganism() {
        return organism;
    }

    public String getDomain() {
        return domain;
    }

    public String getGn() {
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

    public String getHmpOxygen() {
        return hmpOxygen;
    }

    public String getEfiId() {
        return efiId;
    }

    public String getEc() {
        return ec;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getClazz() {
        return clazz;
    }

    public String getOrder() {
        return order;
    }

    public String getFamily() {
        return family;
    }

    public String getGenus() {
        return genus;
    }

    public String getSpecies() {
        return species;
    }

    public List<String> getCazy() {
        return cazy;
    }

    public void setUniprotId(String uniprotId) {
        this.uniprotId = uniprotId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSequenceLength(String sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public void setTaxonomyId(String taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public void setGdna(String gdna) {
        this.gdna = gdna;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSwissprotDescription(String swissprotDescription) {
        this.swissprotDescription = swissprotDescription;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setGn(String gn) {
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

    public void setHmpOxygen(String hmpOxygen) {
        this.hmpOxygen = hmpOxygen;
    }

    public void setEfiId(String efiId) {
        this.efiId = efiId;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setCazy(List<String> cazy) {
        this.cazy = cazy;
    }

    public String getSupercluster() {
        return supercluster;
    }

    public void setSupercluster(String supercluster) {
        this.supercluster = supercluster;
    }
}
