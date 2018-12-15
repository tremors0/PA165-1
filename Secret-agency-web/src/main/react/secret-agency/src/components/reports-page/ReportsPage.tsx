import * as React from "react";
import {IReport} from "../../types/Report";
import * as reportService from "../../services/reportService";

interface IReportPageState {
    reports: IReport[],
    isLoaded: boolean,
}

type IState = IReportPageState;

/**
 * Component for reports page.
 */
export class ReportsPage extends React.PureComponent<{}, IState> {

    constructor(props: {}) {
        super(props);

        this.state = {
            reports: [],
            isLoaded: false,
        }
    }

    public componentDidMount() {
        reportService.getAllReports().then((reports) => {
           this.setState((prevState) => ({...prevState, isLoaded: true, reports}));
        });
    }

    public render(): JSX.Element {
        if (!this.state.isLoaded) {
           return <div>Loading table...</div>;
        }

        const tableRows = this.state.reports.map((report) => (
            <tr key={report.id}>
                <td>{report.mission && report.mission.id}</td>
                <td>{report.agent && report.agent.codeName}</td>
                <td>{report.date}</td>
                <td>{report.missionResult}</td>
                <td>{report.reportStatus}</td>
            </tr>
           )
        );

        return (
            <div className={"table-wrapper"}>
                <table className={"data-table"}>
                    <thead>
                    <tr>
                        <th>Mission</th>
                        <th>Agent</th>
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
        );
    }
}
