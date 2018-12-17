import * as React from "react";
import {IReport} from "../../types/Report";
import * as reportService from "../../services/reportService";
import {Link, Redirect} from "react-router-dom";
import {RouteComponentProps} from "react-router";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {Button} from "react-bootstrap";
import "./ReportsPage.css";

type NavigateToDetailMode = "EDIT" | "READ-ONLY";

interface IReportsPageOwnProps {
    isAuthenticatedUserAdmin: boolean;
    authenticatedUserId: number;
}

interface IReportPageState {
    reports: IReport[],
    isLoaded: boolean,
    navigateToDetailId: number,
    navigateToDetailMode: NavigateToDetailMode;
}

type IState = IReportPageState;
type IProps = IReportsPageOwnProps & RouteComponentProps<{}>;

/**
 * Component for reports page.
 *
 * @author Jan Pavlu (487548)
 */
export class ReportsPage extends React.PureComponent<IProps, IState> {

    /********************************************************
     * EVENT HANDLERS
     *******************************************************/
    private onRemoveReport = async (reportId: number): Promise<void> => {
        const wasDeleted = await reportService.deleteReport(reportId);
        if (wasDeleted) {
            const reports = this.state.reports.filter((report) => report.id !== reportId);
            this.setState(prevState => ({...prevState, reports}));
        }
    };

    private onShowDetail = (reportId: number): void => {
        this.setState(prevState => ({...prevState, navigateToDetailId: reportId,
            navigateToDetailMode: "READ-ONLY"}));
    };

    private onEditReport = (reportId: number): void => {
        this.setState(prevState => ({...prevState, navigateToDetailId: reportId, navigateToDetailMode:"EDIT"}))
    };

    /********************************************************
     * LIFE-CYCLE METHODS
     *******************************************************/
    constructor(props: IProps) {
        super(props);

        this.state = {
            reports: [],
            isLoaded: false,
            navigateToDetailId: -1, // -1 means don't navigate
            navigateToDetailMode: "READ-ONLY",
        }
    }

    public componentDidMount() {
        reportService.getAllReports().then((reports) => {
            const displayedReports = this.filterReports(reports);
            this.setState((prevState) => ({...prevState, isLoaded: true, reports: displayedReports}));
        });
    }

    /********************************************************
     * RENDERING
     *******************************************************/
    /**
     * Admin can view all reports. User can view only his own reports. Returns filtered reports.
     * @param reports reports
     */
    private filterReports(reports: IReport[]): IReport[] {
        if (!this.props.isAuthenticatedUserAdmin) {
            return reports.filter((report) => report.agent.id === this.props.authenticatedUserId);
        }
        return reports;
    }
    
    private getActionsForReport(report: IReport) {
        const canUserEdit = report.agent.id === this.props.authenticatedUserId && report.reportStatus !== "APPROVED";
        if (canUserEdit || this.props.isAuthenticatedUserAdmin) {
            return (
                <div className={'ReportsPage__actions'}>
                    <Button bsStyle={'info'} onClick={() => this.onShowDetail(report.id)}>View</Button>
                    <Button bsStyle={'success'} onClick={() => this.onEditReport(report.id)}>Edit</Button>
                    <Button bsStyle={'danger'} onClick={() => this.onRemoveReport(report.id)}>Delete</Button>
                </div>
            );
        }

        // authenticated agent is not admin or Report author
        return (
            <div className={'ReportsPage__actions'}>
                <Button bsStyle={'info'}>View</Button>
            </div>
        );
    }

    private getRedirectUrl(): string {
        const currentPath = this.props.match.path;
        const detailId = this.state.navigateToDetailId;
        const mode = this.state.navigateToDetailMode;

        const urlBase = `${currentPath}/report/${detailId}`;
        return mode === "READ-ONLY" ? urlBase : urlBase + `/edit`;

    }

    public render(): JSX.Element {
        const detailId = this.state.navigateToDetailId;
        // go to detail
        if (detailId !== -1) {
            return <Redirect to={this.getRedirectUrl()}/>;
        }

        if (!this.state.isLoaded) {
           return <div>Loading table...</div>;
        }

        const isAnyRowVisible = this.state.reports.length !== 0;

        if (!isAnyRowVisible) {
            return <div className={'alert alert-info'}>There are no reports from current user</div>;
        }

        const tableRows = this.state.reports.map((report) => (
            <tr key={report.id}>
                <td>{report.mission && report.mission.id}</td>
                {this.props.isAuthenticatedUserAdmin && <td>{report.agent.codeName}</td>}
                <td>{report.date}</td>
                <td>{report.missionResult}</td>
                <td>{report.reportStatus}</td>
                <td>{this.getActionsForReport(report)}</td>
            </tr>
           )
        );

        return (
            <div className={'ReportsPage'}>
                <div className={"table-wrapper"}>
                    <table className={"data-table"}>
                        <thead>
                        <tr>
                            <th>Mission</th>
                            {this.props.isAuthenticatedUserAdmin && <th>Agent</th>}
                            <th>Date</th>
                            <th>Mission result</th>
                            <th>Report status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableRows}
                        </tbody>
                    </table>
                </div>
                <Button bsStyle="primary" className={'ReportsPage__createNewReport'}>
                    <Link to={`${ROUTING_URL_BASE}/reports/new`}>Create report</Link>
                </Button>
            </div>
        );
    }
}
