package main.core;

import main.environment.air.*;
import main.environment.soil.*;
import main.environment.water.*;
import main.environment.plant.*;
import main.environment.animal.*;

import java.util.*;
import java.util.function.Consumer;

public class Terrain {
    private int width;
    private int height;
    private Section[][] map;

    public Terrain(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new Section[width][height];
        initializeSections();
    }

    private void initializeSections() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Section section = new Section(x, y);
            }
        }
    }

    public Section getSection(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return map[x][y];
    }

    public Section[][] getMap() {
        return map;
    }

    public void forEachSection(Consumer<Section> action) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                action.accept(map[x][y]);
            }
        }
    }

    public void updateEnvironment() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
