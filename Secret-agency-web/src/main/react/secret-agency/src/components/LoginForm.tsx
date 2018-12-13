import * as React from "react";
import {ChangeEvent, FormEvent} from "react";
import "./LoginForm.css";

interface ILoginPageStateProps {
  readonly loginError: string;
}

interface ILoginPageDispatchProps {
  onLogin(userName: string, password: string): void;
  hideAuthenticationError(): void;
}

interface ILoginPageState {
  // codeName/username
  readonly codeName: string;
  readonly password: string;
}

type IState = ILoginPageState;
type IProps = ILoginPageDispatchProps & ILoginPageStateProps;

/**
 * Login page component.
 */
export class LoginPage extends React.PureComponent<IProps, IState> {

  private onSubmit = (e: FormEvent): void => {
      e.preventDefault();
      this.props.onLogin(this.state.codeName, this.state.password);
  };

  private onChange = (e: ChangeEvent<HTMLInputElement>): void => {
      const value = e.target.value;
      const name = e.target.name;
      this.setState((prevState) => {
          return {...prevState, [name]: value};
      });

      // clear error messages when user types something
      if (this.props.loginError) {
        this.props.hideAuthenticationError();
      }
  };

  constructor(props: IProps) {
      super(props);

      this.state = {
          codeName: '',
          password: '',
      };
  }

  public render(): JSX.Element {
    return (
      <div className={'LoginPage'}>
        <form className={'LoginPage__form'} onSubmit={this.onSubmit}>
          <div className={'avatar'}>
            <span className={'glyphicon glyphicon-user'}/>
          </div>
          {this.props.loginError !== "" && (
            <div className={'LoginPage__error text-danger'}>{this.props.loginError}</div>
          )}
          <div className={'form-group'}>
            <label htmlFor={'emailInput'} className={'label-top'}>Codename:</label>
            <input value={this.state.codeName}
                   name={'codeName'} className={'form-control'}
                   onChange={this.onChange}/>
          </div>
          <div className={'form-group'}>
            <label htmlFor={'passwordInput'} className={'label-top'}>Password:</label>
            <input type={'password'} value={this.state.password}
                   name={'password'} className={'form-control'}
                   onChange={this.onChange}/>
          </div>
          <input type={'submit'} value={'Login'} className={'btn btn-primary block'}/>
        </form>
      </div>
    );
  }
}
