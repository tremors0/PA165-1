import * as React from "react";
import {FormEvent} from "react";
import {MissionResult} from "../../types/Report";
import {Alert, Button, ControlLabel, FormControl, FormGroup} from "react-bootstrap";
import * as reportService from "../../services/reportService";
import * as missionService from "../../services/missionService";
import "./ReportNewForm.css";
import {Redirect} from "react-router";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {IMissionSelectOption} from "../../types/Mission";
import {AlertCloseable} from "../alert-closeable/AlertCloseable";


// INTERFACES FOR COMPONENT
interface IReportNewFormOwnProps {
    readonly authenticatedAgentId: number;
}

interface IReportNewFormState {
    // form data
    readonly text: string;
    readonly missionResult: MissionResult;
    readonly agentId: number | null;
    readonly missionId: number | null;

    // mission ids for select
    readonly missionIdsOptions: IMissionSelectOption[];

    // display data
    readonly redirectToReportsPage: boolean;
    readonly isLoading: boolean;

    // errors
    readonly hasError: boolean;
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
        const text = (e.target as HTMLTextAreaElement).value.trim();
        const isMessageTooShort = text.length < 10;
        this.setState((prevState) => ({...prevState, text, hasError: isMessageTooShort, submitError: ""}));
    };

    private onReportStatusChange = (e: FormEvent<FormControl>): void => {
        const missionResult = (e.target as HTMLSelectElement).value as MissionResult;
        this.setState(prevState => ({...prevState, missionResult}));
    };

    private onMissionChange = (e: FormEvent<FormControl>): void => {
        const missionId = parseInt((e.target as HTMLSelectElement).value, 10);
        this.setState(prevState => ({...prevState, missionId}));
    };

    private onSubmit = (e: FormEvent<Button>): void => {
        e.preventDefault();

        // new request - hide previous error
        this.setState(prevState => ({...prevState, createError: ""}));

        const {missionId, agentId, text, missionResult} = this.state;

        // validate data
        if (missionId == null || agentId == null || this.state.hasError) {
            const submitError = "Form is not valid.";
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
            agentId: null,
            missionId: null,
            redirectToReportsPage: false,
            isLoading: true,
            missionIdsOptions: [],
            hasError: false,
            loadingError: "",
            submitError: "",
            createError: "",
        }
    }

    public componentDidMount() {
        missionService.getAllMissions().then((missions) => {
            const authenticatedAgentId = this.props.authenticatedAgentId;
            const agentsMissionsOptions = missions.filter(mission => mission.agentIds.indexOf(authenticatedAgentId) !== -1)
                                                  .map(mission => ({ id: mission.id, name: mission.name }));
            this.setState(prevState => ({...prevState, missionIdsOptions: agentsMissionsOptions, isLoading: false}));
        }).catch(() => {
            const requestError = "Cannot load missions for authenticated agent";
            this.setState(prevState => ({...prevState, requestError, isLoading: false}));
        });
    }

    public componentWillReceiveProps(newProps: IProps) {
        const agentId = newProps.authenticatedAgentId;
        if (agentId !== this.state.agentId) {
            this.setState(prevState => ({...prevState, agentId}));
        }
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

    private getTextValidationState(): "success" | "warning" | "error" {
        if (this.state.text.length < 10) {
            return "error"
        }
        return "success";
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

        const missionResultOptions = this.getMissionResultOptions();
        const missionOptions = this.getMissionOptions();

        // form is loaded but agent does not participate on any mission
        if (missionOptions.length === 0) {
            return (<Alert bsStyle={"info"}>You don't participate on any mission</Alert>);
        }

        return (
            <div className={'ReportNewForm'}>
                <h2>Create new report {this.state.missionId != null && `for mission ${this.state.missionId}`}</h2>

                <AlertCloseable bsStyle={'danger'} isVisible={this.state.createError !== ""}>
                    {this.state.createError}
                </AlertCloseable>);

                <form className={'ReportNewForm__form'}>
                    <FormGroup controlId={'ReportNewForm__selectMission'}>
                        <ControlLabel>Mission</ControlLabel>
                        <FormControl componentClass={'select'}
                                     value={this.state.missionId!}
                                     onChange={this.onMissionChange}>
                            {missionOptions}
                        </FormControl>
                    </FormGroup>
                    <FormGroup controlId={'ReportNewForm__text'} validationState={this.getTextValidationState()}>
                        <ControlLabel>Report</ControlLabel>
                        <FormControl componentClass={'textarea'}
                                     value={this.state.text}
                                     placeholder={'Report must contain at least 10 characters'}
                                     onChange={this.onReportTextChange}/>
                        <FormControl.Feedback />
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

                <Button bsStyle={"primary"} type={"submit"} onChange={this.onSubmit}>Submit</Button>
            </div>
        )
    }
}