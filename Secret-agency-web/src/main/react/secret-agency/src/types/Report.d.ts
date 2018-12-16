import {IMission} from "./Mission";
import {IAgent} from "./Agent";

export interface IReport {
    readonly id: number;
    readonly text: string;
    readonly date: Date;
    readonly reportStatus: ReportStatus;
    readonly missionResult: MissionResult;
    readonly mission: IMission;
    readonly agent: IAgent;
}

export type MissionResult = "COMPLETED" | "FAILED";
type ReportStatus = "NEW" | "UPDATED" | "APPROVED" | "DENIED";

export interface IReportCreate {
    readonly text: string;
    readonly missionResult: MissionResult;
    readonly agentId: number;
    readonly missionId: number;
}