package alfredohdz.com.fruitworld.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import alfredohdz.com.fruitworld.R;
import alfredohdz.com.fruitworld.data.Fruit;

/**
 * Created by alfredohdz on 5/14/17.
 */

public class FruitAdapter extends BaseAdapter{

    protected Context context;
    protected int layout;
    protected ArrayList<Fruit> fruits;


    public FruitAdapter(Context context, int layout, ArrayList<Fruit> fruits) {
        this.context = context;
        this.layout = layout;
        this.fruits = fruits;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            // Inflar la vista
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(layout, null);

            // Obtener los elementos de la vista
            holder = new ViewHolder();
            holder.textViewName = (TextView) convertView.findViewById(R.id.edit_text_fruit_name);
            holder.textViewOrigin = (TextView) convertView.findViewById(R.id.edit_text_fruit_origin);
            holder.image = (ImageView) convertView.findViewById(R.id.fruit_image);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set data to view
        Fruit fruit = fruits.get(position);
        String name = fruit.getName();
        String origin = fruit.getOrigin();
        int image = fruit.getIcon();

        holder.textViewName.setText(name);
        holder.textViewOrigin.setText(origin);
        holder.image.setImageResource(image);

        return convertView;
    }

    class ViewHolder{
        protected TextView textViewName;
        protected TextView textViewOrigin;
        protected ImageView image;
    }
}
