package com.cgvsu.utils;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {
    public static void removePolygons(List<Vector3f> vertices, List<Polygon> polygons,
                                      List<Integer> polygonNumbers, boolean deleteFreeVertices) {
        polygonNumbers.sort(Integer::compareTo);

        if (polygonNumbers.get(0) < 1 || polygonNumbers.get(polygonNumbers.size() - 1) > polygons.size()) {
            throw new IndexOutOfBoundsException("Некорректный номер полигона");
        }

        if (deleteFreeVertices) {
            Set<Integer> probablyFreeVerticesIndices = new HashSet<>();

            for (int i = 0; i < polygonNumbers.size(); i++) {
                int localIndex = polygonNumbers.get(i) - i - 1;

                probablyFreeVerticesIndices.addAll(polygons.get(localIndex).getVertexIndices());
                polygons.remove(localIndex);
            }

            retainOnlyFreeVertices(polygons, probablyFreeVerticesIndices);

            List<Integer> freeVerticesIndices = new ArrayList<>(probablyFreeVerticesIndices);
            freeVerticesIndices.sort(Integer::compareTo);

            if (probablyFreeVerticesIndices.size() != 0) {
                for (int i = 0; i < freeVerticesIndices.size(); i++) {
                    vertices.remove(freeVerticesIndices.get(i) - i);
                }

                adjustPolygonIndices(polygons, freeVerticesIndices);
            }

        } else {
            for (int i = 0; i < polygonNumbers.size(); i++) {
                polygons.remove(polygonNumbers.get(i) - i - 1);
            }
        }
    }

    protected static void retainOnlyFreeVertices(List<Polygon> polygons, Set<Integer> probablyFreeVerticesIndices) {
        for (Polygon currentPolygon : polygons) {
            for (int vertexIndex : currentPolygon.getVertexIndices()) {
                probablyFreeVerticesIndices.remove(vertexIndex);
            }

            if (probablyFreeVerticesIndices.size() == 0) {
                return;
            }
        }
    }

    protected static void adjustPolygonIndices(List<Polygon> polygons, List<Integer> freeVerticesIndices) {
        for (Polygon currentPolygon : polygons) {
            ArrayList<Integer> currentVertexIndices = currentPolygon.getVertexIndices();

            currentVertexIndices.replaceAll(vertexIndex -> getModernVertexIndex(vertexIndex, freeVerticesIndices));

            currentPolygon.setVertexIndices(currentVertexIndices);
        }
    }

    private static int getModernVertexIndex(int oldVertexIndex, List<Integer> freeVerticesIndices) {
        for (int i = 0; i < freeVerticesIndices.size(); i++) {
            if (oldVertexIndex < freeVerticesIndices.get(i)) {
                return oldVertexIndex - i;
            }
        }

        return oldVertexIndex - freeVerticesIndices.size();
    }
}
