import * as React from "react";
import {FormEvent} from "react";
import {MissionResult} from "../../types/Report";
import {Alert, Button, ControlLabel, FormControl, FormGroup} from "react-bootstrap";
import * as reportService from "../../services/reportService";
import * as missionService from "../../services/missionService";
import "./ReportNewForm.css";
import {Redirect} from "react-router";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {IMission, IMissionSelectOption} from "../../types/Mission";
import {AlertCloseable} from "../alert-closeable/AlertCloseable";


// INTERFACES FOR COMPONENT
interface IReportNewFormOwnProps {
    readonly authenticatedAgentId: number;
}

interface IReportNewFormState {
    // form data
    readonly text: string;
    readonly missionResult: MissionResult;
    readonly missionId: number;

    // mission ids for select
    readonly missionIdsOptions: IMissionSelectOption[];

    // display data
    readonly redirectToReportsPage: boolean;
    readonly isLoading: boolean;

    // errors
    readonly loadingError: string;
    readonly submitError: string;
    readonly createError: string;
}

type IState = IReportNewFormState;
type IProps = IReportNewFormOwnProps;

/**
 * Form for creating new report.
 *
 * @author Jan Pavlu (487548)
 */
export class ReportNewForm extends React.PureComponent<IProps, IState> {

    /********************************************************
     * EVENT HANDLERS
     *******************************************************/
    private onReportTextChange = (e: FormEvent<FormControl>): void => {
        const text = (e.target as HTMLTextAreaElement).value;
        this.setState((prevState) => ({...prevState, text, submitError: ""}));
    };

    private onReportStatusChange = (e: FormEvent<FormControl>): void => {
        const missionResult = (e.target as HTMLSelectElement).value as MissionResult;
        this.setState(prevState => ({...prevState, missionResult}));
    };

    private onMissionChange = (e: FormEvent<FormControl>): void => {
        const missionId = parseInt((e.target as HTMLSelectElement).value, 10);
        this.setState(prevState => ({...prevState, missionId}));
    };

    private onCloseCreateAlert = (): void => {
        this.setState(prevState => ({...prevState, createError: ""}));
    };

    private onSubmit = (e: FormEvent<Button>): void => {
        e.preventDefault();

        // new request - hide previous error
        this.setState(prevState => ({...prevState, createError: ""}));

        const {missionId, text, missionResult} = this.state;
        const agentId = this.props.authenticatedAgentId;

        // validate data
        if (this.state.text.trim().length < 10) {
            const submitError = "Report is not long enough";
            this.setState(prevState => ({...prevState, submitError}));
            return;
        }

        // create data for request
        const newReport = { agentId, missionId, missionResult, text };

        // save new report and handle response
        reportService.createNewReport(newReport).then((result) => {
            if (typeof result === "string") {
                this.setState(prevState => ({...prevState, createError: result, submitError: ""}));
           } else {
               this.setState((prevState) => ({...prevState, redirectToReportsPage: true}))
           }
        });
    };

    /********************************************************
     * LIFE-CYCLE METHODS
     *******************************************************/
    constructor(props: IProps) {
        super(props);

        this.state = {
            text: "",
            missionResult: "COMPLETED",
            missionId: -1, // fake id - is changed after componentDidMount method
            redirectToReportsPage: false,
            isLoading: true,
            missionIdsOptions: [],
            loadingError: "",
            submitError: "",
            createError: "",
        }
    }

    /**
     * Prepare values for mission select when component is mounted.
     */
    public async componentDidMount() {
        let missions: IMission[] = [];
        try {
            missions = await missionService.getAllMissions();
        } catch (e) {
            const requestError = "Cannot load missions for authenticated agent";
            this.setState(prevState => ({...prevState, requestError, isLoading: false}));
        }

        const agentsMissionsOptions = await this.getMissionOptionsForSelect(missions);
        const missionId = agentsMissionsOptions.length === 0 ? -1 : agentsMissionsOptions[0].id;
        this.setState(prevState => ({...prevState, missionIdsOptions: agentsMissionsOptions,
            isLoading: false, missionId}));

    }

    /**
     * Return options for mission select. Should return only missions, in which given agent took part
     *   and for which the agent has not written the review yet.
     * @param missions all missions
     */
    private async getMissionOptionsForSelect(missions: IMission[]): Promise<IMissionSelectOption[]> {
        const missionsJoinedByAgent = this.getMissionsJoinedByAgent(missions);
        const missionsJoinedByAgentWithoutReports = await this.filterMissionsAgentAlreadyWroteReview(missionsJoinedByAgent);
        return missionsJoinedByAgentWithoutReports.map((mission) => ({id: mission.id, name: mission.name}));
    }

