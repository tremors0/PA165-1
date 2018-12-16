import * as React from 'react';
import {Alert} from 'react-bootstrap';

interface IAlertCloseableStateProps {
    isVisible: boolean;
    bsStyle: "danger" | "warning" | "success" | "info" | undefined;
}

interface IAlertCloseableDispatchProps {
    onHide?: () => void;
}

interface IAlertCloseableState {
    isVisible: boolean;
}

type IProps = IAlertCloseableStateProps & IAlertCloseableDispatchProps;
type IState = IAlertCloseableState;

/**
 * Component for alert. Alert can be closed.
 *
 * @author Jan Pavlu (487548)
 */
export class AlertCloseable extends React.PureComponent<IProps, IState> {

    private onHide = (): void => {
        this.setState(prevState => ({...prevState, isVisible: false}));
        this.props.onHide != null && this.props.onHide();
    };

    constructor(props: IProps) {
        super(props);

        this.state = {
            isVisible: false,
        }
    }

    public componentWillReceiveProps(newProps: IProps) {
        this.setState(prevState => ({...prevState, isVisible: newProps.isVisible}))
    }

    public render(): JSX.Element | null {
        if (!this.state.isVisible) {
            return null;
        }

        return (
            <Alert bsStyle={this.props.bsStyle || "info"} onDismiss={this.onHide} className={'AlertCloseable'}>
                {this.props.children}
            </Alert>
        );
    }
}
