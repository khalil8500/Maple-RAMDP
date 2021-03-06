package taxi.hierarchies.tasks.root;

import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import taxi.Taxi;
import taxi.hierarchies.tasks.root.state.RootStateMapper;
import taxi.hierarchies.tasks.root.state.TaxiRootPassenger;
import taxi.stateGenerator.TaxiStateFactory;

public class TaxiRootDomain implements DomainGenerator {

	//passenger attributes
	public static final String ATT_GOAL_LOCATION =			"goalLocation";
	public static final String ATT_CURRENT_LOCATION = 		"currentLocation";

	public static final String IN_TAXI =					"inTaxi";

	//actions
	public static final String ACTION_GET = 				"get";
	public static final String ACTION_PUT = 				"put";

	private RewardFunction rf;
	private TerminalFunction tf;

	/**
	 * creates a abstraction 2 taxi domain
	 * @param rf reward function
	 * @param tf terminal function
	 */
	public TaxiRootDomain(RewardFunction rf, TerminalFunction tf) {
		this.rf = rf;
		this.tf = tf;
	}
	
	/**
	 * creates a abstraction 2 taxi domain
	 */
	public TaxiRootDomain() {
		this.tf = new TaxiRootTerminalFunction();
		this.rf = new GoalBasedRF(tf);
	}

	@Override
	public OOSADomain generateDomain() {
		OOSADomain domain = new OOSADomain();
		
		domain.addStateClass(Taxi.CLASS_PASSENGER, TaxiRootPassenger.class);

		TaxiRootModel tmodel = new TaxiRootModel();
		FactoredModel model = new FactoredModel(tmodel, rf, tf);
		domain.setModel(model);
		
		domain.addActionTypes(
				new GetActionType(ACTION_GET, new String[]{Taxi.CLASS_PASSENGER}),
				new PutActionType(ACTION_PUT, new String[]{Taxi.CLASS_PASSENGER})
		);
		
		return domain;
	}

	public static void main(String[] args) {

		TaxiRootDomain taxiBuild = new TaxiRootDomain();
		OOSADomain domain = taxiBuild.generateDomain();
		
		HashableStateFactory hs = new SimpleHashableStateFactory();
		ValueIteration vi = new ValueIteration(domain, 0.5, hs, 0.01, 10);
		
		State base = TaxiStateFactory.createClassicState();
		RootStateMapper map = new RootStateMapper();
		State L2s = map.mapState(base);

		SimulatedEnvironment env = new SimulatedEnvironment(domain, L2s);
		Policy p = vi.planFromState(L2s);
		Episode e = PolicyUtils.rollout(p, env);
		System.out.println(e.actionSequence);
	}

}
