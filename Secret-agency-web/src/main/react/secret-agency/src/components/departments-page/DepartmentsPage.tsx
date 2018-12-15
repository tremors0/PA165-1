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

interface IDepartmentsState {
    readonly departments: IDepartment[];
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

export class DepartmentsPage extends React.Component<any, IDepartmentsState> {
    public async componentDidMount() {
        const departments = await getAllDepartments();
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
    constructor(props: any) {
        super(props);
    }

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
            departments: [...prevState.departments, createdDepartment]
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
        console.log(department);
        const editedDepartment = await editDepartment(department);
        console.log(editedDepartment);
    };

    public render() {
        if (this.state) {
            const tableRows = this.state.departments.map(department =>
                this.state.editedDepartmentId === department.id ?
                    <DepartmentEditRow
                        key={department.id}
                        specializations={this.state.specializations}
                        department={department}
                        onCancelEdit={this.onCancelEditing}
                        onEdit={this.onEdit}
                    /> :
                <DepartmentShowRow
                    key={department.id}
                    department={department}
                    onStartEdit={this.startEditDepartment.bind(this, department.id)}
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
                                        {this.state.specializations.map((specializationOption: string) =>
                                            <option key={specializationOption} value={specializationOption}>{specializationOption}</option>
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
