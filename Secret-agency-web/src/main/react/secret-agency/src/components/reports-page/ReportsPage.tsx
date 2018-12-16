import * as React from "react";
import {IReport} from "../../types/Report";
import * as reportService from "../../services/reportService";
import {Link} from "react-router-dom";
import {ROUTING_URL_BASE} from "../../utils/requestUtils";
import {Button} from "react-bootstrap";
import "./ReportsPage.css";

interface IProps {
    isAuthenticatedUserAdmin: boolean;
    authenticatedUserId: number;
}

interface IReportPageState {
    reports: IReport[],
    isLoaded: boolean,
}

type IState = IReportPageState;

/**
 * Component for reports page.
 */
export class ReportsPage extends React.PureComponent<IProps, IState> {

    constructor(props: IProps) {
        super(props);

        this.state = {
            reports: [],
            isLoaded: false,
        }
    }

    public componentDidMount() {
        reportService.getAllReports().then((reports) => {
            const displayedReports = this.filterReports(reports);
            this.setState((prevState) => ({...prevState, isLoaded: true, reports: displayedReports}));
        });
    }

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

    public render(): JSX.Element {
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
                {this.props.isAuthenticatedUserAdmin && <td>{report.agent && report.agent.codeName}</td>}
                <td>{report.date}</td>
                <td>{report.missionResult}</td>
                <td>{report.reportStatus}</td>
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
