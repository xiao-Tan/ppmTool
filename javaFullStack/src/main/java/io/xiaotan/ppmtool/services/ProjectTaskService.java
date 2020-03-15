package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Backlog;
import io.xiaotan.ppmtool.domain.Project;
import io.xiaotan.ppmtool.domain.ProjectTask;
import io.xiaotan.ppmtool.exceptions.ProjectNotFoundException;
import io.xiaotan.ppmtool.repositories.BacklogRepository;
import io.xiaotan.ppmtool.repositories.ProjectRepository;
import io.xiaotan.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //Exception: project not found

        try {
            //1,project task to be added to a specific project, project != null, backlog exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            //2,set backlog to project task
            projectTask.setBacklog(backlog);

            //3,we want project tasks sequence to be progressing
            Integer BacklogSequence = backlog.getPTSequence();

            //4,update the backlog sequence
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null
            if(projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }

            //INITIAL status when status null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project is not found.");
        }

    }

    public Iterable<ProjectTask> findAllTaskById(String project_id){
        Project project = projectRepository.findByProjectIdentifier(project_id);
        if(project == null){
            throw new ProjectNotFoundException("Project with ID: " + project_id + " is not found");
        }
        List<ProjectTask> allTasks = projectTaskRepository.findByProjectIdentifierOrderByPriority(project_id);
        return allTasks;
    }
}
