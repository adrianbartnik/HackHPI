package hackhpi.de.graden;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import java.io.Serializable;

public class Garden implements Serializable {
    public String type = "Private";
    public String location = "Prof.-Dr.-Helmert-Straße 2-3, 14482 Potsdam";
    public String size = "50m²";
    public String listOfPlants = "Tomatoes, Potatoes, Venus Flytrap";
    public String owner = "Florian Wirtz";
    public String numberOfMembers = "Lukas Heilmann, Jakob Edding, Ann Katrin Kuessner";
    public String livestock = "Chickens, Cows, Dragons";
    @DrawableRes public int imageID = R.drawable.flo;
    @DrawableRes public int headerImage = R.drawable.garden1;
    public double lng, lat;
    public String name = "Go with the Flo";
}
