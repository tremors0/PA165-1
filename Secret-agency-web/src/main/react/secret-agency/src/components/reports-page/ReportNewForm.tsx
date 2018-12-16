import * as React from "react";
import {FormEvent} from "react";
import {MissionResult} from "../../types/Report";
import {FormControl, FormGroup} from "react-bootstrap";

interface IReportNewFormOwnProps {
    readonly authenticatedAgentId: number;
}

interface IReportNewFormState {
    readonly text: string;
    readonly missionResult: MissionResult;
    readonly agentId: number | null;
    readonly missionId: number | null;
}

type IState = IReportNewFormState;
type IProps = IReportNewFormOwnProps;

export class ReportNewForm extends React.PureComponent<IProps, IState> {

    private onReportTextChange(e: FormEvent<FormControl>): void {
        const text = (e.target as HTMLTextAreaElement).value;
        this.setState((prevState) => ({...prevState, text}));
    }

    constructor(props: IProps) {
        super(props);

        this.state = {
            text: "",
            missionResult: "COMPLETED",
            agentId: null,
            missionId: null,
        }
    }


    public render(): JSX.Element {
        return (
            <div className={'ReportNewForm'}>
                <h2>Create new report</h2>
                <form>
                    <FormGroup controlId={'newReport'}>
                        <FormControl type={'textarea'}
                                     value={this.state.text}
                                     placeholder={'Enter report'} onChange={this.onReportTextChange}/>
                    </FormGroup>
                </form>
            </div>
        )
    }
}