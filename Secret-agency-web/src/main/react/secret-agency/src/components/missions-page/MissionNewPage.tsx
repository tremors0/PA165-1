import * as React from "react";
import {ChangeEvent, FormEvent} from "react";
import {Alert, Button} from "react-bootstrap";
import * as missionService from "../../services/missionService";
import {Redirect} from "react-router";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";


// INTERFACES FOR COMPONENT
interface IMissionEditPageProps {
    isAuthenticatedUserAdmin: boolean;
    authenticatedUserId: number;
}

interface IMissionEditPageState {
    // form data
    latitude: number;
    longitude: number;
    missionType: string;
    started: string;

    // mission ids for select
    missionTypesOptions: string[];

    // display data
    redirectToMissionsPage: boolean;
    isLoading: boolean;

    // errors
    errorMsg: string;
    latitudeError: boolean;
    longitudeError: boolean;
    typeError: boolean;
    startedError: boolean;
}

type IState = IMissionEditPageState;
type IProps = IMissionEditPageProps;

/**
 * Form for creating new mission.
 *
 * @author Milos Silhar (433614)
 */
export class MissionNewPage extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            latitude: 0.0,
            longitude: 0.0,
            missionType: "",
            started: "",
            missionTypesOptions: [],
            redirectToMissionsPage: false,
            isLoading: true,
            errorMsg: "",
            latitudeError: false,
            longitudeError: false,
            typeError: false,
            startedError: false,
        }
    }

    public componentDidMount() {
        missionService.getAllTypes().then((types) => {
            this.setState(prevState => ({...prevState, missionTypesOptions: types, missionType: types[0], isLoading: false}));
        }).catch(() => {
            const requestError = "Cannot load mission types";
            this.setState(prevState => ({...prevState, errorMsg: requestError, isLoading: false}));
        });
    }

    /********************************************************
     * EVENT HANDLERS
     *******************************************************/
    private onLatitudeChange = (e: ChangeEvent<HTMLInputElement>): void => {
        const la = parseFloat(e.currentTarget.value);
        this.setState(prevState => ({...prevState, latitude: la}));
    };

    private onLongitudeChange = (e: ChangeEvent<HTMLInputElement>): void => {
        const lo = parseFloat(e.currentTarget.value);
        this.setState(prevState => ({...prevState, longitude: lo}));

    };

    private onStartedChange = (e: ChangeEvent<HTMLInputElement>): void => {
        const startedDate = e.currentTarget.value;
        this.setState(prevState => ({...prevState, started: startedDate}));
    };

    private onMissionTypeChange = (e: ChangeEvent<HTMLSelectElement>): void => {
        const type = e.currentTarget.value;
        this.setState(prevState => ({...prevState, missionType: type}));
    };

    private onSubmit = (e: FormEvent<Button>): void => {
        e.preventDefault();

        // new request - hide previous error
        this.setState(prevState => ({...prevState, latitudeError: false, longitudeError: false, typeError: false, }));

        let isFormValid = true;
        // validate data
        if (isNaN(this.state.latitude) || this.state.latitude > 90 || this.state.latitude < - 90) {
            this.setState(prevState => ({...prevState, latitudeError: true}));
            isFormValid=false;
        }
        if (isNaN(this.state.longitude) || this.state.longitude > 180 || this.state.longitude < -180) {
            this.setState(prevState => ({...prevState, longitudeError: true}));
            isFormValid=false;
        }
        if (this.state.missionType === "") {
            this.setState(prevState => ({...prevState, typeError: true}));
            isFormValid=false;
        }
        if (!this.isDate(this.state.started)) {
            this.setState(prevState => ({...prevState, startedError: true}));
            isFormValid=false;
        }
        if (!isFormValid) {return;}

        // create data for request
        // const newMission = {latitude: this.state.latitude, longitude: this.state.longitude, missionType: this.state.missionType, started: this.state.started};
        console.log(this.state.started);
    };

    private isDate(value: string): boolean {
        const msec = Date.parse(value);
        return !isNaN(msec);
    }

    public render() {
        // submit is completed - redirect to reports
        if (this.state.redirectToMissionsPage) {
            return <Redirect to={`${ROUTING_URL_BASE}/missions`} />;
        }

        // form is loading
        if (this.state.isLoading) {
            return <div>Form is loading...</div>;
        }

        // server responded with error - form cannot be loaded
        if (this.state.errorMsg !== "") {
            return <Alert bsStyle={"danger"}>{this.state.errorMsg}</Alert>
        }

        return (
            <div className={'ReportNewForm'}>
                <h2>Create new mission</h2>
                <form className={'MissionNewForm__form'}>
                    <div className={"form-row"}>
                        <div className={"col-md-6 mb-3"}>
                            <label htmlFor={"latitudeInput"}>Latitude</label>
                            <input type={"number"} className={"form-control"} id={"latitudeInput"} placeholder={"Latitude"} value={this.state.latitude} onChange={this.onLatitudeChange}/>
                            <div className={this.state.latitudeError ? "invalid-feedback" : "valid-feedback"}>
                                {this.state.latitudeError ? "Latitude must be a number between -90 and 90!" : "OK"}
                            </div>
                        </div>
                    </div>
                    <div className={"form-row"}>
                        <div className={"col-md-6 mb-3"}>
                            <label htmlFor={"longitudeInput"}>Longitude</label>
                            <input type={"number"} className={"form-control"} id={"longitudeInput"} placeholder={"Longitude"} value={this.state.longitude} onChange={this.onLongitudeChange}/>
                            <div className={this.state.longitudeError ? "invalid-feedback" : "valid-feedback"}>
                                {this.state.longitudeError ? "Longitude must be a number between -180 and 180!" : "OK"}
                            </div>
                        </div>
                    </div>
                    <div className={"form-row"}>
                        <div className={"col-md-6 mb-3"}>
                            <label htmlFor={"typeSelect"}>Mission Type</label>
                            <select className={"form-control"} id={"typeSelect"} value={this.state.missionType} onChange={this.onMissionTypeChange}>
                                {this.state.missionTypesOptions.map((typeOption) =>
                                    <option key={typeOption}
                                            value={typeOption}>{typeOption}</option>
                                )}
                            </select>
                        </div>
                    </div>
                    <div className={"form-row"}>
                        <div className={"col-md-6 mb-3"}>
                            <label htmlFor={"startedInput"}>Start date</label>
                            <input type={"text"} className={"form-control"} id={"startedInput"} placeholder={"Start date"} value={this.state.started} onChange={this.onStartedChange}/>
                            <div className={this.state.startedError ? "invalid-feedback" : "valid-feedback"}>
                                {this.state.startedError ? "Invalid Date (required format: yyyy-MM-dd)" : "OK"}
                            </div>
                        </div>
                    </div>
                </form>

                <Button bsStyle={"primary"} type={"submit"} onClick={this.onSubmit}>Submit</Button>
                <Button bsStyle={"dark"} href={`${ROUTING_URL_BASE}/missions`}>Cancel</Button>
            </div>
        )
    }
}