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
        return 0;
    }

    @Override
    public int getY() {
        return 0;
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
}
