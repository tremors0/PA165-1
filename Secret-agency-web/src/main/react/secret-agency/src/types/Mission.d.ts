export interface IMission {
    readonly id: number;
    readonly name: string;
    // TODO finish after RestController is ready
    readonly agentIds: number[];
}

export interface IMissionSelectOption {
    readonly id: number,
    readonly name: string,
}