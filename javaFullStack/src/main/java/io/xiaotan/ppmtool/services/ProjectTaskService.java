package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.Backlog;
import io.xiaotan.ppmtool.domain.ProjectTask;
import io.xiaotan.ppmtool.repositories.BacklogRepository;
import io.xiaotan.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //Exception: project not found

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
    }

}
