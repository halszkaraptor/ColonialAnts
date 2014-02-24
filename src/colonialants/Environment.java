/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colonialants;

import colonialants.Colony.ColLoc;
import java.util.ArrayList;
import java.util.Random;
import org.eclipse.swt.graphics.Point;

/**
 *
 * @author George McDaid
 */
public class Environment {
    
    //Needs to be a multiple of 2 for now
    private final int spaceSize = 20;
    
    private int dimension = 36;
    
    private GridLocation[][] locations;
    
    //Normally this will be a swarm holding all ants, sadly we must wait for Krish Fish (unless I get impatient)
    //Envionment has a Swarm (I think this has become a colony. Eventually ants can go in there)
    private Ant[] ant;
    private CommonScents scent;
    private Colony colony;
    private int population;
    
    public Environment(){
        locations = new GridLocation[dimension][dimension];
        colony = new Colony();
        population = 5;
    }
    
    public Environment(int dimension){
        this.dimension = dimension;
        locations = new GridLocation[dimension][dimension];
        colony = new Colony();
        population = 5;
    }
    
    private void initAntHill(){
        int is = 0;
        int ib = 0;
        
        int js = 0;
        int jb = 0;
        
        if(colony.getLocation() == ColLoc.TR){
            is = dimension-colony.getDimension();
            ib = dimension;
            
            js = 0;
            jb = 0+colony.getDimension();
        }else if(colony.getLocation() == ColLoc.TL){
            is = 0;
            ib = 0+colony.getDimension();
            
            js = 0;
            jb = 0+colony.getDimension();
        }else if(colony.getLocation() == ColLoc.BR){
            is = dimension-colony.getDimension();
            ib = dimension;
            
            js = dimension-colony.getDimension();
            jb = dimension;
        }else if(colony.getLocation() == ColLoc.BL){
            is = 0;
            ib = 0+colony.getDimension();
            
            js = dimension-colony.getDimension();
            jb = dimension;
        }
        
        for(int i = is;i<ib;i++){
            for(int j = js;j<jb;j++){
                locations[i][j].setTerrain((Terrain) new AntHill());
            }
        }
    }
    
    public void initEmptyField(){
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                
                locations[i][j] = new GridLocation((int)(spaceSize*(i)+(spaceSize/2)),(int)(spaceSize*(j)+(spaceSize/2)));
                Random r = new Random();
                if(r.nextInt(100)<5){
                    locations[i][j].setTerrain((Terrain) new Leaf());
                }else{
                    locations[i][j].setTerrain((Terrain) new Sand());
                }
                
                
                //System.out.print("("+locations[i][j].getX()+","+locations[i][j].getY()+") ");
            }
            //System.out.println();
        }
        
        initAntHill();
        
    }
    
    public void addTestAnts(){
        ant = new Ant[population];
        for(int i = 0;i<population;i++){
            ant[i] = new Ant(new FineLocation(locations[0][0].getX(),locations[0][0].getY()));
        }
    }
    
    public Ant[] getTestAnts(){
        return ant;
    }
    
    public void addTestScent(){
        scent = new CommonScents(new GridLocation(locations[5][5].getX(),locations[5][5].getY()));
    }
    
    public CommonScents getTestScent(){
        return scent;
    }
    
    public GridLocation[][] getLocations(){
        return locations;
    }
    
    public ArrayList<GridLocation> getSquare(Point p){
        ArrayList<GridLocation> square  = new ArrayList<GridLocation>();
        
        int ox = p.x;
        int oy = p.y;

        for(int i = -1;i<=1;i++){
            int x = (int)((ox-spaceSize/2)/spaceSize);
            x = x+i;
            if(x>=0&&x<dimension){
                for(int j = -1;j<=1;j++){
                    int y = (int)((oy-spaceSize/2)/spaceSize);
                    y = y+j;
                    if(y>=0&&y<dimension){
                        if((i==0)&&(j==0)){
                            continue;
                        }
                        square.add(locations[x][y]);
                    }
                }
            }
        }
                
        return square;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String s = "";
        for(int i = 0;i < dimension;i++){
            for(int j = 0;j < dimension;j++){
                System.out.print(locations[j][i].toString() + " ");
            }
            System.out.println();
        }
        return s;
    }
    
    public int getSpaceSize(){
        return spaceSize;
    }

}
