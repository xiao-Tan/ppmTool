import axios from "axios";
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK } from "./types";

export const creatProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.post(
      `http://localhost:8080/api/backlog/${backlog_id}`,
      project_task
    );
    history.push(`/projectBoard/${backlog_id}`);

    //when backto form, error massage refresh to null
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getAllTasks = backlog_id => async dispatch => {
  try {
    const res = await axios.get(
      `http://localhost:8080/api/backlog/${backlog_id}`
    );
    dispatch({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getOneTask = (
  backlog_id,
  sequence_id,
  history
) => async dispatch => {
  try {
    const res = await axios.get(
      `http://localhost:8080/api/backlog/${backlog_id}/${sequence_id}`
    );
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};

export const updateTask = (
  updatedTask,
  backlog_id,
  sequence_id,
  history
) => async dispatch => {
  try {
    await axios.patch(
      updatedTask,
      `http://localhost:8080/api/backlog/${backlog_id}/${sequence_id}`
    );
    history.push(`/projectBoard/${backlog_id}`);
  } catch (error) {}
};
