package Assignment3;

import java.util.Arrays;

public class FastCollinearPoints {

    //private final Map<String, LineSegment> segments;
    private LineSegment[] segments;
    private Point[] myPoints;
    private int lineSegmentCount;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        this.lineSegmentCount = 0;
        this.segments = new LineSegment[1];
        this.myPoints = Arrays.copyOf(points, points.length);
        computeSegments(points, myPoints);
    }

    /**
     * Computes existing number of collinear points in the given array.
     *
     * @param points
     */
    private void computeSegments(Point[] points, Point[] myPoints) {
        for (int i = 0; i < myPoints.length; i++) {
            final Point presentPoint = myPoints[i];
            //segments.add(points[i]);
            Arrays.sort(points, presentPoint.slopeOrder());

            findAndAddLineSegment(points);
            /*
            for (int j = i + 1; j < points.length; j++) {
                System.out.println("slope between "
                        + presentPoint.toString() + " - " + points[j].toString() + "->"
                        + points[i].slopeTo(points[j]) + " ,");
            }*/


        }
    }

    private void findAndAddLineSegment(Point[] points) {

        int startIndex = 1;
        OuterLoop:
        for (int i = 2; i < points.length; i++) {
            final double currentSlope = points[0].slopeTo(points[startIndex]);
            if (points[0].slopeTo(points[i]) == currentSlope) {
                if (i + 1 != points.length)
                    continue;
                else
                    i++;
            }

            int range = i - startIndex;
            if (range >= 3) {
                int copyCounter = startIndex;
                Point[] temp = new Point[range + 1];
                temp[0] = points[0];
                for (int k = 1; k < temp.length; k++) {
                    temp[k] = points[copyCounter++];
                }
                final Point min = findMin(temp);
                final Point max = findMax(temp);
                final LineSegment lineSegment = new LineSegment(min, max);
                //System.out.println(lineSegment.toString());
                if (lineSegmentCount == segments.length) {
                    resize(2 * lineSegmentCount);
                }

                if (lineSegmentCount == 0) {
                    segments[lineSegmentCount++] = lineSegment;
                    startIndex = i;
                    continue;
                }
                for (int q = 0; q < lineSegmentCount; q++) {
                    if (segments[q].toString().equals(lineSegment.toString())) {
                        startIndex = i;
                        continue OuterLoop;
                    }
                }
                segments[lineSegmentCount++] = lineSegment;

            }
            startIndex = i;
        }


//        if (p)
//        Point[] temp = {points[p1], points[p2], points[p3], points[p4]};
//        Point min = temp[0];
//        Point max = temp[0];
//        for (int i = 0; i < 3; i++) {
//            if (min.compareTo(temp[i + 1]) > 0) {
//                min = temp[i + 1];
//            }
//            if (max.compareTo(temp[i + 1]) < 0) {
//                max = temp[i + 1];
//            }
//        }

//        final LineSegment lineSegment = new LineSegment(min, max);
//        return lineSegment;
    }

    private Point findMin(Point[] temp) {
        Point min = temp[0];
        for (int i = 1; i < temp.length; i++) {
            if (min.compareTo(temp[i]) > 0) {
                min = temp[i];
            }
        }
        return min;
    }

    private Point findMax(Point[] temp) {
        Point max = temp[0];
        for (int i = 1; i < temp.length; i++) {
            if (max.compareTo(temp[i]) < 0) {
                max = temp[i];
            }
        }
        return max;
    }


    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return lineSegmentCount;
    }

    /**
     * the line segments
     *
     * @return
     */
    public LineSegment[] segments() {
        return Arrays.copyOfRange(this.segments, 0, lineSegmentCount);
    }

    private void resize(int capacity) {
        final LineSegment[] copyItems = new LineSegment[capacity];

        for (int i = 0; i < lineSegmentCount; i++) {
            copyItems[i] = segments[i];
        }
        this.segments = copyItems;
    }
}
