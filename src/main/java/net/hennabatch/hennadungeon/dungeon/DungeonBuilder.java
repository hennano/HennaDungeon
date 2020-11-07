package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.*;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.lang.reflect.Constructor;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //セクション生成
        Reference.logger.debug("Section generating...");
        List<Section> sections = generateSections();
        sections.forEach( x-> Reference.logger.debug(x.toString()));
        //部屋生成
        Reference.logger.debug("Room generating...");
        sections.stream().sorted(Comparator.comparingInt(x -> x.size().area())).findFirst().get().generateStartRoom();
        sections.stream().sorted(Comparator.comparingInt(x -> - x.size().area())).findFirst().get().generateExitRoom();
        sections.parallelStream().filter(x -> x.room == null).forEach(Section::generateRoom);
        sections.forEach( x-> Reference.logger.debug(x.toString()));
        //通路生成
        Reference.logger.debug("Passage generating...");
        List<Passage> passages = generatePassages(sections);
        passages.forEach(x -> Reference.logger.debug(x.toString()));
        //ゴールまでの経路設定
        setExitPath(sections.stream().map(x -> x.room).filter(x -> x instanceof ExitRoom).findFirst().get());


        return new Dungeon(this);
    }

    private void setExitPath(Floor floor){
        floor.getConnectFloors().forEach( x -> {
            if(x.getFloor().getPathToExit() != null) return;
            x.getFloor().setPathToExit(x.getFloor().getConnectFloors().stream().filter(y -> y.getFloor().equals(floor)).findFirst().get());
            setExitPath(x.getFloor());
        });
    }

    private List<Passage> generatePassages(List<Section> sections){
        List<Passage> passages = new ArrayList<>();
        Random rand = new Random();
        sections.forEach(x ->{
            //自身の左側
            sections.stream().filter(y -> {
                EnumDirection direction = x.nextTo(y);
                return direction != null && direction.equals(EnumDirection.NX);
            }).forEach(y -> {
                int lRx = x.room.getUpperLeft().getX();
                int lRy = rand.nextInt(x.room.size().getY()) + x.room.getUpperLeft().getY();
                int uLx = y.room.getLowerRight().getX();
                int uLy = rand.nextInt(y.room.size().getY()) + y.room.getUpperLeft().getY();
                Passage passage = new Passage(new Vec2d(uLx, uLy), new Vec2d(lRx, lRy), new Vec2d(x.upperLeft.getX(), 0));
                connectFromRoomToRoomByPassage(x.room, y.room, passage, EnumDirection.NX);
                passages.add(passage);
            });
            //自身の上側
            sections.stream().filter(y ->{
                EnumDirection direction = x.nextTo(y);
                return direction != null && direction.equals(EnumDirection.NY);
            }).forEach(y -> {
                int lRx = rand.nextInt(x.room.size().getX()) + x.room.getUpperLeft().getX();
                int lRy = x.room.getUpperLeft().getY();
                int uLx = rand.nextInt(y.room.size().getX()) + y.room.getUpperLeft().getX();
                int uLy = y.room.getLowerRight().getY();
                Passage passage = new Passage(new Vec2d(uLx, uLy), new Vec2d(lRx, lRy), new Vec2d(0, x.upperLeft.getY()));
                connectFromRoomToRoomByPassage(x.room, y.room, passage, EnumDirection.NY);
                passages.add(passage);
            });
        });
        return passages;
    }

    private void connectFromRoomToRoomByPassage(Room room1, Room room2, Passage passage, EnumDirection direction){
        room1.addConnectFloor(new ConnectFloor(passage, direction));
        room2.addConnectFloor(new ConnectFloor(passage, direction.switchOtherSide()));
    }

    private List<Section> generateSections(){
        List<Section> sections = new ArrayList<>();
        Section currentSection = new Section(new Vec2d(0,0), new Vec2d(this.width - 1, this.height - 1));
        EnumDirection currentDirection = EnumDirection.random();
        for(int i=0; i < this.maxRooms && currentSection.canSplit(); i++){
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

        Room room;

        Section(Vec2d upperLeft, Vec2d lowerRight){
            this.upperLeft = upperLeft;
            this.lowerRight = lowerRight;
        }

        Vec2d size(){
            return this.lowerRight.sub(this.upperLeft).add(new Vec2d(1, 1));
        }

        boolean canSplit(){
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
            if(this.upperLeft.getY() < section.lowerRight.getY() && this.lowerRight.getY() > section.upperLeft.getY()){
                if(this.upperLeft.getX() == section.lowerRight.getX()) return EnumDirection.NX;
                if(this.lowerRight.getX() == section.upperLeft.getX()) return EnumDirection.X;
            }
            if(this.upperLeft.getX() < section.lowerRight.getX() && this.lowerRight.getX() > section.upperLeft.getX()){
                if(this.upperLeft.getY() == section.lowerRight.getY()) return EnumDirection.NY;
                if(this.lowerRight.getY() == section.upperLeft.getY()) return EnumDirection.Y;
            }
            return null;
        }

        void generateRoom(){
            generateRoom(Room.class);
        }

        void generateStartRoom(){
            generateRoom(StartRoom.class);
        }

        void generateExitRoom(){
            generateRoom(ExitRoom.class);
        }

        private void generateRoom(Class<? extends Room> clazz){
            Random rand = new Random();
            int uLxBound = this.size().getX() - minRoomWidth - 4;
            int uLyBound = this.size().getY() - minRoomHeight - 4;
            int uLx = ( uLxBound > 0 ? rand.nextInt(uLxBound) : 0 ) + this.upperLeft.getX() + 2;
            int uLy = ( uLyBound > 0 ? rand.nextInt(uLyBound) : 0 ) + this.upperLeft.getY() + 2;
            int lRxBound = this.size().getX() - 3 - minRoomWidth - uLx;
            int lRyBound = this.size().getY() - 3 - minRoomHeight - uLy;
            int lRx = ( lRxBound > 0 ? rand.nextInt(lRxBound) : 0 ) + uLx + 2 + minRoomWidth;
            int lRy = ( lRyBound > 0 ? rand.nextInt(lRyBound) : 0 ) + uLy + 2 + minRoomHeight;
            Constructor constructor;
            try{
                constructor = clazz.getConstructor(Vec2d.class, Vec2d.class);
                this.room = (Room)constructor.newInstance(new Vec2d(uLx, uLy), new Vec2d(lRx, lRy));
            } catch (ReflectiveOperationException e){
                Reference.logger.error(e.getMessage(), e);
            }
        }

        @Override
        public String toString() {
            return "section uLx: " + upperLeft.getX() +" uLy: " + upperLeft.getY() +
                    "\tlRx: " + lowerRight.getX() + " lRy: " + lowerRight.getY() +
                    "\tsize x: " + size().getX() + " size y: " + size().getY() +
                    "\tarea: " + size().area();
        }
    }
}
