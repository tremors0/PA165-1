export interface IMission {
    readonly id: number;
    readonly name: string;
    readonly latitude: number;
    readonly longitude: number;
    readonly started: Date;
    readonly ended: Date;
    readonly missionType: string;
    readonly agentIds: number[];
    readonly reportIds: number[];
}

export interface IMissionSelectOption {
    readonly id: number,
    readonly name: string,
}