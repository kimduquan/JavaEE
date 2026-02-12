// State of the agent, make sure this aligns with your agent's state.
export type AgentState = {
  progress?: {
    value?: number,
    max?: number
  };
};