    /**
     * Return missions in which logged user took part.
     * @param missions missions in which logged user took part
     */
    private getMissionsJoinedByAgent(missions: IMission[]): IMission[] {
        return missions.filter(mission => mission.agentIds.indexOf(this.props.authenticatedAgentId) !== -1);
    }

    /**
     * Returns missions for which user has not written review yet.
     * @param missionsJoinedByLoggedAgent missions for which user has not written review yet
     */
    private async filterMissionsAgentAlreadyWroteReview(missionsJoinedByLoggedAgent: IMission[]): Promise<IMission[]> {
        const reports = await reportService.getAllReports();
        const reportsFromLoggedUserIds = reports.filter(report => report.agent.id === this.props.authenticatedAgentId)
                                                .map(report => report.id);
        const result: IMission[] = [];
        missionsJoinedByLoggedAgent.forEach(mission => {
           for(const reportId of reportsFromLoggedUserIds) {
               if (mission.reportIds.indexOf(reportId) >= 0) {
                   return;
               }
           }
           result.push(mission);
        });
        return result;
    }

    /********************************************************
     * RENDERING
     *******************************************************/
    private getMissionResultOptions() {
        const missionResultOptions = ["COMPLETED", "FAILED"];
        return missionResultOptions.map(((value, index) => (
            <option value={value} key={index}>{value}</option>
        )));
    }

    private getMissionOptions() {
        return this.state.missionIdsOptions.map((missionOpt, index) => (
           <option value={missionOpt.id} key={index}>{missionOpt.name}</option>
        ));
    }

    private getMissionNameForId(id: number):string {
        return this.state.missionIdsOptions.filter((missionOpt) => missionOpt.id === id)
                                           .map((mission) => mission.name)[0];
    }


    public render(): JSX.Element {
        // submit is completed - redirect to reports
        if (this.state.redirectToReportsPage) {
            return <Redirect to={`${ROUTING_URL_BASE}/reports`} />;
        }

        // form is loading
        if (this.state.isLoading) {
            return <div>Form is loading...</div>;
        }

        // server responded with error - form cannot be loaded
        if (this.state.loadingError !== "") {
            return <Alert bsStyle={"danger"}>{this.state.loadingError}</Alert>
        }
        const missionOptions = this.getMissionOptions();

        // form is loaded but agent does not participate on any mission
        if (missionOptions.length === 0) {
            return (
                <Alert bsStyle={"info"}>
                    You don't participate on any mission or you've written the review already.
                </Alert>
            );
        }

        const selectedMissionId = this.state.missionId;
        const selectedMissionName = this.getMissionNameForId(selectedMissionId) || "";
        const missionResultOptions = this.getMissionResultOptions();

        return (
            <div className={'ReportNewForm'}>
                <AlertCloseable bsStyle={'danger'}
                                isVisible={this.state.createError !== ""}
                                onHide={this.onCloseCreateAlert}>
                    {this.state.createError}
                </AlertCloseable>

                <h2>Create new report for mission {selectedMissionId !== -1 &&
                    <span className={'text-primary'}>{selectedMissionName}</span>}
                </h2>

                <form className={'ReportNewForm__form'} noValidate={true}>
                    <FormGroup controlId={'ReportNewForm__selectMission'}>
                        <ControlLabel>Mission</ControlLabel>
                        <FormControl componentClass={'select'}
                                     value={selectedMissionId}
                                     onChange={this.onMissionChange}>
                            {missionOptions}
                        </FormControl>
                    </FormGroup>
                    <FormGroup controlId={'ReportNewForm__text'}>
                        <ControlLabel>Report</ControlLabel>
                        <FormControl componentClass={'textarea'}
                                     value={this.state.text}
                                     placeholder={'Report must contain at least 10 characters'}
                                     onChange={this.onReportTextChange}
                                     className={this.state.submitError && "is-invalid"}/>
                        {this.state.submitError && <div className={'text-danger small'}>{this.state.submitError}</div>}
                    </FormGroup>
                    <FormGroup controlId={'ReportNewForm__missionResult'}>
                        <ControlLabel>Result of your work</ControlLabel>
                        <FormControl componentClass={'select'}
                                     value={this.state.missionResult}
                                     onChange={this.onReportStatusChange}>
                            {missionResultOptions}
                        </FormControl>
                    </FormGroup>
                </form>

                <Button bsStyle={"primary"} type={"submit"} onClick={this.onSubmit}>Submit</Button>
            </div>
        )
    }
}