import * as React from "react";
import * as reportService from "../../services/reportService";
import {RouteComponentProps} from "react-router";
import {IReport} from "../../types/Report";
import "./ReportDetail.css";
import {Alert, Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";

interface IReportDetailState {
    readonly isLoading: boolean;
    readonly loadingError: string;
}

type IProps = RouteComponentProps<{reportId: string}>;
type IState = IReportDetailState;

/**
 * Detail view for report.
 *
 * @author Jan Pavlu
 */
export class ReportDetail extends React.PureComponent<IProps, IState> {

    private displayedReport: IReport;

    /********************************************************
     * LIFE-CYCLE METHODS
     *******************************************************/
    constructor(props: IProps) {
        super(props);

        this.state = {
            isLoading: true,
            loadingError: "",
        }
    }

    public async componentDidMount(): Promise<void> {
        this.loadReport(parseInt(this.props.match.params.reportId, 10));
    }

    private async loadReport(reportId: number): Promise<void> {
        const response = await reportService.getReportById(reportId);
        if (typeof response === "string") {
            this.setState(prevState => ({...prevState, loadingError: response, isLoading: false}));
            return;
        }
        this.displayedReport = response;
        this.setState(prevState => ({...prevState, isLoading: false}));
    }

    /********************************************************
     * RENDERING
     *******************************************************/
    public render(): JSX.Element {
        if (this.state.isLoading) {
            return <div>Detail is loading</div>;
        }

        if (this.state.loadingError !== "") {
            return <Alert bsStyle={'danger'}>{this.state.loadingError}</Alert>
        }

        return (
            <div className={"ReportDetail"}>
                <Button bsStyle={"primary"}><Link to={`${ROUTING_URL_BASE}/reports`}>Back to reports</Link></Button>
                <hr />
                <h3>Detail of report {this.props.match.params.reportId}</h3>
                <div>Mission: <em className={"text-primary"}>{this.displayedReport.mission.name}</em></div>
                <div>Created at: <em>{this.displayedReport.date}</em></div>
                <div>Result: <em>{this.displayedReport.missionResult}</em></div>
                <div>Status: <em>{this.displayedReport.reportStatus}</em></div>
                <div>Text: <div className={"ReportDetail__text"}>{this.displayedReport.text}</div></div>
            </div>
        )
    }

}
