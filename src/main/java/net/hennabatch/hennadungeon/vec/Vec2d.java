package net.hennabatch.hennadungeon.vec;

public class Vec2d implements IVec{

    private int x;
    private int y;

    public Vec2d(int x, int y){
        this.x = x;
        this.y = y;
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

    public Vec2d sub(IVec vec){
        return add(new Vec2d(-vec.getX(), -vec.getY()));
    }

    public Vec2d dot(IVec vec){
        return new Vec2d(this.getX() * vec.getX(), this.getY() * vec.getY());
    }

    public Vec2d dot(int scalar){
        return dot(new Vec2d(scalar, scalar));
    }

    public double distance(IVec vec){
        return Math.sqrt(Math.pow(this.getX() - vec.getX(), 2) + Math.pow(this.getY() - vec.getY(), 2));
    }

    public int area(){
        return this.getX() * this.getY();
    }
}
