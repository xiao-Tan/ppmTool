package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Backlog;
import io.xiaotan.ppmtool.domain.Project;
import io.xiaotan.ppmtool.domain.ProjectTask;
import io.xiaotan.ppmtool.exceptions.ProjectIDException;
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

    //add task to backlog(project) by identifier
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

    //find all tasks from one project
    public Iterable<ProjectTask> findAllTaskById(String backlog_id){
        //Project project = projectRepository.findByProjectIdentifier(project_id);
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: " + backlog_id + " is not found");
        }
        List<ProjectTask> allTasks = projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
        return allTasks;
    }

    //find one task by projectSequence
    public ProjectTask findOneTaskBySequence(String backlog_id, String project_sequence){
        //1,check backlog(project) exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: " + backlog_id + " is not found");
        }

        //2,check task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(project_sequence);
        if(projectTask == null){
            throw new ProjectNotFoundException("Task is not found");
        }

        //3,check backlog/project id corresponds right to task
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectIDException("This project does not contain this task");
        }

        return projectTask;
    }

    //update project task
    public ProjectTask updateTaskBySequence(ProjectTask updatedTask, String backlog_id, String project_sequence){
        //use function above, do the same validation
        ProjectTask projectTask = findOneTaskBySequence(backlog_id,project_sequence);
        projectTask = updatedTask;
        projectTaskRepository.save(projectTask);
        return projectTask;
    }

    //delete project task
    public void deleteTask(String backlog_id, String project_sequence){
        ProjectTask projectTask = findOneTaskBySequence(backlog_id,project_sequence);
        projectTaskRepository.delete(projectTask);
    }

}
