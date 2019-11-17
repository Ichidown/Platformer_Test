package GameState;


import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ResourceManager
{
    //before the game starts we need to loop throught every entity & load its needed resources


    ArrayList<Ressource> ressources;

    public ResourceManager()
    {
        ressources = new ArrayList<>();
    }

    public BufferedImage getRessource(String path,String id)
    {
        //if resource exist return image
        for (int i = 0 ;i<ressources.size();i++)
        {
            if(ressources.get(i).getRessourceID()==id)
            {
                return ressources.get(i).getImage();
            }
        }

        //if resource non existing create new one & return new image
        ressources.add(new Ressource(path,id));

        return ressources.get(ressources.size()-1).getImage();

    }

    public BufferedImage getUnitRessource(String path,String id)
    {
        //if resource exist return image
        for (int i = 0 ;i<ressources.size();i++)
        {
            if(ressources.get(i).getRessourceID()==id)
            {
                return ressources.get(i).getImage();
            }
        }

        //if resource non existing create new one & return new image
        ressources.add(new Ressource(path,id));

        return ressources.get(ressources.size()-1).getImage();

    }

    public Ressource getExistingRessource(String id)
    {
        for(int i=0;i<ressources.size();i++)
        {
            if(ressources.get(i).getRessourceID()==id)
            {
                return ressources.get(i);
            }
        }
        return null;
    }
}
