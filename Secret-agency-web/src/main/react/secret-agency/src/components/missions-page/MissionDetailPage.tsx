import * as React from "react";
import {IMission} from "../../types/Mission";
import * as missionService from "../../services/missionService";
import {Link} from "react-router-dom";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {IAgent} from "../../types/Agent";
import * as agentService from "../../services/agentService";
import {Button} from "react-bootstrap";

interface IMissionDetailPageState {
    mission: IMission;
    missionAgents: IAgent[];
    otherAgents: IAgent[];
    isLoaded: boolean;
}

interface IMissionDetailPageProps {
    missionId: number;
    isAuthenticatedUserAdmin: boolean;
}

type IProps = IMissionDetailPageProps;
type IState = IMissionDetailPageState;

/**
 * Form for displaying detailed mission.
 *
 * @author Milos Silhar (433614)
 */
export class MissionDetailPage extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props)

        this.state = {
            mission: {} as IMission,
            missionAgents: [],
            otherAgents: [],
            isLoaded: false,
        }
    }

    public componentDidMount() {
        missionService.getMission(this.props.missionId).then(m => {
            agentService.getAllAgents().then( agents => {
                const mAgents = agents.filter(agent => m.agentIds.indexOf(agent.id) !== -1);
                const oAgents = agents.filter(agent => m.agentIds.indexOf(agent.id) === -1);
                this.setState(prevState => ({...prevState,
                    mission: m,
                    missionAgents: mAgents,
                    otherAgents: oAgents,
                    isLoaded: true}));
            });
        });
    }

    public render() {
        if (!this.state.isLoaded) {
            return <div>Loading missions and agents...</div>;
        }

        const handleDelete = (missionId: number, agentId: number) => {
            const response = confirm("Remove agent from mission?");
            if (response) {
                missionService.removeAgentFromMission(agentId, missionId);
                const assigned = this.state.missionAgents.filter(a => a.id !== agentId);
                const removedAgentIndex = this.state.missionAgents.findIndex(a => a.id === agentId);
                const removedAgent = this.state.missionAgents[removedAgentIndex];
                const other = this.state.otherAgents.filter(a => true);
                other.push(removedAgent);
                this.setState(prevState => ({...prevState, missionAgents: assigned, otherAgents: other}));
            }
        };

        const handleAssignment = (missionId: number, agentId: number) => {
            const response = confirm("Assign agent to mission?");
            if (response) {
                missionService.assignAgentToMission(agentId, missionId);
                const other = this.state.otherAgents.filter(a => a.id !== agentId);
                const assignedAgentIndex = this.state.otherAgents.findIndex(a => a.id === agentId);
                const assignedAgent = this.state.otherAgents[assignedAgentIndex];
                const assigned = this.state.missionAgents.filter(a => true);
                assigned.push(assignedAgent);
                this.setState(prevState => ({...prevState, missionAgents: assigned, otherAgents: other}));
            }
        };

        const missionAgentRows = this.state.missionAgents.map(agent =>
            <tr key={agent.id}>
                <td>{agent.codeName}</td>
                <td>{agent.rank}</td>
                {this.props.isAuthenticatedUserAdmin && <td>
                    <Button className={"btn btn-danger"} onClick={() => handleDelete(this.props.missionId, agent.id)}>Remove</Button>
                </td>}
            </tr>
        );

        const otherAgentRows = this.state.otherAgents.map(agent =>
            <tr key={agent.id}>
                <td>{agent.codeName}</td>
                <td>{agent.rank}</td>
                {this.props.isAuthenticatedUserAdmin && <td>
                    <Button className={"btn btn-success"} onClick={() => handleAssignment(this.props.missionId, agent.id)}>Assign</Button>
                </td>}
            </tr>
        );

        return (
            <div className={"MissionDetailPage mb-5"}>
                <h4 className={"mt-2"}>Mission Detail</h4>
                <h5>Name <span className={"badge badge-light"}>{this.state.mission.name}</span></h5>
                <h5>Location <span className={"badge badge-light"}>{this.state.mission.latitude}, {this.state.mission.longitude}</span></h5>
                <h5>Start date <span className={"badge badge-light"}>{this.state.mission.started.toString()}</span></h5>
                {
                    this.state.mission.ended === null ?
                    <h5>Status <span className="badge badge-success">Active</span></h5> :
                    <div>
                        <h5>End date <span className="badge badge-light">{this.state.mission.ended.toString()}</span></h5>
                        <h5>Status <span className="badge badge-dark">Finished</span></h5>
                    </div>
                }
                <h4 className={"mt-5"}>Assigned Agents</h4>
                {
                    this.state.missionAgents.length === 0 ?
                    <div className={'alert alert-info'}>No one is assigned to this mission.</div> :
                    <table className="data-table">
                        <thead>
                        <tr>
                            <th>Codename</th>
                            <th>Rank</th>
                            {this.props.isAuthenticatedUserAdmin && <th>Actions</th>}
                        </tr>
                        </thead>
                        <tbody>
                        {missionAgentRows}
                        </tbody>
                    </table>
                }
                {
                    this.props.isAuthenticatedUserAdmin &&
                    <div>
                        <h5 className={"mt-5"}>Assign new Agent</h5>
                        {
                        this.state.otherAgents.length === 0 ?
                        <div className={'alert alert-info'}>Already assigned every agent to this mission.</div> :
                        <table className={"data-table"}>
                            <thead>
                            <tr>
                                <th>Codename</th>
                                <th>Rank</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {otherAgentRows}
                            </tbody>
                        </table>
                        }
                    </div>
                }
                <Link className={"btn btn-dark mt-3"} to={`${ROUTING_URL_BASE}/missions`}>Back</Link>
            </div>
        )
    }
}