import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { getOneTask, updateTask } from "../../../actions/backlogAction";
import { Link } from "react-router-dom";

class UpdateProjectTask extends Component {
  constructor(props) {
    super(props);

    const { id } = this.props.match.params;
    const { sequence } = this.props.match.params;
    this.state = {
      summary: "",
      acceptanceCriteria: "",
      status: "",
      priority: 0,
      dueDate: "",
      projectIdentifier: id,
      projectSequence: sequence,
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    const {
      summary,
      acceptanceCriteria,
      status,
      priority,
      dueDate,
      projectIdentifier,
      projectSequence
    } = nextProps.project_task;

    this.setState({
      summary,
      acceptanceCriteria,
      status,
      priority,
      dueDate,
      projectIdentifier,
      projectSequence
    });
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    const { sequence } = this.props.match.params;
    this.props.getOneTask(id, sequence, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updatedTask = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      status: this.state.status,
      priority: this.state.priority,
      dueDate: this.state.dueDate
    };
    this.props.updateTask(
      updatedTask,
      this.state.projectIdentifier,
      this.state.projectSequence,
      this.props.history
    );
  }

  render() {
    const { id } = this.props.match.params;
    const { sequence } = this.props.match.params;

    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link to={`/projectBoard/${id}`} className="btn btn-light">
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <p className="lead text-center">
                Project Name: {id} | Project Code: {sequence}
              </p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    name="summary"
                    value={this.state.summary}
                    onChange={this.onChange}
                    placeholder="Project Task summary"
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Acceptance Criteria"
                    name="acceptanceCriteria"
                    value={this.state.acceptanceCriteria}
                    onChange={this.onChange}
                  ></textarea>
                </div>
                <h6>Due Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="dueDate"
                    value={this.state.dueDate}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value={0}>Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                </div>

                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="status"
                    value={this.state.status}
                    onChange={this.onChange}
                  >
                    <option value="">Select Status</option>
                    <option value="TO_DO">TO DO</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateProjectTask.propTypes = {
  getOneTask: PropTypes.func.isRequired,
  updateTask: PropTypes.func.isRequired,
  project_task: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project_task: state.backlog.project_task
});

export default connect(mapStateToProps, { getOneTask, updateTask })(
  UpdateProjectTask
);
