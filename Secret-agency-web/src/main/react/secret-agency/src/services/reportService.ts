import {IReport} from "../types/Report";
import {GET, REST_URL_BASE} from "../utils/requestUtils";


export function getAllReports(): Promise<IReport[]> {
    return GET<IReport[]>(`${REST_URL_BASE}/reports`).then((result) => {
       return result.data;
    });
}
