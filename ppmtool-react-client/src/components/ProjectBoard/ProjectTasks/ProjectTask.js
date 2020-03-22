import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteTask } from "../../../actions/backlogAction";

class ProjectTask extends Component {
  onDeleteClick = (backlog_id, sequence_id) => {
    this.props.deleteTask(backlog_id, sequence_id);
  };

  render() {
    const { project_task } = this.props;
    let priorityString;
    let priorityClass;

    if (project_task.priority === 1) {
      priorityString = "HIGH";
      priorityClass = "bg-danger text-light";
    }
    if (project_task.priority === 2) {
      priorityString = "MEDIUM";
      priorityClass = "bg-warning text-light";
    }
    if (project_task.priority === 3) {
      priorityString = "LOW";
      priorityClass = "bg-info text-dark";
    }

    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          ID: {project_task.projectSequence} -- Priority: {priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_task.summary}</h5>
          <p className="card-text text-truncate ">
            {project_task.acceptanceCriteria}
          </p>
          <Link
            to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`}
            className="btn btn-primary"
          >
            View / Update
          </Link>

          <button
            className="btn btn-danger ml-4"
            onClick={this.onDeleteClick.bind(
              this,
              project_task.projectIdentifier,
              project_task.projectSequence
            )}
          >
            Delete
          </button>
        </div>
      </div>
    );
  }
}

ProjectTask.propTypes = {
  deleteTask: PropTypes.func.isRequired
};

export default connect(null, { deleteTask })(ProjectTask);
