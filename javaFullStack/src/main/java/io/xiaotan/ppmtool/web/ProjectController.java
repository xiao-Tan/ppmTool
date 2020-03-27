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
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationMapErrorService validationMapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createdNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){

        ResponseEntity<?> errorMap = validationMapErrorService.ValidationMapService(result);
        if(errorMap!=null){
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project, principal.getName()); //add and save to database.
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectID, Principal principal ){
        Project project = projectService.findProjectByIdentifier(projectID, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectID, Principal principal){
        projectService.deleteProjectById(projectID,principal.getName());
        return new ResponseEntity<String>(projectID + "has deleted successfully.", HttpStatus.OK);
    }

}
