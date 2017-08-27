package Assignment3;

import java.util.*;

public class FastCollinearPoints {

    private final List<LineSegment> segments;
    private final Point[] copyPoints;
    private Map<Double, List<Point>> segmentTracker;
    private int lineSegmentCount;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Points array cannot be null !!");

        this.copyPoints = new Point[points.length];

        Arrays.sort(points);

        // copies points and throws exceptions when a null point is found.
        copyPoints(points, copyPoints);

        for (int i = 0; i < points.length - 1; i++) {
            if (copyPoints[i + 1].compareTo(copyPoints[i]) == 0)
                throw new IllegalArgumentException("points inside the array should not have duplicates");

        }

        this.lineSegmentCount = 0;
        this.segments = new ArrayList<>();
        this.segmentTracker = new HashMap<>();
        computeSegments(points, copyPoints);
    }

    /**
     * Computes existing number of collinear points in the given array.
     *
     * @param points
     */
    private void computeSegments(Point[] points, Point[] copyPoints) {

        for (int i = 0; i < points.length - 1; i++) {

            final Point current = points[i];

            // since the array is already sorted no need of sorting for i = 0.
            if (i > 0)
                Arrays.sort(copyPoints, i, points.length);

            Arrays.sort(copyPoints, i, points.length, current.slopeOrder());

            int startIndex = i + 1;

            double previousSlope = points[i].slopeTo(copyPoints[i + 1]);

            for (int j = i + 1; j < points.length; j++) {

                final double currentSlope = current.slopeTo(copyPoints[j]);


                if (previousSlope == currentSlope) {
                    if (j != points.length - 1)
                        continue;
                }
                int lastIndexWithMatchedSlope = (j == points.length - 1 && previousSlope == currentSlope) ? j : j - 1;
                if ((lastIndexWithMatchedSlope - startIndex) + 1 >= 3) {
                    final Point startPoint = copyPoints[i];
                    final Point endPoint = copyPoints[lastIndexWithMatchedSlope];

                    List<Point> segmentTrackerEndPointList;
                    if (segmentTracker.containsKey(previousSlope)) {
                        if (!segmentTracker.get(previousSlope).contains(endPoint)) {
                            segmentTrackerEndPointList = segmentTracker.get(previousSlope);
                            segmentTrackerEndPointList.add(endPoint);
                            segmentTracker.put(previousSlope, segmentTrackerEndPointList);
                            final LineSegment lineSegment = new LineSegment(startPoint, endPoint);
                            segments.add(lineSegment);
                            lineSegmentCount++;
                        }
                    } else {
                        segmentTrackerEndPointList = new ArrayList<>();
                        segmentTrackerEndPointList.add(endPoint);
                        segmentTracker.put(previousSlope, segmentTrackerEndPointList);
                        final LineSegment lineSegment = new LineSegment(startPoint, endPoint);
                        segments.add(lineSegment);
                        lineSegmentCount++;
                    }
                    startIndex = j;
                    previousSlope = currentSlope;
                } else {
                    startIndex = j;
                    previousSlope = currentSlope;
                    continue;
                }
            }
        }
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
        return segments.toArray(new LineSegment[segments.size()]);
    }

    /**
     * Helper method to copy points
     * @param points
     * @param copyPoints
     */
    private void copyPoints(final Point[] points, final Point[] copyPoints){

        for (int i = 0; i < points.length; i++) {
            if (points[i] != null) {
                copyPoints[i] = points[i];
            } else {
                throw new IllegalArgumentException("Points inside the array cannot be null!!");
            }
        }
    }

    private void printPoints(final Point[] points) {
        for (Point p : points) {
            System.out.print(p.toString() + ", ");
        }
    }

    private void printPoints(final Point[] points, int i) {
        for (int j = 0; j <= i; j++) {
            System.out.print(points[j].toString() + ", ");
        }

        for (int j = i + 1; j < points.length; j++) {
            System.out.print(points[j].toString() + ", ");
        }

    }

    private void printPointsWithSlope(final Point[] points) {
        System.out.println();
        for (int i = 1; i < points.length; i++) {
            System.out.println("slope: " + points[0].toString() + " - " + points[i].toString() + "->"
                    + points[0].slopeTo(points[i]));
        }
    }

}
