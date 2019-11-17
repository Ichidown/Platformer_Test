package TileMap;

import Main.Game_Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap
{
    //position
    private double x=0, y=0;

    //bounds
    private int xmin, ymin, xmax, ymax;

    //view
    private double tween;//smouthe scroll player camera when move

    //map stuff
    private int[][] map;
    private int tileSize;
    private int numRows;//num of rows in the map
    private int numCols;//num of columns in the map
    private int width;//dimentions of the map
    private int height;//   //     //     //

    //tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    public Tile[][] tiles;//list of images in the map

    //drawing
    private int rowOffset;//to determine witch row to start draw
    private int colOffset;//to determine witch column to start draw
    private int numRowsToDraw;//to know how much rows to draw
    private int numColsToDraw;//to know how much columns to draw

    public TileMap(int tileSize)
    {
        this.tileSize = tileSize;
        numRowsToDraw = Game_Panel.HEIGHT / tileSize+2;//to get the number of blocks vertically + 2 just in case
        numColsToDraw = Game_Panel.WIDTH / tileSize+2;//to get the number of blocks horisentally +2 just in case
    }

    public void loadTiles(String s)//load tile file in memory
    {
        try
        {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));//to get resources
            numTilesAcross = tileset.getWidth() / tileSize;//??
            tiles = new Tile[2][numTilesAcross];//??

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++)
            {
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);//for the 1st row "non blocker"
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);//for the 2nd row "blocker"
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void loadMap(String s)//load map file in memory
    {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //now the map file is loaded
            numCols = Integer.parseInt(br.readLine());//to read 1st ligne for num of columns
            numRows = Integer.parseInt(br.readLine());//to read 2nd ligne for num of rows

            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            //-----------------------------
            xmin = Game_Panel.WIDTH-width;
            xmax = 0;
            ymin = Game_Panel.HEIGHT-height;
            ymax = 0;
            //-----------------------------

            String delims = "\\s+";//replacewith " " //white space : to determine the end of a token
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);//split a line into tokens using delims as a spliter
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);//fill the map
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTileSize()
{
        return tileSize;
    }

    public double getx()
    {
        return  x;
    }

    public double gety()
    {
        return  y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setTween(double i){tween=i;}

    public int getType(int row,int col)
    {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;//% modulo:rest of /
        return tiles[r][c].getType();
    }

    public void setPosition(double x, double y)
    {
        this.x += (x - this.x) * tween / Game_Panel.FPS;//this.x=x;    for extra effect camera folow
        this.y += (y - this.y) * tween / Game_Panel.FPS;//this.y=y;   use comment for exact cam folow

        fixBounds();
        colOffset = (int) -this.x / tileSize;//to determine where to start drawing
        rowOffset = (int) -this.y / tileSize;//       in    column & row
    }

    private void fixBounds()
    {
        if (x < xmin) x = xmin;
        if (y < ymin) y = ymin;
        if (x > xmax) x = xmax;
        if (y > ymax) y = ymax;
    }

    public void draw(Graphics2D g)
    {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) break;//if nothing to draw

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols) break;//if nothing to draw

                if (map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
            }
        }
    }

}


