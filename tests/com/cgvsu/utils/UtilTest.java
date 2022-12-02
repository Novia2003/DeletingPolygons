package com.cgvsu.utils;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class UtilTest {

    @Test
    void removePolygonsWhenFreeVerticesDeleted() {
        List<Vector3f> vertex = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 2),
                new Vector3f(0, 0, 3),
                new Vector3f(0, 0, 4),
                new Vector3f(0, 0, 5),
                new Vector3f(0, 0, 6)));



        List<Polygon> polygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(2, 5, 4),
                Arrays.asList(2, 1, 4),
                Arrays.asList(3, 5, 4),
                Arrays.asList(3, 1, 4),
                Arrays.asList(1, 6, 2),
                Arrays.asList(1, 6, 4)
        ));

        Util.removePolygons(vertex, polygons,  Arrays.asList(1, 3), true);

        List<Vector3f> expectedVertex = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 2),
                new Vector3f(0, 0, 3),
                new Vector3f(0, 0, 4),
                new Vector3f(0, 0, 6)));

        List<Polygon> expectedPolygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(2, 1, 4),
                Arrays.asList(3, 1, 4),
                Arrays.asList(1, 5, 2),
                Arrays.asList(1, 5, 4)
        ));

        Assertions.assertEquals(TestUtils.vertexToString(expectedVertex), TestUtils.vertexToString(vertex));
        Assertions.assertEquals(TestUtils.polygonToString(expectedPolygons), TestUtils.polygonToString(polygons));
    }

    @Test
    void removePolygonsWhenFreeVerticesNotDeleted() {
        List<Vector3f> vertex = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 2),
                new Vector3f(0, 0, 3),
                new Vector3f(0, 0, 4),
                new Vector3f(0, 0, 5),
                new Vector3f(0, 0, 6)
        ));



        List<Polygon> polygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(2, 5, 4),
                Arrays.asList(2, 1, 4),
                Arrays.asList(3, 5, 4),
                Arrays.asList(3, 1, 4),
                Arrays.asList(1, 6, 2),
                Arrays.asList(1, 6, 4)
        ));

        Util.removePolygons(vertex, polygons,  Arrays.asList(1, 3), false);

        List<Vector3f> expectedVertex = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 2),
                new Vector3f(0, 0, 3),
                new Vector3f(0, 0, 4),
                new Vector3f(0, 0, 5),
                new Vector3f(0, 0, 6)
        ));

        List<Polygon> expectedPolygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(2, 1, 4),
                Arrays.asList(3, 1, 4),
                Arrays.asList(1, 6, 2),
                Arrays.asList(1, 6, 4)
        ));

        Assertions.assertEquals(TestUtils.vertexToString(expectedVertex), TestUtils.vertexToString(vertex));
        Assertions.assertEquals(TestUtils.polygonToString(expectedPolygons), TestUtils.polygonToString(polygons));
    }

    @Test
    void removePolygonsWithIncorrectPolygonNumber() {
        List<Vector3f> vertex = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 2),
                new Vector3f(0, 0, 3),
                new Vector3f(0, 0, 4),
                new Vector3f(0, 0, 5),
                new Vector3f(0, 0, 6)
        ));



        List<Polygon> polygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(2, 5, 4),
                Arrays.asList(2, 1, 4),
                Arrays.asList(3, 5, 4),
                Arrays.asList(3, 1, 4),
                Arrays.asList(1, 6, 2),
                Arrays.asList(1, 6, 4)
        ));

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> Util.removePolygons(vertex, polygons,  Arrays.asList(9, 3, 0), false));
    }

    @Test
    void retainOnlyFreeVertices() {
        List<Polygon> polygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 4),
                Arrays.asList(4, 2, 1),
                Arrays.asList(86, 3, 4),
                Arrays.asList(1, 25, 3),
                Arrays.asList(1, 3, 4)
        ));

        Set<Integer> probablyFreeVerticesIndices = new HashSet<>(Arrays.asList(1, 2, 56, 4, 8, 86));

        Util.retainOnlyFreeVertices(polygons, probablyFreeVerticesIndices);

        Set<Integer> expectedSet = new HashSet<>(Arrays.asList(8, 56));

        Assertions.assertEquals(expectedSet, probablyFreeVerticesIndices);
    }

    @Test
    void adjustPolygonIndices() {
        List<Polygon> polygons = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 4),
                Arrays.asList(4, 2, 1),
                Arrays.asList(86, 3, 4),
                Arrays.asList(1, 25, 3),
                Arrays.asList(1, 3, 4)
        ));

        List<Integer> freeVerticesIndices = new ArrayList<>(Arrays.asList(0, 9, 30, 90));

        Util.adjustPolygonIndices(polygons, freeVerticesIndices);

        List<Polygon> expectedList = TestUtils.getPolygonsList(Arrays.asList(
                Arrays.asList(0, 1, 2),
                Arrays.asList(1, 2, 3),
                Arrays.asList(3, 1, 0),
                Arrays.asList(83, 2, 3),
                Arrays.asList(0, 23, 2),
                Arrays.asList(0, 2, 3)
        ));

        Assertions.assertEquals(TestUtils.polygonToString(expectedList), TestUtils.polygonToString(polygons));
    }

}