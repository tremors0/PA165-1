import * as React from "react";
import {IMission} from "../../types/Mission";
import * as missionService from "../../services/missionService";
import {Link} from "react-router-dom";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {IAgent} from "../../types/Agent";
import * as agentService from "../../services/agentService";

interface IMissionDetailPageState {
    mission: IMission;
    agents: IAgent[];
    isLoaded: boolean;
}

interface IMissionDetailPageProps {
    missionId: number;
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
            agents: [],
            isLoaded: false,
        }
    }

    public componentDidMount() {
        missionService.getMission(this.props.missionId).then(m => {
            this.setState(prevState => ({...prevState, isLoaded: true, mission: m}));
            agentService.getAllAgents().then(agents => {
                const assignedAgents = [] as IAgent[];
                agents.forEach(agent => {
                    if (m.agentIds.indexOf(agent.id) !== -1) {
                        assignedAgents.push(agent);
                    }
                });
                this.setState(prevState => ({...prevState, agents: assignedAgents}));
            })
        })
    }

    public render() {
        if (!this.state.isLoaded) {
            return <div>Loading mission...</div>;
        }

        return (
            <div>
                Mission Name: {this.state.mission.name}<br />
                Mission Latitude: {this.state.mission.latitude}<br />
                Mission Longitude: {this.state.mission.longitude}<br />
                Mission StartDate: {this.state.mission.started}<br />
                Mission EndDate: {this.state.mission.ended}<br />
                Assigned Agents: {this.state.agents.map(agent => <div key={agent.id}>{agent.codeName}<br /></div>)}<br />

                <Link className={"btn btn-dark"} to={`${ROUTING_URL_BASE}/missions`}>Back</Link>
            </div>
        )
    }
}