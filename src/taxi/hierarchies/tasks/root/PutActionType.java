package taxi.hierarchies.tasks.root;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.oo.ObjectParameterizedAction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.oo.ObjectParameterizedActionType;
import taxi.hierarchies.tasks.root.state.TaxiRootState;

import static taxi.hierarchies.tasks.root.TaxiRootDomain.ATT_CURRENT_LOCATION;
import static taxi.hierarchies.tasks.root.TaxiRootDomain.IN_TAXI;

public class PutActionType  extends ObjectParameterizedActionType {

	public PutActionType(String name, String[] parameterClasses) {
		super(name, parameterClasses);
	}

	@Override
	protected boolean applicableInState(State s, ObjectParameterizedAction objectParameterizedAction) {
		OOState state = (OOState) s;
		String[] params = objectParameterizedAction.getObjectParameters();
		String passengerName = params[0];
		ObjectInstance passenger = state.object(passengerName);
		return passenger.get(ATT_CURRENT_LOCATION).equals(IN_TAXI);
	}

}
