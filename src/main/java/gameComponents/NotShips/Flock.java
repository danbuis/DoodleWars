package gameComponents.NotShips;

import BBDGameLibrary.GameEngine.Camera;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.OpenGL.Renderer;
import BBDGameLibrary.OpenGL.Window;
import gameComponents.Ships.BaseShip;
import gameComponents.Ships.MediumBulletShip;
import gameComponents.Ships.PlayerShip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Flock {

    private ArrayList<BaseShip> ships = new ArrayList<>();

    public BBDPoint flockTarget;
    public boolean playerFound = false;
    public final int flockNumber = (int)(Math.random() * 1000);
    private List<Flock> flockList;
    private int JOIN_DIST = 25;
    private PlayerShip player;

    public Flock(PlayerShip player, List<Flock> flockList){
        ships.add(new MediumBulletShip(player, this));
        this.assignTargetsToFlock(null);
        this.flockList = flockList;
        this.player = player;
    }

    public void update(float interval, MouseInput mouseInput, Window window){
        if (this.flockSize() != 0) {
            MediumBulletShip canSeePlayer = this.canFlockSeePlayer();
            if (canSeePlayer != null){
                if (flockSize() < 7){
                    float angleToPlayer = canSeePlayer.directionToPlayer();
                    BBDPoint newTarget = new BBDPoint(canFlockSeePlayer().positionToPoint(), 100, angleToPlayer + (float)Math.PI);
                    assignTargetsToFlock(newTarget);
                }
                else{
                    BBDPoint newTarget = new BBDPoint(player.positionToPoint(), 5, -player.getRotation().z);
                    assignTargetsToFlock(newTarget);
                }
            }
            int arrivalCount = 0;
            for (BaseShip ship : ships) {
                MediumBulletShip medShip = (MediumBulletShip) ship;
                ship.update(interval, mouseInput, window);
                if (medShip.arrived) {
                    arrivalCount++;
                }
            }
            searchForOtherFlocks();
            if (arrivalCount == this.flockSize()) {
                assignTargetsToFlock(null);
            }
        }
    }

    private MediumBulletShip canFlockSeePlayer(){
        for (BaseShip ship : this.ships){
            MediumBulletShip medShip = (MediumBulletShip) ship;
            if(medShip.distanceToPlayer() < 75){
                return medShip;
            }
        }
        return null;
    }

    public void searchForOtherFlocks(){
        for (Flock flock : flockList) {
            if (flock.flockSize() != 0) {
                float playerDiff = Math.abs(this.distanceToPlayerApproximate() - flock.distanceToPlayerApproximate());
                if (this != flock && playerDiff <= JOIN_DIST * 2) {
                    for (BaseShip thisShip : this.ships) {
                        for (BaseShip otherShip : flock.ships) {
                            if (thisShip.distanceSquaredFlat(otherShip) <= JOIN_DIST) {
                                combineFlocks(flock);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private float distanceToPlayerApproximate(){
        MediumBulletShip leadShip = (MediumBulletShip)this.ships.get(0);
        return leadShip.distanceToPlayer();
    }

    private void combineFlocks(Flock otherFlock){
        Flock keep = this;
        Flock discard = otherFlock;

        for (BaseShip ship : discard.ships){
            keep.addShip(ship);
        }
        discard.ships = new ArrayList<>();
        assignTargetsToFlock(null);
    }

    public void assignTargetsToFlock(BBDPoint newTarget){
        BaseShip leadShip = ships.get(0);
        if (newTarget == null) {
            flockTarget = leadShip.roombaSearch();
        }else{
            flockTarget = newTarget;
        }
        leadShip.targetPoint = flockTarget;
        for (int i = 1; i < ships.size(); i++){
            ships.get(i).targetPoint =
                    new BBDPoint(flockTarget.getXLoc() + getJitter(),
                    flockTarget.getYLoc() + getJitter());
        }
    }

    private float getJitter(){
        double jitter = Math.random();
        jitter -= 0.5;
        return (float)jitter * 10;
    }

    public void addShip(BaseShip ship){
        ships.add(ship);
    }

    public void removeShip(BaseShip ship){
        ships.remove(ship);
    }

    public int flockSize(){
        return ships.size();
    }

    public ArrayList<BaseShip> getShips(){
        return this.ships;
    }
}
