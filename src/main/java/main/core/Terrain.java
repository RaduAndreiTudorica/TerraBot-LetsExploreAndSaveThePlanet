package main.core;

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
                this.map[x][y] = new Section(x, y);
            }
        }
    }

    public List<Animal> getAllAnimals() {
        List<Animal> allAnimals = new ArrayList<>();
        for(Section[] rows : map) {
            for(Section section : rows) {
                Animal animal = section.getAnimal();
                if(animal != null) {
                    allAnimals.add(animal);
                }
            }
        }
        return allAnimals;
    }

    public List<Section> getNeighbors(Section section) {
        if(section == null) {
            return new ArrayList<>();
        }

        int x = section.getX();
        int y = section.getY();

        List<Section> neighbors = new ArrayList<>();
        neighbors.add(getSection(x, y + 1));
        neighbors.add(getSection(x + 1, y));
        neighbors.add(getSection(x, y - 1));
        neighbors.add(getSection(x - 1, y));

        return neighbors;
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


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}