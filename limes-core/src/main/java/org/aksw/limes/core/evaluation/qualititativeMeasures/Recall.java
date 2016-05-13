package org.aksw.limes.core.evaluation.qualititativeMeasures;

import org.aksw.limes.core.datastrutures.GoldStandard;
import org.aksw.limes.core.io.mapping.Mapping;

/**
 * It measures how far the algorithm retrieved correct results out of the all existed correct results. 
 * It is defined to be the ratio between the true positive to the total number of correct results retrieved or not
 * @author Mofeed Hassan <mounir@informatik.uni-leipzig.de>
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * @version 1.0
 *
 */
public class Recall extends PRF implements IQualitativeMeasure {

	@Override
	public double calculate(Mapping predictions, GoldStandard goldStandard) {
		if(predictions.size()==0)
			return 0;
		return trueFalsePositive(predictions, goldStandard.goldStandard, true)/(double)goldStandard.goldStandard.size();

	}

}