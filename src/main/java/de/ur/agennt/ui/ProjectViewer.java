package de.ur.agennt.ui;

import de.ur.agennt.entity.project.Project;

import java.io.IOException;

public interface ProjectViewer {

    public void viewProject(Project project) throws IOException;
}
