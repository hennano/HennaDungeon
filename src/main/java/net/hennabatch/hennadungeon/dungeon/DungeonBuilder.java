package net.hennabatch.hennadungeon.dungeon;

import net.hennabatch.hennadungeon.dungeon.floor.*;
import net.hennabatch.hennadungeon.entity.Entity;
import net.hennabatch.hennadungeon.scene.GameScene;
import net.hennabatch.hennadungeon.scene.Scene;
import net.hennabatch.hennadungeon.util.EnumDifficulty;
import net.hennabatch.hennadungeon.util.Reference;
import net.hennabatch.hennadungeon.vec.EnumDirection;
import net.hennabatch.hennadungeon.vec.Vec2d;

import java.lang.reflect.Constructor;
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
    private double roomConnectChance = Reference.DUNGEON_CONNECT_CHANCE;

    public double getRoomConnectChance() {
        return roomConnectChance;
    }

    public void setRoomConnectChance(double roomConnectChance) {
        this.roomConnectChance = roomConnectChance;
    }

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

    public Dungeon build(GameScene scene){
        //セクション生成
        Reference.logger.debug("Section generating...");
        List<Section> sections = generateSections();
        sections.forEach( x-> Reference.logger.debug(x.toString()));
        //部屋生成
        Reference.logger.debug("Room generating...");
        sections.stream().min(Comparator.comparingInt(x -> x.size().area())).get().generateStartRoom();
        sections.stream().min(Comparator.comparingInt(x -> -x.size().area())).get().generateExitRoom();
        sections.parallelStream().filter(x -> x.room == null).forEach(Section::generateRoom);
        sections.forEach( x-> Reference.logger.debug(x.room.toString()));
        //通路生成
        Reference.logger.debug("Passage generating...");
        Reference.logger.debug("Main passage generating...");
        List<Passage> mainPassages = generateAdjacentPassage(sections);
        mainPassages.forEach(x -> Reference.logger.debug(x.toString()));
        Reference.logger.debug("Other passage generating...");
        List<Passage> otherPassages = generateOtherPassages(sections);
        otherPassages.forEach(x -> Reference.logger.debug(x.toString()));
        mainPassages.addAll(otherPassages);
        //ゴールまでの経路設定
        Reference.logger.debug("Exit path calculating...");
        setExitPath(sections.stream().map(x -> x.room).filter(x -> x instanceof ExitRoom).findFirst().get());

        List<Entity> entities = new ArrayList<>();



        return new Dungeon(scene, new Vec2d(width, height), Stream.concat(sections.stream().map(x -> x.room), mainPassages.stream()).collect(Collectors.toList()), entities, difficulty);
    }

    private void setExitPath(Floor floor){
        floor.getConnectFloors().forEach( x -> {
            if(x.getFloor().getPathToExit() != null) return;
            x.getFloor().setPathToExit(x.getFloor().getConnectFloors().stream().filter(y -> y.getFloor().equals(floor)).findFirst().get());
            setExitPath(x.getFloor());
        });
    }

    private List<Passage> generateAdjacentPassage(List<Section> sections){
        List<Passage> passages = new ArrayList<>();
        for(int i = 0; i < sections.size() - 1; i++){
            //Reference.logger.debug("1: " + sections.get(i).toString() + "\t\t 2: " + sections.get(i + 1).toString());
            passages.add(generatePassage(sections.get(i), sections.get(i + 1)));
        }
        return passages;
    }


    private List<Passage> generateOtherPassages(List<Section> sections){
        List<Passage> passages = new ArrayList<>();
        Random rand = new Random();
        sections.forEach(x ->{
            //自身の左側
            sections.stream()
                .filter(y -> !x.equals(y))
                .filter(y -> x.room.getConnectFloors().stream().noneMatch(z -> y.room.getConnectFloors().stream().anyMatch(u -> u.equals(z))))
                .filter(y -> {
                    EnumDirection direction = x.nextTo(y);
                    return direction != null && (direction.equals(EnumDirection.NX) || direction.equals(EnumDirection.NY));
                })
                .forEach(y -> {
                    if(rand.nextDouble() <= roomConnectChance){
                        passages.add(generatePassage(x, y));
                    }
                });
        });
        return passages;
    }

    private Passage generatePassage(Section section1, Section section2){
        EnumDirection direction = section1.nextTo(section2);
        if(direction == null) return null;
        if(direction.equals(EnumDirection.X) || direction.equals(EnumDirection.Y)){
            Section tmp = section1;
            section1 = section2;
            section2 = tmp;
            direction = direction.switchOtherSide();
        }

        Random rand = new Random();
        if(direction.equals(EnumDirection.NX)){
            int lRx = section1.room.getUpperLeft().getX();
            int lRy = rand.nextInt(section1.room.size().getY()) + section1.room.getUpperLeft().getY();
            int uLx = section2.room.getLowerRight().getX();
            int uLy = rand.nextInt(section2.room.size().getY()) + section2.room.getUpperLeft().getY();
            Passage passage = new Passage(new Vec2d(uLx, uLy), new Vec2d(lRx, lRy), new Vec2d(section1.upperLeft.getX(), 0));
            connectFromRoomToRoomByPassage(section1.room, section2.room, passage, EnumDirection.NX);
            return passage;
        }
        if(direction.equals(EnumDirection.NY)){
            int lRx = rand.nextInt(section1.room.size().getX()) + section1.room.getUpperLeft().getX();
            int lRy = section1.room.getUpperLeft().getY();
            int uLx = rand.nextInt(section2.room.size().getX()) + section2.room.getUpperLeft().getX();
            int uLy = section2.room.getLowerRight().getY();
            Passage passage = new Passage(new Vec2d(uLx, uLy), new Vec2d(lRx, lRy), new Vec2d(0, section1.upperLeft.getY()));
            connectFromRoomToRoomByPassage(section1.room, section2.room, passage, EnumDirection.NY);
            return passage;
        }
        return null;
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
