package net.hennabatch.hennadungeon.vec;

import java.util.Arrays;
import java.util.Comparator;

public class Vec2d implements IVec, Cloneable{

    private final int x;
    private final int y;

    public Vec2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vec2d(IVec vec){
        this(vec.getX(), vec.getY());
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public Vec2d add(IVec vec){
        return new Vec2d(this.getX() + vec.getX(), this.getY() + vec.getY());
    }

    public Vec2d add(int scalar){
        return add(new Vec2d(scalar, scalar));
    }

    public Vec2d sub(IVec vec){
        return add(new Vec2d(-vec.getX(), -vec.getY()));
    }

    public Vec2d sub(int scalar){
        return sub(new Vec2d(scalar, scalar));
    }

    public Vec2d dot(IVec vec){
        return new Vec2d(this.getX() * vec.getX(), this.getY() * vec.getY());
    }

    public Vec2d dot(int scalar){
        return dot(new Vec2d(scalar, scalar));
    }

    public Vec2d div(IVec vec){
        return new Vec2d(this.getX() / vec.getX(), this.getY() / vec.getY());
    }

    public Vec2d div(int scalar){
        return div(new Vec2d(scalar, scalar));
    }

    public double distance(IVec vec){
        return Math.sqrt(Math.pow(this.getX() - vec.getX(), 2) + Math.pow(this.getY() - vec.getY(), 2));
    }

    public int area(){
        return this.getX() * this.getY();
    }

    public Vec2d rotate(EnumDirection direction){
        return new Vec2d(direction.cos() * getX() - direction.sin() * getY(), direction.sin() * getX() + direction.cos() * getY());
    }

    public Vec2d rotate(EnumDirection from, EnumDirection to){
        return rotate(from.normalize()).rotate(to);
    }

    @Override
    public Vec2d clone(){
        return new Vec2d(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IVec){
            IVec vec = (IVec)obj;
            return this.getX() == vec.getX() && this.getY() == vec.getY();
        }
        return false;
    }

    public Vec2d abs(){
        return new Vec2d(Math.abs(this.getX()), Math.abs(this.getY()));
    }

    public static <T extends IVec> T max(T... vecs){
        return Arrays.stream(vecs).max(Comparator.comparing(x -> new Vec2d(x).area())).get();
    }

    public static <T extends IVec> T min(T... vecs){
        return Arrays.stream(vecs).min(Comparator.comparing(x -> new Vec2d(x).area())).get();
    }

    public static Vec2d maxOfCoordinates(IVec... vecs){
        int maxX = Arrays.stream(vecs).max(Comparator.comparing(x -> x.getX())).get().getX();
        int maxY = Arrays.stream(vecs).max(Comparator.comparing(x -> x.getY())).get().getY();
        return new Vec2d(maxX, maxY);
    }

    public static Vec2d minOfCoordinates(IVec... vecs){
        int minX = Arrays.stream(vecs).min(Comparator.comparing(x -> x.getX())).get().getX();
        int minY = Arrays.stream(vecs).min(Comparator.comparing(x -> x.getY())).get().getY();
        return new Vec2d(minX, minY);
    }

    @Override
    public String toString() {
        return "x:" + x + " y:" + y;
    }
}
