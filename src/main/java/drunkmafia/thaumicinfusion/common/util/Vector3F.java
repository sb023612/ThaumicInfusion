package drunkmafia.thaumicinfusion.common.util;

/**
 * Created by DrunkMafia on 18/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class Vector3F {

    public float x, y, z;

    public Vector3F(){
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3F(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3F(Vector3F vec){
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public void add(Vector3F vec){
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void sub(Vector3F vec){
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    public void mult(Vector3F vec){
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
    }

    public void dev(Vector3F vec){
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
