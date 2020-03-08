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
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIDException("Project ID" + project.getProjectIdentifier().toUpperCase() + "is already existed.");
        }
    }
}
