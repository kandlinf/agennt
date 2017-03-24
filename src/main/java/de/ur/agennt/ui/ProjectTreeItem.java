package de.ur.agennt.ui;

import java.io.File;

public class ProjectTreeItem {

    public enum ItemType {PROJECT, SSN, GNN, FILTERED_SSN, FILTERED_GNN}

    private String info;
    private File file;
    private ItemType itemType;

    public ProjectTreeItem(String info) {
        this.info = info;
        this.itemType = ItemType.PROJECT;
    }

    public ProjectTreeItem(String info, ItemType itemType) {
        this.info = info;
        this.itemType = itemType;
    }

    public ProjectTreeItem(String info, ItemType itemType, File file) {
        this.file = file;
        this.info = info;
        this.itemType = itemType;
    }

    public File getFile() {
        return file;
    }


    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return info;
    }
}
