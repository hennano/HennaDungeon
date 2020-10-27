package net.hennabatch.hennadungeon.scene;

import net.hennabatch.hennadungeon.util.Reference;

import java.util.ArrayList;
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

    //行(横)
    public void setRow(int row, int column, String message, boolean canNext, boolean shouldPadding){
        List<String> str = formatMessage(message, shouldPadding);
        for(int i = 0; i < str.size(); i++){
            try{
                screen[row + i][column] = str.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                if(canNext && column < height){
                    column += 1;
                }else{
                    return;
                }
            }
        }
    }

    //列(縦)
    public void setColumn(int row, int column, String message, boolean canNext, boolean shouldPadding){
        List<String> str = formatMessage(message, shouldPadding);
        for(int i = 0; i < message.length(); i++){
            try{
                screen[row][column + i] = str.get(i);
            }catch(ArrayIndexOutOfBoundsException e){
                if(canNext && row < width){
                    row += 1;
                }else{
                    return;
                }
            }
        }
    }

    @Override
    public Screen clone(){
        Screen clone = new Screen(this.width, this.height);
        for(int x = 0; x < clone.width; x++){
            for(int y = 0; y < clone.height; y++){
                clone.screen[x][y] = this.screen[x][y];
            }
        }
        return clone;
    }

    public Screen overWrite(Screen screen){
        Screen ret = this.clone();
        for(int x = 0; x < ret.width; x++){
            for(int y = 0; y < ret.width; y++){
                try{
                    ret.screen[x][y] = screen.screen[x][y];
                }catch(ArrayIndexOutOfBoundsException e){}
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
                    continue;
                }else{
                    out.add(String.valueOf(message.charAt(i) + message.charAt(i + 1)));
                    i++;
                    continue;
                }
            }catch(IndexOutOfBoundsException e){
                break;
            }
        }
        return out;
    }

}
