package taxi;

import burlap.mdp.core.oo.ObjectParameterizedAction;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.oo.ObjectParameterizedActionType;
import taxi.state.TaxiState;
import taxi.state.TaxiPassenger;

public class PickupActionType extends ObjectParameterizedActionType {
    public PickupActionType(String name, String[] parameterClasses) {
        super(name, parameterClasses);
    }

    @Override
    protected boolean applicableInState(State s, ObjectParameterizedAction objectParameterizedAction) {
        TaxiState state = (TaxiState) s;
        String[] params = objectParameterizedAction.getObjectParameters();
        String passengerName = params[0];
        TaxiPassenger passenger = (TaxiPassenger)state.object(passengerName);

        // Can't pick up a passenger already in the taxi
        if((boolean)passenger.get(Taxi.ATT_IN_TAXI)) {
            return false;
        }

        int tx = (int)state.getTaxiAtt(Taxi.ATT_X);
        int ty = (int)state.getTaxiAtt(Taxi.ATT_Y);
        int px = (int)passenger.get(Taxi.ATT_X);
        int py = (int)passenger.get(Taxi.ATT_Y);

        return tx == px && ty == py;
    }
}

