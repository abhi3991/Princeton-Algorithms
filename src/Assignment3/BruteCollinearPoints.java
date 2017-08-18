package Assignment3;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int lineSegmentCount;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("points array cannot be null!!");
        for (int i =0; i < points.length ; i++){
            if (points[i] == null)
                throw new IllegalArgumentException("Points in the array cannot be null!");
        }
        this.lineSegmentCount = 0;
        this.segments = new LineSegment[1];
        computeSegments(points);
    }

    /**
     * Computes existing number of collinear points in the given array.
     *
     * @param points
     */
    private void computeSegments(Point[] points) {
        final int len = points.length;
        for (int i = 0; i < len - 3; i++) {

            for (int j = i + 1; j < len - 2; j++) {

                for (int k = j + 1; k < len - 1; k++) {

                    outerLoop:
                    for (int l = k + 1; l < len; l++) {

                        if (isCollinear(points, i, j, k, l)) {
                            final LineSegment lineSegment = findLineSegment(points, i, j, k, l);
                            if (lineSegmentCount == 0) {
                                this.segments[lineSegmentCount++] = lineSegment;
                                continue;
                            }
                            if (lineSegmentCount == segments.length) {
                                resize(2 * lineSegmentCount);
                            }
                            for (int q = 0; q < lineSegmentCount; q++) {
                                if (segments[q].toString().equals(lineSegment.toString())) {
                                    continue outerLoop;
                                }

                            }
                            this.segments[lineSegmentCount++] = lineSegment;
                        }
                    }
                }
            }
        }
    }

    private boolean isCollinear(Point[] points, int p1, int p2, int p3, int p4) {

        final double p1_p2_slope = points[p1].slopeTo(points[p2]);
        final double p1_p3_slope = points[p1].slopeTo(points[p3]);
        final double p1_p4_slope = points[p1].slopeTo(points[p4]);

        return (p1_p2_slope == p1_p3_slope) && (p1_p2_slope == p1_p4_slope);
    }

    private LineSegment findLineSegment(Point[] points, int p1, int p2, int p3, int p4) {

        Point[] temp = {points[p1], points[p2], points[p3], points[p4]};
        Point min = temp[0];
        Point max = temp[0];
        for (int i = 0; i < 3; i++) {
            if (min.compareTo(temp[i + 1]) > 0) {
                min = temp[i + 1];
            }
            if (max.compareTo(temp[i + 1]) < 0) {
                max = temp[i + 1];
            }
        }

        final LineSegment lineSegment = new LineSegment(min, max);
        return lineSegment;
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
