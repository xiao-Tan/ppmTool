package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Backlog;
import io.xiaotan.ppmtool.domain.Project;
import io.xiaotan.ppmtool.domain.User;
import io.xiaotan.ppmtool.exceptions.ProjectIDException;
import io.xiaotan.ppmtool.repositories.BacklogRepository;
import io.xiaotan.ppmtool.repositories.ProjectRepository;
import io.xiaotan.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        String identifier = project.getProjectIdentifier().toUpperCase();

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(identifier);

            //如果project不存在，new一个新的backlog给project
            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);
            }

            //如果存在，即update， 提取backlog给project
            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIDException("Project ID '" + identifier + "' is already existed.");
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
