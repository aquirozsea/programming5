/*
 * RandomPointGenerator.java
 *
 * Copyright 2008 Andres Quiroz Hernandez
 *
 * This file is part of Programming5.
 * Programming5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Programming5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Programming5.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package programming5.math;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import programming5.arrays.ArrayOperations;

/**
 * Generates real-valued points (coordinates) in a multidimensional space around fixed geometries with random noise.
 * @author Andres Quiroz Hernandez
 * @version 6.01
 */
public class RandomPointGenerator {
    
    public static enum DistType {POINT, LINE, AREA};
    public static final int PROPORTION = 0;
    public static final int RANGE_MIN = 1;
    public static final int RANGE_MAX = 2;
    
    protected Random random;
    protected int dimensions;
    protected long[] range;
    protected float[] proportions = {1.0f};
    protected DistType[] declaredTypes = {DistType.AREA};
    protected int[] correspondence = null;
    // Point specs
    protected Vector<double[]> pointCenters = new Vector<double[]>();
    protected Vector<double[][][]> pointAngleDistributions = new Vector<double[][][]>();
    protected Vector<double[][]> pointWidthDistributions = new Vector<double[][]>();
    // Line specs
    protected Vector<double[]> lineCoefficients = new Vector<double[]>();
    protected Vector<double[]> lineIntercepts = new Vector<double[]>();
//    protected Vector<double[][][]> lineDistributions = new Vector<double[][][]>();
    protected Vector<double[][]> lineDomainDistributions = new Vector<double[][]>();
    protected Vector<double[][][]> lineWidthDistributions = new Vector<double[][][]>();
    // Area specs
    protected Vector<double[][][]> areaDistributions = new Vector<double[][][]>();
    
    /**
     *Initializes a new random point generator in a space with a limit for each dimension given each value of the given space range array.
     *The point values along each dimension can only take values between 0 and the corresponding limit.
     */
    public RandomPointGenerator(long[] spaceRange) {
        random = new Random(System.currentTimeMillis());
        dimensions = spaceRange.length;
        range = Arrays.copyOf(spaceRange, spaceRange.length);
    }
    
    /**
     *Initializes a new random point generator in a space with a limit for each dimension given each value of the given space range array.
     *The point values along each dimension can only take values between 0 and the corresponding limit. Uses the given long value to seed 
     *the random number generator.
     */
    public RandomPointGenerator(long randomSeed, long[] spaceRange) {
        random = new Random(randomSeed);
        dimensions = spaceRange.length;
        range = Arrays.copyOf(spaceRange, spaceRange.length);
    }
    
    /**
     *Declares that the given proportion (between 0 and 1) of all the points generated should be generated around the given coordinate center.
     *@throws IllegalArgumentException if the sum of all proportions declared exceeds 1.
     */
    public int declarePoint(float proportion, double[] center) throws IllegalArgumentException {
        int pointIndex = pointCenters.size();
        newDeclaration(pointIndex, proportion, DistType.POINT);
        double[] pointCenter = Arrays.copyOf(center, dimensions);
        pointCenters.add(pointCenter);
        pointAngleDistributions.add(new double[dimensions-1][][]);
        pointWidthDistributions.add(null);
        return pointIndex;
    }
    
    /**
     *Specifies a distribution of points in angle ranges around the center of a declared point for a given dimension (e.g. 50% of points 
     *between 0 and 75 degrees and 25% between 90 and 95 degrees on X) --> (index, 0, {{0.5, 0, 75}, {0.25, 90, 95}}).
     *<p> Any proportion not specified will be generated uniformly.
     *@param pointIndex the point declaration to which the distribution applies (in the order of declaration from 0, not counting declarations 
     *of other geometries)
     *@param dimension the coordinate dimension to which the distribution applies for the given point declaration
     *@param angleDistribution list of distribution triples of the form (proportion, min, max), which indicates that the given proportion 
     *of points (of the total for this declaration) should be generated within the limits given by [min, max], relative to the point 
     *center
     */
    public void setPointAngleDistribution(int pointIndex, int dimension, double[]... angleDistribution) {
        double[][] pointDistribution = new double[angleDistribution.length][];
        for (int i = 0; i < angleDistribution.length; i++) {
            pointDistribution[i] = Arrays.copyOf(angleDistribution[i], 3);
        }
        pointAngleDistributions.elementAt(pointIndex)[dimension] = pointDistribution;
    }
    
