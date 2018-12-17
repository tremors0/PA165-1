import {IReport, IReportCreate, IReportUpdate} from "../types/Report";
import {DELETE, GET, POST, PUT, REST_URL_BASE} from "../utils/requestUtils";


export function getAllReports(): Promise<IReport[]> {
    return GET<IReport[]>(`${REST_URL_BASE}/reports`).then((result) => {
       return result.data;
    });
}

/**
 * Creates new report. If report cannot be created, return error message.
 * @param newReport
 */
export function createNewReport(newReport: IReportCreate): Promise<IReport | string> {
    return POST<IReport>(`${REST_URL_BASE}/reports`, newReport).then((response => {
        return response.data;
    })).catch((response) => {
        return response.response.data;
    });
}

/**
 * Delete report with specific id. Returns true, if report was deleted. Otherwise returns false;
 * @param reportId id of the report
 */
export function deleteReport(reportId: number): Promise<boolean> {
    return DELETE<void>(`${REST_URL_BASE}/reports/report/${reportId}`).then(() => {
        return true;
    }).catch(() => {
        return false;
    });
}

/**
 * Get report with selected id. If report is not found, error message is returned.
 * @param reportId id of the report
 */
export function getReportById(reportId: number): Promise<IReport | string> {
    return GET<IReport>(`${REST_URL_BASE}/reports/report/${reportId}`).then((report) => {
        return report.data;
    }).catch((error) => {
        return error.response.data as string;
    });
}

/**
 * Update selected report. Returns updated report or error message.
 * @param reportUpdate data needed for updating report
 */
export async function updateReport(reportUpdate: IReportUpdate): Promise<IReport | string> {
    return PUT<IReport>(`${REST_URL_BASE}/reports/report/${reportUpdate.id}`, reportUpdate)
        .then(response => {
            return response.data
        }).catch(error => {
            return error.response.data as string;
        });
}