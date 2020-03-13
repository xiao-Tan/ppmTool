import { GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "../actions/types";

const initialState = {
  projects: [],
  project: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload
      };
    case GET_PROJECT:
      return {
        ...state,
        project: action.payload
      };
    case DELETE_PROJECT:
      return {
        ...state,
        projects: state.projects.filter(
          project => project.projectIdentifier !== action.payload //更新state，不用refresh，排除掉对应id的project
        )
      };

    default:
      return state;
  }
}
