package taxi.functions.rmaxq;

import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import taxi.Taxi;
import taxi.state.TaxiState;

public class BaseGetCompletedPF extends PropositionalFunction{ 
	//get is complete if desired passenger is in taxi  - no abstraction
	
	public BaseGetCompletedPF() {
		super("get", new String[]{Taxi.CLASS_PASSENGER});
	}
	
	@Override
	public boolean isTrue(OOState s, String... params) {
		String action = params[0];
		BaseGetActionType actyp = new BaseGetActionType();
		BaseGetActionType.GetAction a = actyp.associatedAction(action);
		TaxiState st = (TaxiState) s;
		
		return (boolean) st.getPassengerAtt(a.getPassenger(), Taxi.ATT_IN_TAXI);
	}
}
