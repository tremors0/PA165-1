import * as React from "react";
import "../SharedStyles.css";
import {ChangeEvent, MouseEvent} from 'react';
import {IDepartment} from "../../types/Department";
import {
    createDepartment,
    editDepartment,
    getAllDepartments,
    getSpecializations
} from "../../services/departmentService";
import {DepartmentShowRow} from "./DepartmentShowRow";
import {DepartmentEditRow} from "./DepartmentEditRow";
import * as Immutable from 'immutable';
import {defineAbility} from "../../config/ability";

interface IDepartmentsState {
    readonly departments: Immutable.Map<number, IDepartment>;
    readonly newDepartment: INewDepartmentState;
    readonly specializations: string[];
    readonly editedDepartmentId: number | null;
}

interface INewDepartmentState {
    readonly city: string;
    readonly country: string;
    readonly latitude: string;
    readonly longitude: string;
    readonly specialization: string;
}

export class DepartmentsPage extends React.Component<{}, IDepartmentsState> {
    public async componentDidMount() {
        const departments = Immutable.Map<number, IDepartment>((await getAllDepartments()).map(
            (department: IDepartment) => [
                department.id, department
            ]
        ));
        const specializations = await getSpecializations();
        const newDepartment = {
            city: '',
            country: '',
            latitude: '',
            longitude: '',
            specialization: '',
        };
        this.setState({
            departments,
            newDepartment,
            specializations,
            editedDepartmentId: null
        });
    };

    private addDepartment = async (event: MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        const {city, country, latitude, longitude, specialization} = this.state.newDepartment;
        const department: IDepartment = {
            id: 1, // temporary id, will be given from API
            city,
            country,
            latitude: parseFloat(latitude),
            longitude: parseFloat(longitude),
            specialization
        };
        const createdDepartment = await createDepartment(department);
        this.setState((prevState) => ({
            departments: prevState.departments.set(createdDepartment.id, createdDepartment)
        }));
    };

    private onCityChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const city = event.currentTarget.value;
        this.setState((prevState) => ( {newDepartment:  { ...prevState.newDepartment, city }}));
    };

    private onCountryChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const country = event.currentTarget.value;
        this.setState((prevState) => ( {newDepartment:  { ...prevState.newDepartment, country }}));
    };

    private onLatitudeChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const latitude = event.currentTarget.value;
        this.setState((prevState) => ( {newDepartment:  { ...prevState.newDepartment, latitude }}));
    };

    private onLongitudeChange = (event: ChangeEvent<HTMLInputElement>): void => {
        const longitude = event.currentTarget.value;
        this.setState((prevState) => ( {newDepartment:  { ...prevState.newDepartment, longitude }}));
    };

    private onSpecializationChange = (event: ChangeEvent<HTMLSelectElement>): void => {
        const specialization = event.currentTarget.value;
        this.setState((prevState) => ( {newDepartment:  { ...prevState.newDepartment, specialization }}));
    };

    private startEditDepartment = (editedDepartmentId: number): void => {
        this.setState(_ => ({
            editedDepartmentId,
        }))
    };

    private onCancelEditing = (): void => {
        this.setState(_ => ({editedDepartmentId: null}));
    };

    private onEdit = async (department: IDepartment) => {
        const editedDepartment = await editDepartment(department);
        console.log(editedDepartment);
        this.setState((prevState) => ({
            departments: prevState.departments.set(editedDepartment.id, editedDepartment)
        }));
        this.setState(_ => ({editedDepartmentId: null}));
    };

    public render() {
        if (this.state) {
            const { departments } = this.state;
            const tableRows = departments.keySeq().map((key: number) =>
                this.state.editedDepartmentId === key ?
                    <DepartmentEditRow
                        key={key}
                        specializations={this.state.specializations}
                        department={departments.get(key)}
                        onCancelEdit={this.onCancelEditing}
                        onEdit={this.onEdit}
                    /> :
                <DepartmentShowRow
                    key={key}
                    department={departments.get(key)}
                    onStartEdit={this.startEditDepartment.bind(this, key)}
                />
            );
            const {city, country,latitude, longitude, specialization} = this.state.newDepartment;
            return (
                <div className="table-wrapper">
                    <table className="data-table">
                        <thead>
                        <tr>
                            <th>City</th>
                            <th>Country</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Specialization</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableRows}
                        {defineAbility().can('create', 'Department') &&
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
                                    <option value={""}/>
                                    {this.state.specializations.map((specializationOption: string) =>
                                        <option key={specializationOption}
                                                value={specializationOption}>{specializationOption}</option>
                                    )}
                                </select>
                            </td>
                            <td>
                                <button
                                    className={"btn btn-primary"}
                                    type={'submit'}
                                    value={'create'}
                                    onClick={this.addDepartment}
                                >
                                    Create
                                </button>
                            </td>
                        </tr>
                        }
                        </tbody>
                    </table>
                </div>
            )
        } else {
            return (
                <div>Loading table...</div>
            )
        }
    }
}
