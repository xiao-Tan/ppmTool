package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Project;
import io.xiaotan.ppmtool.exceptions.ProjectIDException;
import io.xiaotan.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIDException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' is already existed.");
        }
    }

    public Project findProjectByIdentifier(String projectID){

        Project project = projectRepository.findByProjectIdentifier(projectID.toUpperCase());
        if (project == null){
            throw new ProjectIDException("Project ID '" + projectID + "' is not found");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null){
            throw new ProjectIDException("Project ID '" + projectId + "' is not exist");
        }
        projectRepository.delete(project);
    }
}
