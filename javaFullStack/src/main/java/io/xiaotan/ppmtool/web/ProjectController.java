package io.xiaotan.ppmtool.web;

import io.xiaotan.ppmtool.domain.Project;
import io.xiaotan.ppmtool.services.ProjectService;
import io.xiaotan.ppmtool.services.ValidationMapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationMapErrorService validationMapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createdNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = validationMapErrorService.ValidationMapService(result);
        if(errorMap!=null){
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project); //add and save to database.
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectID){

        Project project = projectService.findProjectByIdentifier(projectID);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectID){
        projectService.deleteProjectById(projectID);
        return new ResponseEntity<String>(projectID + "has deleted successfully.", HttpStatus.OK);
    }

}
