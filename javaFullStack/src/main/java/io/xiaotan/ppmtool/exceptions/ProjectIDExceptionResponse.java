package io.xiaotan.ppmtool.exceptions;

public class ProjectIDExceptionResponse {
    private String projectIdentifier;

    //Constructor
    public ProjectIDExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
