package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.EnumDirection;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.util.*;

public class DungeonBuilder {

    //ダンジョン設定
    private int width = Reference.DUNGEON_WIDTH;
    private int height = Reference.DUNGEON_HEIGHT;
    private int maxRooms = Reference.DUNGEON_MAXROOMS;
    private int minRoomWidth = Reference.DUNGEON_MIN_ROOMWIDTH;
    private int minRoomHeight = Reference.DUNGEON_MIN_ROOMHEIGTH;
    private EnumDifficulty difficulty = EnumDifficulty.NORMAL;

    public int getMinRoomWidth() {
        return minRoomWidth;
    }

    public DungeonBuilder setMinRoomWidth(int minRoomWidth) {
        this.minRoomWidth = minRoomWidth;
        return this;
    }

    public int getMinRoomHeight() {
        return minRoomHeight;
    }

    public DungeonBuilder setMinRoomHeight(int minRoomHeight) {
        this.minRoomHeight = minRoomHeight;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public DungeonBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public DungeonBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public DungeonBuilder setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
        return this;
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }

    public DungeonBuilder setDifficulty(EnumDifficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public Dungeon build(){
        List<Section> sections = createSections();
        sections.forEach( x-> Reference.logger.debug("section uLx: " + x.upperLeft.getX() +" uLy: " + x.upperLeft.getY() +
                "\tlRx: " + x.lowerRight.getX() + " lRy: " + x.lowerRight.getY() +
                "\tsize x: " + x.size().getX() + " size y: " + x.size().getY() +
                "\tarea: " + x.size().area()
        ));







        return new Dungeon(this);
    }

    private List<Section> createSections(){
        List<Section> sections = new ArrayList<>();
        Section currentSection = new Section(new Vec2d(0,0), new Vec2d(this.width - 1, this.height - 1));
        EnumDirection currentDirection = EnumDirection.random();
        for(int i=0; i < this.maxRooms && currentSection.canSplit(this.minRoomWidth, this.minRoomHeight); i++){
            DivisionLine divLine = DivisionLine.randomDivLine(currentSection, currentDirection, this.minRoomWidth, this.minRoomHeight);
            List<Section> tmp = currentSection.split(divLine);
            if(tmp.get(0).biggerThan(tmp.get(1))){
                currentSection = tmp.get(0);
                sections.add(tmp.get(1));
            }else{
                currentSection = tmp.get(1);
                sections.add(tmp.get(0));
            }
            currentDirection = currentDirection.switchDirection();
        }
        sections.add(currentSection);
        return sections;
    }

    private static class DivisionLine{
        int pos;
        EnumDirection direction;

         DivisionLine(int pos, EnumDirection direction){
            this.pos = pos;
            this.direction = direction;
        }

        public static DivisionLine randomDivLine(Section section, EnumDirection direction, int minRoomWidth, int minRoomHeight){
            if(direction == EnumDirection.X){
                int chanceSize = section.size().getX() - 7 - (minRoomWidth * 2);
                Random rand = new Random();
                return new DivisionLine(rand.nextInt(Math.max(1, chanceSize/ 4)) + rand.nextInt(2) * (3 * chanceSize / 4) + section.upperLeft.getX() + minRoomWidth + 4, EnumDirection.X);
            }else{
                int chanceSize = section.size().getY() - 7 - (minRoomHeight * 2);
                Random rand = new Random();
                return new DivisionLine(rand.nextInt(Math.max(1, chanceSize/ 4)) + rand.nextInt(2) * (3 * chanceSize / 4) + section.upperLeft.getY() + minRoomHeight + 4, EnumDirection.Y);
            }
        }
    }

    private class Section{
        Vec2d upperLeft;
        Vec2d lowerRight;

        Section(Vec2d upperLeft, Vec2d lowerRight){
            this.upperLeft = upperLeft;
            this.lowerRight = lowerRight;
        }

        Vec2d size(){
            return this.lowerRight.sub(this.upperLeft).add(new Vec2d(1, 1));
        }

        boolean canSplit(int minRoomWidth, int minRoomHeight){
            Vec2d minimumSectionSize = new Vec2d(minRoomWidth + 2, minRoomHeight + 2);
            return (this.size().getX() > (minimumSectionSize.getX() * 2 + 4)) && (this.size().getY() > (minimumSectionSize.getY() * 2 + 4));
        }

        List<Section> split(DivisionLine divline){
            if(divline.direction==EnumDirection.X){
                return new ArrayList<>(Arrays.asList(
                        new Section(this.upperLeft.clone(), new Vec2d(divline.pos, this.lowerRight.getY())),
                        new Section(new Vec2d(divline.pos, this.upperLeft.getY()), this.lowerRight.clone())
                ));
            }else{
                return new ArrayList<>(Arrays.asList(
                        new Section(this.upperLeft.clone(), new Vec2d(this.lowerRight.getX(), divline.pos)),
                        new Section(new Vec2d(this.upperLeft.getX(), divline.pos), this.lowerRight.clone())
                ));
            }
        }

        boolean biggerThan(Section section){
            return this.size().area() > section.size().area();
        }

        EnumDirection nextTo(Section section){
            return null;
        }
    }

}