    /**
     *Specifies a distribution of points in width ranges around the center of a declared point (e.g. 50% of points 
     *between 0 and 75 units from the center and 25% between 90 and 95 units from the center) --> (index, {{0.5, 0, 75}, {0.25, 90, 95}}).
     *<p> Any proportion not specified will be generated uniformly.
     *@param pointIndex the point declaration to which the distribution applies (in the order of declaration from 0, not counting declarations 
     *of other geometries)
     *@param widthDistribution list of distribution triples of the form (proportion, min, max), which indicates that the given proportion 
     *of points (of the total for this declaration) should be generated within the limits given by [min, max], relative to the point 
     *center
     */
    public void setPointWidthDistribution(int pointIndex, double[]... widthDistribution) {
        double[][] pointDistribution = new double[widthDistribution.length][];
        for (int i = 0; i < widthDistribution.length; i++) {
            pointDistribution[i] = Arrays.copyOf(widthDistribution[i], 3);
        }
        pointWidthDistributions.setElementAt(pointDistribution, pointIndex);
    }
    
    /**
     *Declares that the given proportion (between 0 and 1) of all the points generated should be generated around the given line 
     *description (set of coefficients in a canonical line equation).
     *@throws IllegalArgumentException if the sum of all proportions declared exceeds 1.
     */
    public int declareLine(float proportion, double[] coefficients, double[] intercepts) throws IllegalArgumentException {
        int lineIndex = lineCoefficients.size();
        newDeclaration(lineIndex, proportion, DistType.LINE);
        lineCoefficients.add(Arrays.copyOf(coefficients, dimensions-1));
        lineIntercepts.add(Arrays.copyOf(intercepts, dimensions-1));
//        lineDistributions.add(new double[dimensions][][]);
        lineDomainDistributions.add(null);
        lineWidthDistributions.add(new double[dimensions][][]);
        return lineIndex;
    }
    
    /**
     *Specifies a distribution of points along each of the independent dimensions for a declared line (e.g.
     *100% of points between 50 and 100 on X, and 50% of points between 205 and 250 and 50% between 250 and
     *255 on Y, for a line in three dimensions). The dependent dimension is completely determined by the
     *line equation in the declaration.
     *<p> Any proportion not specified will be generated uniformly.
     *@param lineIndex the line declaration to which the distribution applies (in the order of declaration from 0, not counting declarations
     *of other geometries)
     *@param dimension the coordinate dimension to which the distribution applies for the given line declaration
     *@param distribution list of distribution triples of the form (proportion, min, max), which indicates that
     *the given proportion of points (of the total for this declaration) should be generated within the limits
     *given by [min, max].
     */
    public void setLineDomainDistribution(int lineIndex, double[]... distribution) {
        double[][] lineDomainDistribution = new double[distribution.length][];
        for (int i = 0; i < distribution.length; i++) {
            lineDomainDistribution[i] = Arrays.copyOf(distribution[i], 3);
        }
        lineDomainDistributions.setElementAt(lineDomainDistribution, lineIndex);
    }

    /**
     *Specifies a distribution of points with respect to a declared line (e.g.
     *100% of points between 0 and 10 from the line on X, and 50% of points between 5 and 15 and 50% between
     *15 and 20 from the line on Y, for a line in two dimensions).
     *<p> Any proportion not specified will be generated uniformly
     *@param lineIndex the line declaration to which the distribution applies (in the order of declaration from 0, not counting declarations
     *of other geometries)
     *@param dimension the coordinate dimension to which the distribution applies for the given line declaration
     *@param distribution list of distribution triples of the form (proportion, min, max), which indicates that
     *the given proportion of points (of the total for this declaration) should be generated within the limits
     *given by [min, max], relative to the declared line.
     */
    public void setLineWidthDistribution(int lineIndex, int dimension, double[]... distribution) {
        double[][] lineWidthDistribution = new double[distribution.length][];
        for (int i = 0; i < distribution.length; i++) {
            lineWidthDistribution[i] = Arrays.copyOf(distribution[i], 3);
        }
        lineWidthDistributions.elementAt(lineIndex)[dimension] = lineWidthDistribution;
    }
    
    /**
     *Declares that the given proportion (between 0 and 1) of all the points generated should be generated within an area to be specified.
     *@throws IllegalArgumentException if the sum of all proportions declared exceeds 1.
     */
    public int declareArea(float proportion) throws IllegalArgumentException {
        int areaIndex = areaDistributions.size();
        newDeclaration(areaIndex, proportion, DistType.AREA);
        areaDistributions.add(new double[dimensions][][]);
        return areaIndex;
    }
    
    /**
     *Specifies a distribution of points within ranges for each dimension (e.g. 50% of points 
     *between 0 and 75 units in the space on X and 25% between 90 and 95 units in the space on Y) --> (index, 0, {{0.5, 0, 75}}) (index, 1, {{0.25, 90, 95}}).
     *<p> Any proportion not specified will be generated uniformly.
     *@param areaIndex the area declaration to which the distribution applies (in the order of declaration from 0, not counting declarations 
     *of other geometries)
     *@param distribution list of distribution triples of the form (proportion, min, max), which indicates that the given proportion 
     *of points (of the total for this declaration) should be generated within the limits given by [min, max], relative to the space.
     */
    public void setAreaDistribution(int areaIndex, int dimension, double[]... distribution) {
        double[][] areaDistribution = new double[distribution.length][];
        for (int i = 0; i < distribution.length; i++) {
            areaDistribution[i] = Arrays.copyOf(distribution[i], 3);
        }
        areaDistributions.elementAt(areaIndex)[dimension] = areaDistribution;
    }
    
