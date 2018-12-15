import * as React from "react";
import "./TopBar.css";
import {ITab} from "../../App";
import {NavLink} from "react-router-dom";

export function TopBar(props: any) {
    const tabs = props.tabs.map((tab: ITab, index: number) =>
        <li key={index}>
            <NavLink to={tab.link}>
                {tab.title}
            </NavLink>
        </li>
    );
    return (
        <div className="header-background">
            <h3 className="top-bar-header">Secret agency</h3>
            <ul>
                {tabs}
            </ul>
        </div>
    );
}