package GameState;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class Ressource
{
    private String ressourceID;
    private BufferedImage image;
    private ArrayList<BufferedImage[]> spritesU;
    private BufferedImage[] spritesP;

    public Ressource(String ressourcePath,String ressourceID)
    {
        this.ressourceID = ressourceID;
        loadRessource(ressourcePath);
    }



    private void loadRessource(String path)
    {
        try
        {
            InputStream in =getClass().getResourceAsStream(path);
            image = ImageIO.read(in);
            in.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public ArrayList<BufferedImage[]> loadUnitRessource(int[] numFrames,int width,int height)
    {

        if (spritesU==null)
        {
            spritesU = new ArrayList<BufferedImage[]>();

            for (int i = 0; i < numFrames.length; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for (int j = 0; j < numFrames[i]; j++) {
                    if (i != 6)//standard size sprites , 6==special sized one
                    {
                        bi[j] = image.getSubimage(j * width, i * height, width, height);//cut the needed image
                    } else//spectial size:width size == normal width*2
                    {
                        bi[j] = image.getSubimage(j * width * 2, i * height, width * 2, height);
                    }
                }
                spritesU.add(bi);
            }
        }

        return spritesU;
    }

    public BufferedImage[] loadProjectileResource(int spriteNumber, int width, int height)
    {
        if(spritesP==null)
        {
            spritesP = new BufferedImage[spriteNumber];//number of sprites


                for (int i = 0; i < spritesP.length; i++) {
                    spritesP[i] = image.getSubimage(
                            i * width,
                            0,
                            width,
                            height
                    );
                }
        }

        return spritesP;
    }

    public String getRessourceID()
    {
        return ressourceID;
    }

    public BufferedImage getImage()
    {
        return image;
    }


}
