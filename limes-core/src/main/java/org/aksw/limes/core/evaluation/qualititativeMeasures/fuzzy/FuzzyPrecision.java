package org.aksw.limes.core.evaluation.qualititativeMeasures.fuzzy;

import org.aksw.limes.core.datastrutures.GoldStandard;
import org.aksw.limes.core.evaluation.qualititativeMeasures.IQualitativeMeasure;
import org.aksw.limes.core.io.mapping.AMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mohamed Sherif (sherif@informatik.uni-leipzig.de)
 * @version 1.1.2
 */
public class FuzzyPrecision extends AFuzzeyMeasures implements IQualitativeMeasure {
    static Logger logger = LoggerFactory.getLogger(FuzzyPrecision.class);

    /** Denominator
     * The method calculates the fuzzy precision of the machine learning predictions compared to a gold standard
     * @param predictions The predictions provided by a machine learning algorithm
     * @param goldStandard It contains the gold standard (reference mapping) combined with the source and target URIs
     * @return double - This returns the calculated fuzzy precision
     */
    @Override
    public double calculate(AMapping predictions, GoldStandard goldStandard) {

        double numerator = 0.0;
        double denuminator   = 0.0;

        for (String sUri : goldStandard.referenceMappings.getMap().keySet()){
            for (String tUri : goldStandard.referenceMappings.getMap().get(sUri).keySet()){
                    double xInYMu = predictions.getConfidence(sUri, tUri);
                    double xInXMu = goldStandard.referenceMappings.getConfidence(sUri, tUri);
                    double minMu = (xInXMu < xInYMu)? xInXMu : xInYMu;
                    numerator += (minMu > 0.0) ? xInYMu : 0.0;
                    denuminator += xInYMu;
            }
        }
        return (numerator == 0 && denuminator == 0) ? 0 : (numerator / denuminator);
    }
    
//    @Override
//    public double calculate(AMapping predictions, GoldStandard goldStandard) {
//
//        double num = 0d;
//        double denum   = 0d;
//
//        for (String sUri : predictions.getMap().keySet()){
//            for (String tUri : predictions.getMap().get(sUri).keySet()){
//                    double xInYMu = goldStandard.referenceMappings.getConfidence(sUri, tUri);
//                    double xInXMu = predictions.getConfidence(sUri, tUri);
//                    double minMu = (xInXMu < xInYMu)? xInXMu : xInYMu;
//                    num += (minMu > 0) ? xInYMu : 0;
//                    denum += xInYMu;
//            }
//        }
//        return (num == 0 && denum == 0) ? 0 : (num / denum);
//    }

}