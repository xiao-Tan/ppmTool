package io.xiaotan.ppmtool.web;

import io.xiaotan.ppmtool.domain.ProjectTask;
import io.xiaotan.ppmtool.services.ProjectTaskService;
import io.xiaotan.ppmtool.services.ValidationMapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ValidationMapErrorService validationMapErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> errorMap = validationMapErrorService.ValidationMapService(result);
        if(errorMap != null){
            return errorMap;
        }
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id,projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getAllTasks(@PathVariable String backlog_id){
        return projectTaskService.findAllTaskById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{project_sequence}")
    public ResponseEntity<?> getOneTaskBySequence(@PathVariable String backlog_id, @PathVariable String project_sequence){
        ProjectTask projectTask = projectTaskService.findOneTaskBySequence(backlog_id, project_sequence);
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{project_sequence}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody ProjectTask updateTask,BindingResult result,@PathVariable String backlog_id, @PathVariable String project_sequence){
        ResponseEntity<?> errorMap = validationMapErrorService.ValidationMapService(result);
        if(errorMap != null){
            return errorMap;
        }
        ProjectTask updatedTask = projectTaskService.updateTaskBySequence(updateTask,backlog_id, project_sequence);
        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{project_sequence}")
    public ResponseEntity<?> deleteTask(@PathVariable String backlog_id, @PathVariable String project_sequence){
        projectTaskService.deleteTask(backlog_id,project_sequence);
        return new ResponseEntity<String>("Task with Sequence" + project_sequence + "delete successfully", HttpStatus.OK);
    }

}
