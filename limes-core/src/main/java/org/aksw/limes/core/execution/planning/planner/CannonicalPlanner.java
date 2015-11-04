package org.aksw.limes.core.execution.planning.planner;

import java.util.ArrayList;
import java.util.List;

import org.aksw.limes.core.execution.planning.plan.ExecutionPlan;
import org.aksw.limes.core.execution.planning.plan.Instruction;
import org.aksw.limes.core.execution.planning.plan.Instruction.Command;
import org.aksw.limes.core.measures.mapper.SetOperations.Operator;


/**
 *
 * Impelements Cannonical Planner class.
 * 
 * @author ngonga
 * @author kleanthi
 */
public class CannonicalPlanner extends ExecutionPlanner {

    public CannonicalPlanner() {

    }

    /**
     * Generates a nested instructionList for a link spec
     *
     * @param spec
     *            Input spec
     * @return Nested instructionList
     */
    public ExecutionPlan plan(LinkSpec spec) {
	ExecutionPlan plan = new ExecutionPlan();
	plan.instructionList = new ArrayList<Instruction>();
	// atomic specs are simply ran
	if (spec.isAtomic()) {

	    // nested plan have a null instruction list as default
	    plan.instructionList = new ArrayList<Instruction>();
	    plan.addInstruction(new Instruction(Instruction.Command.RUN, spec.getFilterExpression(),
		    spec.threshold + "", -1, -1, 0));
	} else {
	    List<ExecutionPlan> children = new ArrayList<ExecutionPlan>();
	    // set childrean
	    for (LinkSpec child : spec.children) {
		children.add(plan(child));
	    }
	    // logger.info("Added "+children.size()+" subplans");
	    plan.subPlans = children;
	    // set operator

	    if (spec.operator.equals(Operator.AND)) {
		plan.operator = Command.INTERSECTION;
	    } else if (spec.operator.equals(Operator.OR)) {
		plan.operator = Command.UNION;
	    } else if (spec.operator.equals(Operator.XOR)) {
		plan.operator = Command.XOR;
	    } else if (spec.operator.equals(Operator.MINUS)) {
		plan.operator = Command.DIFF;
	    }
	    plan.filteringInstruction = new Instruction(Command.FILTER, spec.getFilterExpression(), spec.threshold + "",
		    -1, -1, 0);
	}
	// logger.info("Generated the following instructionList:\n"+plan);

	return plan;

    }

    
}
