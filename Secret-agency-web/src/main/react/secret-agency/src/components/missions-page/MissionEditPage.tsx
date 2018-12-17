import * as React from "react";

interface IMissionEditPageState {
    test: boolean;
}

interface IMissionEditPageProps {
    missionId: number;
    isAuthenticatedUserAdmin: boolean;
}

type IProps = IMissionEditPageProps;
type IState = IMissionEditPageState;

/**
 * Form for editing mission.
 *
 * @author Milos Silhar (433614)
 */
export class MissionEditPage extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props)
    }

    public render() {
        return (
            <div>
                {this.props.missionId} is doing this and is admin {this.props.isAuthenticatedUserAdmin ? "YES" : "NO"}
            </div>
        )
    }
}