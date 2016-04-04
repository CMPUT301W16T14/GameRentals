package t14.com.GameRentals;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qliang on 4/4/16.
 */
public class Model<V extends IView> {

    private transient List<V> views;

    public Model(){
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (views != null) {
            if (!views.contains(view)) views.add(view);
        } else {
            views = new ArrayList<V>();
            views.add(view);
        }
    }

    public void deleteView(V view){
        views.remove(view);
    }

    /**
     * Notify views that the underlying
     * model has changed.
     */
    public void notifyViews(){
        if(views != null) {
            for (IView view : views) {
                view.Update(this);
            }
        }
    }

    public List<V> getViews() {
        return views;
    }


}
