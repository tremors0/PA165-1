import {IDepartment} from "../../types/Department";
import * as React from 'react';
import {ChangeEvent, MouseEvent} from "react";

interface IState {
    readonly department: IDepartment
}

interface IProps {
    readonly specializations: string[];
    readonly department: IDepartment;
    readonly onCancelEdit: () => void;
    readonly onEdit: (department: IDepartment) => void;
}

export class DepartmentEditRow extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);
        const {department} = props;
        this.state = {
            department,
        }
    }
    private onCityChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const city = event.currentTarget.value;
        this.setState((prevState) => ( { department: {...prevState.department, city }}));
    };

    private onCountryChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const country = event.currentTarget.value;
        this.setState((prevState) => ( {department:  { ...prevState.department, country }}));
    };

    private onLatitudeChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const latitude = parseFloat(event.currentTarget.value);
        this.setState((prevState) => ( {department:  { ...prevState.department, latitude }}));
    };

    private onLongitudeChange = (event: ChangeEvent<HTMLInputElement>): void => {
        console.log(parseFloat(event.currentTarget.value));
        const longitude = parseFloat(event.currentTarget.value);
        this.setState((prevState) => ({department: {...prevState.department, longitude}}));
    };

    private onSpecializationChange = (event: ChangeEvent<HTMLSelectElement>): void => {
        const specialization = event.currentTarget.value;
        this.setState((prevState) => ( {department:  { ...prevState.department, specialization }}));
    };

    private edit = (event: MouseEvent<HTMLButtonElement>): void => {
        event.preventDefault();
        this.props.onEdit(this.state.department);
    };

    private cancelEditing = (event: MouseEvent<HTMLButtonElement>): void => {
        event.preventDefault();
        this.props.onCancelEdit();
    };

    public render() {
        const {city, country,latitude, longitude, specialization} = this.state.department;
        return (
            <tr>
                <td>
                    <input type={'text'} value={city}
                           onChange={this.onCityChange}
                    />
                </td>
                <td>
                    <input type={'text'} value={country}
                           onChange={this.onCountryChange}
                    />
                </td>
                <td>
                    <input type={'text'} value={latitude}
                           onChange={this.onLatitudeChange}
                    />
                </td>
                <td>
                    <input type={'text'} value={longitude}
                           onChange={this.onLongitudeChange}
                    />
                </td>
                <td>
                    <select value={specialization}
                            onChange={this.onSpecializationChange}
                    >
                        {this.props.specializations.map((specializationOption: string) =>
                            <option key={specializationOption} value={specializationOption}>{specializationOption}</option>
                        )}
                    </select>
                </td>
                <td>
                    <button
                        className={"btn btn-primary"}
                        type={'submit'}
                        value={'create'}
                        onClick={this.edit}
                    >
                        Create
                    </button>
                    <button
                        className={"btn btn-info"}
                        type={'submit'}
                        value={'cancel'}
                        onClick={this.cancelEditing}
                    >
                        Cancel
                    </button>
                </td>
            </tr>
        );
    }
}