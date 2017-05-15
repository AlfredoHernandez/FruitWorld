package alfredohdz.com.fruitworld.interactor;

import android.os.Handler;

import java.util.ArrayList;

import alfredohdz.com.fruitworld.R;
import alfredohdz.com.fruitworld.data.Fruit;

/**
 * Created by alfredohdz on 5/14/17.
 */

public class FruitInteractor{

    private ArrayList<Fruit> fruits;
    private int counter = 0;

    public void getFruits(final OnFruitListener listener){
        fruits = new ArrayList<Fruit>(){{
            add(new Fruit("Banana","Gran Canaria", R.drawable.banana));
            add(new Fruit("Strawberry","Huelva",R.drawable.strawberry));
            add(new Fruit("Orange","Sevilla",R.drawable.orange));
            add(new Fruit("Apple","Madrid",R.drawable.apple));
            add(new Fruit("Cherry","México",R.drawable.cherry));
            add(new Fruit("Pear","CD de Mexico",R.drawable.pear));
            add(new Fruit("Raspberry","Estado de Mexico",R.drawable.raspberry));
        }};

        if(fruits!=null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onFruitsSuccess(fruits);
                }
            },600);

        }else{
            listener.onFruitsFailed("ERROR LOADING FRUITS...");
        }
    }

    public void addFruit(final OnFruitListener listener){
        final Fruit fruit = new Fruit("New Fruit #" + (++counter), "Android", R.drawable.watermelon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fruits.add(fruit);
                listener.onFruitsSuccess(fruits);
            }
        },300);

    }

    public void deleteFruit(final int position, final  OnFruitListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Fruit fruitToDelete = fruits.get(position);
                fruits.remove(fruitToDelete);
                listener.onFruitsSuccess(fruits);
            }
        },300);
    }

    public interface OnFruitListener{
        void onFruitsSuccess(ArrayList<Fruit> fruits);
        void onFruitsFailed(String error);
    }
}
