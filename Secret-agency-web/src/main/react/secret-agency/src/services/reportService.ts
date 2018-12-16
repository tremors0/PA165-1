import {IReport, IReportCreate} from "../types/Report";
import {GET, POST, REST_URL_BASE} from "../utils/requestUtils";


export function getAllReports(): Promise<IReport[]> {
    return GET<IReport[]>(`${REST_URL_BASE}/reports`).then((result) => {
       return result.data;
    });
}

/**
 * Creates new report. If report cannot be created, return error message.
 * @param newReport
 */
export function createNewReport(newReport: IReportCreate): Promise<IReport | string>{
    return POST<IReport>(`${REST_URL_BASE}/reports`, newReport).then((response => {
        return response.data;
    })).catch((response) => {
        return response.response.data;
    });
}
