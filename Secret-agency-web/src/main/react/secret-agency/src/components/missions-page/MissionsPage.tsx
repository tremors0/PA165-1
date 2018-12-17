import * as React from "react";
import "../SharedStyles.css";
import * as missionService from "../../services/missionService";
import {IMission} from "../../types/Mission";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";

interface IMissionsState {
    activeMissions: IMission[];
    completedMissions: IMission[];
    isLoaded: boolean;
}

interface IProps {
    isAuthenticatedUserAdmin: boolean;
    authenticatedUserId: number;
}

interface ITableProps {
    missions: IMission[];
    isShowCompleted: boolean;
    isAdmin: boolean;
}

type IState = IMissionsState;

/**
 * Component for missions page
 * @author Milos Silhar (433614)
 */
export class MissionsPage extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            activeMissions: [],
            completedMissions: [],
            isLoaded: false,
        }
    }

    public componentDidMount() {
        missionService.getAllMissions().then((missions) => {
            const active = this.filterActiveMissions(missions);
            const completed = this.filterCompletedMissions(missions);
            this.setState((prevState) =>
                ({...prevState, isLoaded: true, activeMissions: active, completedMissions: completed}));
        });
    }

    private filterMissions(missions: IMission[]): IMission[] {
        if (!this.props.isAuthenticatedUserAdmin) {
            return missions
                .filter(mission =>
                    mission.agentIds.some((agentId) => agentId === this.props.authenticatedUserId));
        }
        return missions;
    }

    private filterActiveMissions(missions: IMission[]): IMission[] {
        return this.filterMissions(missions).filter(mission => mission.ended === null);
    }

    private filterCompletedMissions(missions: IMission[]): IMission[] {
        return this.filterMissions(missions).filter(mission => mission.ended !== null);
    }

    public render() {
        if (!this.state.isLoaded) {
            return <div>Loading table...</div>;
        }

        return (
            <div className={"MissionsPage mb-5"}>
                <h4 className={"mt-5"}>Active Missions <span className={"badge badge-success"}>{this.state.activeMissions.length}</span></h4>
                <RenderTable missions={this.state.activeMissions} isShowCompleted={false} isAdmin={this.props.isAuthenticatedUserAdmin}/>
                <h4 className={"mt-5"}>Completed Missions <span className={"badge badge-dark"}>{this.state.completedMissions.length}</span></h4>
                <RenderTable missions={this.state.completedMissions} isShowCompleted={true} isAdmin={this.props.isAuthenticatedUserAdmin}/>
                {this.props.isAuthenticatedUserAdmin && <Link className={"btn btn-success mt-4"} to={`${ROUTING_URL_BASE}/missions/new`}>Create mission</Link>}
            </div>
        )
    }
}

/**
 * Function to render table of missions
 * @param props Missions to render
 */
function RenderTable(props: ITableProps) {
    const showOnlyInfo = props.missions.length === 0;
    const infoString = props.isShowCompleted ? "There are no completed missions" : "There are no active missions";
    if (showOnlyInfo) {
        return <div className={'alert alert-info'}>{infoString}</div>;
    }

    const handleDelete = (missionId: number) => {
        alert("Delete mission with id" + missionId);
    };

    const rows = props.missions.map(mission =>
        <tr key={mission.id}>
            <td><Link to={`${ROUTING_URL_BASE}/missions/detail/${mission.id}`}>{mission.name}</Link></td>
            <td>{mission.latitude}</td>
            <td>{mission.longitude}</td>
            <td>{mission.missionType}</td>
            <td>{mission.started}</td>
            {props.isShowCompleted && <td>{mission.ended}</td>}
            <td>{mission.agentIds.length}</td>
            {props.isAdmin && <td>
                <Link className={"btn btn-primary ml-1"} to={`${ROUTING_URL_BASE}/missions/edit/${mission.id}`}>Edit</Link>
                <Button className={"btn btn-danger mt-1 ml-1"} onClick={() => handleDelete(mission.id)}>Delete</Button>
            </td>}
        </tr>
    );

    return (
        <div className="table-wrapper">
            <table className="data-table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>Type</th>
                    <th>Started</th>
                    {props.isShowCompleted && <th>Ended</th>}
                    <th>Assigned Agents</th>
                    {props.isAdmin && <th>Actions</th>}
                </tr>
                </thead>
                <tbody>
                {rows}
                </tbody>
            </table>
        </div>
    );
}
