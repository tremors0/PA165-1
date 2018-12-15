import {Ability, AbilityBuilder} from "@casl/ability";
import {loadAuthenticatedAgent} from "../repository/secretAgecyRepository";

export const defineAbility = (): Ability => {
    const agent = loadAuthenticatedAgent();

    const { rules, can, cannot } = AbilityBuilder.extract();
    if (agent === null || agent === undefined) {
        cannot('view', 'anything');
    } else if (agent.rank === 'AGENT_IN_CHARGE') {
        can('edit', 'all');
        can('manage', 'all');
    } else {
        can('view', 'all');
        cannot('create', 'Department');
        cannot('edit', 'Department');
    }
    return new Ability(rules);
};