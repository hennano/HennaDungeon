package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.vec.IVec;
import net.hennabatch.hennadungeon.vec.Vec2d;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Screen implements Cloneable{

    private final String[][] screen;
    private final int width;
    private final int height;

    public Screen(int width, int height){
        screen = new String[width][height];
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean setPos(int row, int column, String aLetter){
        try{
            screen[row][column] = aLetter;
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public Vec2d setRow(int row, int column, String message, boolean canNext, boolean shouldPadding){
        return setRow(row, column, this.width + 1, message, canNext, shouldPadding);
    }

    //行(横)
    public Vec2d setRow(int row, int column, int rowMax, String message, boolean canNext, boolean shouldPadding){
        List<String> str = formatMessage(message, shouldPadding);
        int pos = row;
        for(int i = 0; i < str.size(); i++, pos++){
            if(str.get(i).equals("\n") || pos >= rowMax){
                pos = row - 1;
                column++;
                continue;
            }
            try{
                screen[pos][column] = str.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                if(canNext && column < height){
                    pos = row;
                    column++;
                    screen[pos][column] = str.get(i);
                }else{
                    return new Vec2d(pos, column);
                }
            }
        }
        return new Vec2d(pos, column);
    }

    //列(縦)
    public Vec2d setColumn(int row, int column, String message, boolean canNext, boolean shouldPadding){
        return setColumn(row, column, this.height + 1, message, canNext, shouldPadding);
    }

    public Vec2d setColumn(int row, int column, int columnMax, String message, boolean canNext, boolean shouldPadding){
        List<String> str = formatMessage(message, shouldPadding);
        int pos = column;
        for(int i = 0; i < str.size(); i++, pos++){
            if(str.get(i).equals("\n") || column >= columnMax){
                pos = column - 1;
                row++;
                continue;
            }
            try{
                screen[row][pos] = str.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                if(canNext && row < width){
                    pos = column;
                    row++;
                    screen[row][pos] = str.get(i);
                }else{
                    return new Vec2d(row, pos);
                }
            }
        }
        return new Vec2d(row, pos);
    }

    @Override
    public Screen clone(){
        Screen clone = new Screen(this.width, this.height);
        for(int x = 0; x < clone.width; x++){
            if (clone.height >= 0) System.arraycopy(this.screen[x], 0, clone.screen[x], 0, clone.height);
        }
        return clone;
    }

    public Screen overWrite(Screen screen){
        Screen ret = this.clone();
        for(int x = 0; x < ret.width; x++){
            for(int y = 0; y < ret.width; y++){
                try{
                    if(screen.screen[x][y] != null) ret.screen[x][y] = screen.screen[x][y];
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){
                    break;
                }
            }
        }
        return ret;
    }

    private boolean isFullWidthChar(char ch){
        Pattern p = Pattern.compile("^[^!-~｡-ﾟ]*$");
        Matcher m = p.matcher(String.valueOf(ch));

        return m.find();
    }

    private List<String> formatMessage(String message, boolean shouldPadding){
        List<String> out = new ArrayList<>();
        for(int i = 0; i < message.length(); i++){
            if(isFullWidthChar(message.charAt(i))){
                out.add(String.valueOf(message.charAt(i)));
                continue;
            }

            if(shouldPadding){
                out.add(message.charAt(i) + " ");
                continue;
            }

            try{
                if(isFullWidthChar(message.charAt(i + 1))){
                    out.add(message.charAt(i) + " ");
                }else{
                    out.add(message.charAt(i) + "" + message.charAt(i + 1));
                    i++;
                }
            }catch(IndexOutOfBoundsException e){
                out.add(message.charAt(i) + " ");
                break;
            }
        }
        return out;
    }

    public void println(){
        StringBuilder screenStr = new StringBuilder();
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                screenStr.append(screen[x][y]);
            }
            screenStr.append("\n");
        }
        System.out.print(screenStr);
    }

    public static Screen createBaseScreen(int width, int height){
        Screen ret = new Screen(width, height);
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                ret.screen[x][y] = "　";
            }
        }
        return ret;
    }

    public void fillRect(int uLx, int uLy, int lRx, int lRy, String replace, boolean hasFrame){
        for(int x = uLx; x <= lRx; x++) {
            for (int y = uLy; y <= lRy; y++) {
                try{
                    screen[x][y] = replace;
                }catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }
        }
        if(!hasFrame) return;

        for(int x = uLx; x <= lRx; x++) {
            try{
                screen[x][uLy] = "―";
                screen[x][lRy] = "―";
            }catch (ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
        for(int y = uLy; y <= lRy; y++) {
            try{
                screen[uLx][y] = "｜";
                screen[lRx][y] = "｜";
            }catch (ArrayIndexOutOfBoundsException e){
                continue;
            }
        }
        try {
            screen[uLx][uLy] = "＋";
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            screen[uLx][lRy] = "＋";
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            screen[lRx][uLy] = "＋";
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            screen[lRx][lRy] = "＋";
        }catch (ArrayIndexOutOfBoundsException e){}
    }

    public void drawGauge(int row, int column, int length, int current, int max, String fill, String empty, boolean hasEndDecoration){
        int gaugeLength = (int) (((double)current / max) * length);
        if(hasEndDecoration){
            setRow(row, column, "［" + String.join("", Collections.nCopies(gaugeLength, fill)) + String.join("", Collections.nCopies( max - gaugeLength, empty)) + "］", false, false);
        }else{
            setRow(row, column, String.join("", Collections.nCopies(gaugeLength, fill)) + String.join("", Collections.nCopies( max - gaugeLength, empty)), false, false);
        }
    }
}
