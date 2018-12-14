import {IAgent} from "../types/Agent";
import * as secretAgencyReposotory from "../repository/secretAgecyRepository";
import {POST, URL_BASE} from "../utils/requestUtils";

/**
 * Method for authentication. When user is logged successfully, it returns object
 *   which holds info about authenticated user. When error occurs, error message is returned.
 *
 * @param codeName codename from form
 * @param password password from form
 */
export function authenticate(codeName: string, password: string): Promise<IAgent | string> {
  const formData = new FormData();
  formData.append("username", codeName);
  formData.append("password", password);

  // emulate sending form
  return POST(`${URL_BASE}/login`, formData)
    .then((result) => {
      return result.data as IAgent;
    })
    .catch((result) => {
      return result.response.data as string;
    });
}

/**
 * Log out authenticated user.
 */
export function logOut() {
  POST(`${URL_BASE}/logout`);
}

/**
 * Tries to authenticate automatically. Authentication is successful when agent is found in
 *   local storage.
 */
export function authenticateAutomatically(): IAgent | null {
  return secretAgencyReposotory.loadAuthenticatedAgent();
}