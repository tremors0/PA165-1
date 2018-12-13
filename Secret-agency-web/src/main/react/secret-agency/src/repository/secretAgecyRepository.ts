import {IAgent} from "../types/Agent";

const AUTHENTICATED_AGENT_KEY = 'AUTHENTICATED_AGENT';

/**
 * This method stores authenticated agent into local storage.
 * @param agent authenticated agent
 */
export function storeAuthenticatedAgent(agent: IAgent): void {
  localStorage.setItem(AUTHENTICATED_AGENT_KEY, JSON.stringify(agent));
}

/**
 * Load authenticated user from local storage. Returns null, when no
 *   authenticated user is found. Otherwise it returns authenticated agent.
 */
export function loadAuthenticatedAgent(): IAgent | null {
  const authenticatedAgentStr = localStorage.getItem(AUTHENTICATED_AGENT_KEY);
  if (authenticatedAgentStr == null) {
    return null;
  }
  return JSON.parse(authenticatedAgentStr);
}

/**
 * Removes agent from local storage after log out.
 */
export function removeAuthenticatedAgent(): void {
  localStorage.removeItem(AUTHENTICATED_AGENT_KEY)
}