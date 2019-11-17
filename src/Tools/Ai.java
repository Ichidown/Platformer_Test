package Tools;


import Entity.Character.Unit;

public class Ai
{
    public static void SimpleMovementAi(Unit u)
    {
        if(u.dx == 0)//dx == 0 means it hit a wall
        {
            if(u.right )
            {
                u.right = false;
                u.left = true;
                u.facingRight = false;
            }
            else if(u.left )
            {
                u.right = true;
                u.left = false;
                u.facingRight = true;
            }
            SmartSlowAi(u);
        }

    }
    public static void SimpleJumpAi(Unit u)
    {
        if(u.dx == 0)//dx == 0 means it hit a wall
        {
            u.setJumping(true);
            SmartSlowAi(u);
        }

    }

    public static void SmartSlowAi(Unit u)
    {
        u.setMoveSpeed(u.getMaxSpeed()/50);
    }


}
