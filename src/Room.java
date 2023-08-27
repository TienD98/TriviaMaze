import javax.swing.*;

public class Room extends JButton {
    boolean north,south,east,west;

    public Room(boolean north, boolean south, boolean east, boolean west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    @Override
    public String toString(){
        String s="Ways out of this room are:";
        if(north){
            s+=" North ";
        }
        if(south){
            s+=" South ";
        }
        if(east){
            s+=" East ";
        }
        if(west){
            s+=" West ";
        }
        return s;
    }
}
