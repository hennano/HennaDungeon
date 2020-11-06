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
                "\tlRx: " + x.lowerRight().getX() + " lRy: " + x.lowerRight().getY()));







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
                int chanceSize = section.size.getX() - 7 - (minRoomWidth * 2);
                Random rand = new Random();
                return new DivisionLine(rand.nextInt(chanceSize / 2) + (rand.nextInt(2) * (chanceSize / 2)) + minRoomWidth + 4, EnumDirection.X);
            }else{
                int chanceSize = section.size.getY() - 7 - (minRoomHeight * 2);
                Random rand = new Random();
                return new DivisionLine(rand.nextInt(chanceSize / 2) + (rand.nextInt(2) * (chanceSize / 2)) + minRoomHeight + 4, EnumDirection.Y);
            }
        }
    }

    private class Section{
        Vec2d upperLeft;
        Vec2d size;

        Section(Vec2d upperLeft, Vec2d size){
            this.upperLeft = upperLeft;
            this.size = size;
        }

        boolean canSplit(int minRoomWidth, int minRoomHeight){
            Vec2d minimumSectionSize = new Vec2d(minRoomWidth + 2, minRoomHeight + 2);
            return (this.size.getX() > (minimumSectionSize.getX() * 2 + 3)) && (this.size.getY() > (minimumSectionSize.getY() * 2 + 3));
        }

        List<Section> split(DivisionLine divline){
            if(divline.direction==EnumDirection.X){
                return new ArrayList<>(Arrays.asList(
                        new Section(new Vec2d(this.upperLeft.getX(), this.upperLeft.getY()), new Vec2d(divline.pos, this.size.getY())),
                        new Section(new Vec2d(divline.pos, this.upperLeft.getY()), new Vec2d(this.size.getX() - divline.pos, this.size.getY()))
                ));
            }else{
                return new ArrayList<>(Arrays.asList(
                        new Section(new Vec2d(this.upperLeft.getX(), this.upperLeft.getY()), new Vec2d(this.size.getX(), divline.pos)),
                        new Section(new Vec2d(this.upperLeft.getX(), divline.pos), new Vec2d(this.size.getX(), this.size.getY() - divline.pos))
                ));
            }
        }

        boolean biggerThan(Section section){
            return (this.size.getX() * this.size.getY()) > (section.size.getX() * section.size.getY());
        }

        IVec lowerRight(){
            return this.size.add(this.upperLeft);
        }

        EnumDirection nextTo(Section section){
            if(this.lowerRight().getY() == section.upperLeft.getY() && this.lowerRight().getX() < section.upperLeft.getX()) return EnumDirection.Y;
            if(this.lowerRight().getX() == section.upperLeft.getX() && this.lowerRight().getY() > section.upperLeft.getY()) return EnumDirection.X;
            return null;
        }

    }

}
