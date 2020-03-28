package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Backlog;
import io.xiaotan.ppmtool.domain.ProjectTask;
import io.xiaotan.ppmtool.exceptions.ProjectIDException;
import io.xiaotan.ppmtool.exceptions.ProjectNotFoundException;
import io.xiaotan.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    //add task to backlog(project) by identifier
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

        //Exception: project not found
        //1,project task to be added to a specific project, project != null, backlog exists
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
                //backlogRepository.findByProjectIdentifier(projectIdentifier);

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
        if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }

        //INITIAL status when status null
        if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);

    }

    //find all tasks from one project
    public Iterable<ProjectTask> findAllTaskById(String backlog_id,String username){

        projectService.findProjectByIdentifier(backlog_id, username);

        List<ProjectTask> allTasks = projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
        return allTasks;
    }

    //find one task by projectSequence
    public ProjectTask findOneTaskBySequence(String backlog_id, String project_sequence, String username){
        //1,check backlog(project) exists
        projectService.findProjectByIdentifier(backlog_id, username);

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
    public ProjectTask updateTaskBySequence(ProjectTask updatedTask, String backlog_id, String project_sequence, String username){
        //use function above, do the same validation
        ProjectTask projectTask = findOneTaskBySequence(backlog_id,project_sequence,username);
        projectTask = updatedTask;
        projectTaskRepository.save(projectTask);
        return projectTask;
    }

    //delete project task
    public void deleteTask(String backlog_id, String project_sequence,String username){
        ProjectTask projectTask = findOneTaskBySequence(backlog_id,project_sequence,username);
        projectTaskRepository.delete(projectTask);
    }

}
