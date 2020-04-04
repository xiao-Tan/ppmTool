import axios from "axios";
import { SET_CURRENT_USER, GET_ERRORS } from "./types";
import setJwtToken from "../securityutil/setJwtToken";
import jwt_decode from "jwt-decode";

export const userRegister = (newUser, history) => async dispatch => {
  try {
    await axios.post("http://localhost:8080/api/users/register", newUser);
    history.push("/login");

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const userLogin = LoginRequest => async dispatch => {
  try {
    //post => login request
    const res = await axios.post(
      "http://localhost:8080/api/users/login",
      LoginRequest
    );

    //extract token from res.data
    const { token } = res.data;

    //store token in local storage
    localStorage.setItem("jwtToken", token);

    //set token in header
    setJwtToken(token);

    //decode token on react
    const decode = jwt_decode(token);

    //dispatch to security reducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decode
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
