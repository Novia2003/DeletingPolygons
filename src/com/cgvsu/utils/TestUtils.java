package com.cgvsu.utils;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static List<Polygon> getPolygonsList(List<List<Integer>> lists) {
        List<Polygon> results = new ArrayList<>();

        for (List<Integer> list : lists) {
            results.add(new Polygon(new ArrayList<>(list)));
        }

        return results;
    }

    public static List<String> vertexToString(List<Vector3f> list) {
        List<String> result = new ArrayList<>();

        for (Vector3f v : list) {
            result.add("v " + v.x + " " + v.y + " " + v.z);
        }

        return result;
    }

    public static List<String> polygonToString(List<Polygon> list) {
        List<String> results = new ArrayList<>();

        for (Polygon polygon : list) {
            StringBuilder result = new StringBuilder("f ");
            for (int v : polygon.getVertexIndices()) {
                result.append(v);
                result.append(" ");
            }

            results.add(result.toString());
        }

        return results;
    }
}
