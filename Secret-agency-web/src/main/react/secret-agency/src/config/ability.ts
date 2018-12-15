import {IAgent} from "../types/Agent";
import {Ability, AbilityBuilder} from "@casl/ability";

export const defineAbilityFor = (agent: IAgent): Ability => {
    const { rules, can } = AbilityBuilder.extract();
    if (agent.rank === 'AGENT_IN_CHARGE') {
        can('manage', 'all');
    } else {
        can('view', 'all');
    }
    return new Ability(rules);
};