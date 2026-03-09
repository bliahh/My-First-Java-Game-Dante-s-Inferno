package Collision;

import Entity.Entity;
import Harta.InterfaceHarta;


public class CollisionCheck {



    //FOLOSIT DE ORC< EVIT COLIZIUNI
  public void avoidCollision(InterfaceHarta harta, Entity entity) {
      int tileSize = 16;

      int left = (int) ((entity.x + entity.collisionBox.x) / tileSize);
      int right = (int) ((entity.x + entity.collisionBox.x + entity.collisionBox.width) / tileSize);
      int top = (int) ((entity.y + entity.collisionBox.y) / tileSize);
      int bottom = (int) ((entity.y + entity.collisionBox.y + entity.collisionBox.height) / tileSize);

      if (!checkCollision(entity, harta)) {
          return;
      }

      switch (entity.direction) {
          case "up":
              top = (int) ((entity.y + entity.collisionBox.y - entity.speed) / tileSize);
              if (harta.isCollidable(left,top) || harta.isCollidable(right, top)) {

                  if (!harta.isCollidable(left, bottom) || !harta.isCollidable(left, top)) {

                      entity.direction = "left";
                      entity.x -= entity.speed;


                  } else if (!harta.isCollidable(right, bottom + 1) || !harta.isCollidable(right, top)) {

                      entity.direction = "right";
                      entity.x += entity.speed;


                  } else if (!harta.isCollidable(right, bottom) || !harta.isCollidable(left, bottom)) {

                      entity.direction = "down";
                      entity.y += entity.speed;


                  }

                  break;
              }
          case "down":
              bottom = (int) ((entity.y + entity.collisionBox.y + entity.collisionBox.height + entity.speed) / tileSize);
              if (harta.isCollidable(left, bottom) || harta.isCollidable(right, bottom)) {
                  if (!harta.isCollidable(left, top) || !harta.isCollidable(left, bottom)) {

                      entity.direction = "left";
                      entity.x -= entity.speed;


                  } else if (!harta.isCollidable(right, top) || !harta.isCollidable(right, bottom)) {

                      entity.direction = "right";
                      entity.x += entity.speed;


                  } else if (!harta.isCollidable(right, top) || !harta.isCollidable(left, top)) {

                      entity.direction = "up";
                      entity.y -= entity.speed;

                  }


                  break;
              }

          case "left":
              left = (int) ((entity.x + entity.collisionBox.x - entity.speed) / tileSize);
              if (harta.isCollidable(left, top) || harta.isCollidable(left, bottom)) {
                  if (!harta.isCollidable(left, top) || !harta.isCollidable(right, top)) {

                      entity.direction = "up";
                      entity.y -= entity.speed;


                  } else if (!harta.isCollidable(left, bottom) || !harta.isCollidable(right, bottom)) {

                      entity.direction = "down";
                      entity.y += entity.speed;

                  } else if (!harta.isCollidable(right, top) || !harta.isCollidable(right, bottom)) {

                      entity.direction = "right";
                      entity.x += entity.speed;



                  }
                  break;
              }

          case "right":
              right = (int) ((entity.x + entity.collisionBox.x + entity.collisionBox.width + entity.speed) / tileSize);
              if (harta.isCollidable(right, top) || harta.isCollidable(right, bottom)) {
                  if (!harta.isCollidable(right, top) || !harta.isCollidable(left, top)) {

                          entity.direction = "up";
                          entity.y -= entity.speed;


                  } else if (!harta.isCollidable(right, bottom) || !harta.isCollidable(left, bottom)) {

                          entity.direction = "down";
                          entity.y += entity.speed;


                  } else if (!harta.isCollidable(left, top) || !harta.isCollidable(left, bottom)) {

                          entity.direction = "left";
                          entity.x -= entity.speed;



              }
              break;
                  }
      }
  }

  //ORCO SI PLAYERRR
   public boolean checkCollision(Entity entity, InterfaceHarta harta){
        int tileSize=16;
        int left = (int)((entity.x + entity.collisionBox.x) / tileSize); //collisionBox.x cat de mult se deplaseaza box fata de entity.x
        int right = (int)((entity.x + entity.collisionBox.x + entity.collisionBox.width ) / tileSize); //colt dx jos
        int top = (int)((entity.y + entity.collisionBox.y) / tileSize); //y min
        int bottom = (int)((entity.y + entity.collisionBox.y + entity.collisionBox.height) / tileSize); //ymax

        switch (entity.direction) {
            case "up":
                top = (int)((entity.y + entity.collisionBox.y - entity.speed) / tileSize);
              //  System.out.println("TESTEZ UP "+top+" . "+left+" . "+right);
                return harta.isCollidable(left, top) || harta.isCollidable(right, top);

            case "down":

                bottom = (int)((entity.y + entity.collisionBox.y + entity.collisionBox.height + entity.speed ) / tileSize);
              //  System.out.println("TESTEZ BOTTOM "+bottom+" . "+left+" . "+right);
                return harta.isCollidable(left, bottom) || harta.isCollidable(right, bottom);

            case "left":
                left = (int)((entity.x + entity.collisionBox.x - entity.speed) / tileSize);
               // System.out.println("TESTEZ LEFT "+left+" . "+top+" . "+bottom);
                return harta.isCollidable(left, top) || harta.isCollidable(left, bottom);

            case "right":
                right = (int)((entity.x + entity.collisionBox.x + entity.collisionBox.width + entity.speed ) / tileSize);
               // System.out.println("TESTEZ RIGTH "+right+" . "+top+" . "+bottom);
                return harta.isCollidable(right, top) || harta.isCollidable(right, bottom);
            default:
                return false ;

        }


    }
}



