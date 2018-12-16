import {GET, REST_URL_BASE} from "../utils/requestUtils";
import {IMission} from "../types/Mission";

export function getAllMissions(): Promise<IMission[]> {
    return GET<IMission[]>(`${REST_URL_BASE}/missions`).then(response => {
        return response.data;
    });
}