    /**
     *Generates the given number of points (coordinates) according to the distributions given.
     */
    public Vector<double[]> generate(int number) {
        Vector<double[]> points = new Vector<double[]>();
        for (int i = 0; i < number; i++) {
            double[] nextPoint = new double[dimensions];
            // Find declaration by which point will be generated
            float toss = random.nextFloat();
            int declaration = 0;
            float accum = 0;
            for (int dc = 0; dc < proportions.length; dc++) {
                if (toss < accum + proportions[dc]) {
                    declaration = dc;
                    break;
                }
                accum += proportions[dc];
            }
            DistType distType = declaredTypes[declaration];
            switch (distType) {
                case POINT: {
                    nextPoint = Arrays.copyOf(pointCenters.elementAt(correspondence[declaration]), dimensions);
                    double[][] widthDistribution = pointWidthDistributions.elementAt(correspondence[declaration]);
                    double width;
                    if (widthDistribution != null) {
                        width = applyDistribution(widthDistribution);
                    }
                    else {
                        width = random.nextFloat() * range[0];
                    }
                    for (int dim = 0; dim < dimensions-1; dim++) {
                        double[][] angleDistribution = pointAngleDistributions.elementAt(correspondence[declaration])[dim];
                        double angle;
                        if (angleDistribution != null) {
                            angle = applyDistribution(angleDistribution);
                        }
                        else {
                            angle = 360 * random.nextFloat();
                        }
                        nextPoint[dim] += width * Math.sin(Math.toRadians(angle));
                        if (dim == dimensions - 2) {
                            nextPoint[dimensions-1] += width * Math.cos(Math.toRadians(angle));
                        }
                    }
                    break;
                }
                case LINE: {
                    // Set line point
                    nextPoint = new double[dimensions];
                    double[][] coordDistribution = lineDomainDistributions.elementAt(correspondence[declaration]);
                    if (coordDistribution != null) {
                        nextPoint[0] = applyDistribution(coordDistribution);
                    }
                    else {
                        nextPoint[0] = range[0] * random.nextFloat();
                    }
                    double[] coefficients = lineCoefficients.elementAt(correspondence[declaration]);
                    double[] intercepts = lineIntercepts.elementAt(correspondence[declaration]);
                    for (int dim = 1; dim < dimensions; dim++) {
                        nextPoint[dim] = coefficients[dim-1] * nextPoint[0] + intercepts[dim-1];
                    }
                    // Perturb line point
                    for (int dim = 0; dim < dimensions; dim++) {
                        double[][] widthDistribution = lineWidthDistributions.elementAt(correspondence[declaration])[dim];
                        if (widthDistribution != null) {
                            nextPoint[dim] += applyDistribution(widthDistribution);
                        }
                        else {
                            nextPoint[dim] += random.nextFloat() * (range[dim] - nextPoint[dim]);
                        }
                    }
                    break;
                }
                case AREA: {
                    nextPoint = new double[dimensions];
                    for (int dim = 0; dim < dimensions; dim++) {
                        nextPoint[dim] = random.nextFloat() * range[dim];
                        if (declaration < declaredTypes.length-1) {
                            double[][] sideDistribution = areaDistributions.elementAt(correspondence[declaration])[dim];
                            if (sideDistribution != null) {
                                nextPoint[dim] = applyDistribution(sideDistribution);
                            }
                        }
                    }
                    break;
                }
            }
            points.add(nextPoint);
        }
        return points;
    }
    
    private void newDeclaration(int index, float proportion, DistType type) {
        proportions = ArrayOperations.insert(proportion, proportions, proportions.length-1);
        proportions[proportions.length-1] -= proportions[proportions.length-2];
        if (proportions[proportions.length-1] < 0) {
            throw new IllegalArgumentException("RandomPointGenerator: Sum of proportions for declared objects exceeds 1");
        }
        DistType[] auxType = new DistType[declaredTypes.length + 1];
        ArrayOperations.arrayCast(auxType, ArrayOperations.insert(type, declaredTypes, declaredTypes.length-1));
        declaredTypes = auxType;
        correspondence = ArrayOperations.addElement(index, correspondence);
    }
    
    private double applyDistribution(double[][] distribution) {
        float toss = random.nextFloat();
        float accum = 0;
        double ret = 0;
        for (int r = 0; r < distribution.length; r++) {
            if (toss < accum + distribution[r][PROPORTION]) {
                double min = distribution[r][RANGE_MIN];
                double max = distribution[r][RANGE_MAX];
                ret = min + random.nextFloat() * (max - min);
                break;
            }
            accum += distribution[r][PROPORTION];
        }
        return ret;
    }
    
}
