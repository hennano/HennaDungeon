package net.hennabatch.hennadungeon.vec;

public class Vec2d implements IVec, Cloneable{

    private final int x;
    private final int y;

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

    public Vec2d add(int scalar){
        return add(new Vec2d(scalar, scalar));
    }

    public Vec2d sub(IVec vec){
        return add(new Vec2d(-vec.getX(), -vec.getY()));
    }

    Vec2d sub(int scalar){
        return sub(new Vec2d(scalar, scalar));
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

    public Vec2d rotate(EnumDirection direction){
        return new Vec2d(direction.cos() * getX() - direction.sin()*getX(), direction.sin() * getY() + direction.cos() * getY());
    }

    public Vec2d rotate(EnumDirection from, EnumDirection to){
        return rotate(from.normalize()).rotate(to);
    }

    @Override
    public Vec2d clone(){
        return new Vec2d(this.getX(), this.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IVec){
            IVec vec = (IVec)obj;
            return this.getX() == vec.getX() && this.getY() == vec.getY();
        }
        return false;
    }

    public static Vec2d byIVec(IVec vec){
        return new Vec2d(vec.getX(), vec.getY());
    }
}
