import axios, {AxiosPromise, AxiosResponse} from "axios";
import * as secretAgencyRepository from "../repository/secretAgecyRepository";

// absolute path is used to be able send requests both from node server and tomcat
export const URL_BASE = "http://localhost:8080/pa165";
export const REST_URL_BASE = "http://localhost:8080/pa165/rest";

// helper request methods - for authorized request
export function GET<T>(url: string): AxiosPromise<T> {
    return axios.get<T>(url, {withCredentials: true}).then((response) => {
        validateResponse(response);
        return response;
    });
}

export function POST<T>(url: string, data?: any): AxiosPromise<T> {
    return axios.post<T>(url, data, {withCredentials: true}).then((response) => {
        validateResponse(response);
        return response;
    });
}

export function PUT<T>(url: string, data: any): AxiosPromise<T> {
    return axios.put<T>(url, data, {withCredentials: true}).then((response) => {
        validateResponse(response);
        return response;
    });
}

export function DELETE<T>(url: string): AxiosPromise<T> {
    return axios.delete(url, {withCredentials: true}).then((response) => {
        validateResponse(response);
        return response;
    });
}

/**
 * Check if user was logged out. If that's true, it removes authenticated user data from local storage and reloads page.
 * @param response server response for last request
 */
function validateResponse(response: AxiosResponse): void {
    // if response is redirection or HTML document, user was logged out
    if (response.status === 301 || response.status === 302 || response.status === 303 ||
        isResponseHtml(response.data)) {
        const agentRemoved = secretAgencyRepository.removeAuthenticatedAgent();
        if (agentRemoved) {
            location.reload();
        }
    }
}

function isResponseHtml(responseData: any): boolean {
    return typeof responseData === "string" && responseData.indexOf("<!DOCTYPE HTML>") !== -1;
}