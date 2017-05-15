package alfredohdz.com.fruitworld.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import alfredohdz.com.fruitworld.R;
import alfredohdz.com.fruitworld.data.Fruit;
import alfredohdz.com.fruitworld.interactor.FruitInteractor;
import alfredohdz.com.fruitworld.presenter.FruitPresenter;
import alfredohdz.com.fruitworld.view.adapter.FruitAdapter;

public class MainActivity extends AppCompatActivity implements FruitPresenter.View, AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    protected FruitPresenter presenter;
    protected ListView listView;
    protected GridView gridView;
    protected ProgressBar progressBar;
    protected TextView textViewMessage;
    private int counter = 0;

    // FruitHolder
    protected FruitAdapter fruitAdapter;
    protected FruitAdapter fruitAdapterGrid;
    protected ArrayList<Fruit> fruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        // Inicialización del Presenter
        presenter = new FruitPresenter(new FruitInteractor());
        presenter.setView(this);
        presenter.loadFruits();

    }


    private void initComponents(){
        // Progress Bar
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.bringToFront();

        // List and grid view
        listView = (ListView)findViewById(R.id.list_view_fruit);
        gridView = (GridView)findViewById(R.id.grid_view_fruit);
        gridView.setOnItemClickListener(this);
        listView.setOnItemClickListener(this);
        registerForContextMenu(gridView);
        registerForContextMenu(listView);

        // TextView
        textViewMessage = (TextView) findViewById(R.id.text_view_message);
        textViewMessage.setVisibility(View.GONE);
        textViewMessage.bringToFront();

        // Action Bar
        setActionBar();
    }

    private void setActionBar(){
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setIcon(R.drawable.watermelon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setAdapters(ArrayList<Fruit> fruits){
        fruitAdapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruits);
        listView.setAdapter(fruitAdapter);

        fruitAdapterGrid = new FruitAdapter(MainActivity.this,R.layout.fruit_item_grid,fruits);
        gridView.setAdapter(fruitAdapterGrid);
    }

    // METODOS DE FRUIT VIEW
    @Override
    public void showFruitList(ArrayList<Fruit> fruits) {
        if (fruits.size() <= 0 ){
            textViewMessage.setVisibility(View.VISIBLE);
        }else {
            textViewMessage.setVisibility(View.GONE);
        }
        this.fruits = fruits;
        setAdapters(fruits);
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String error) {
        Log.d(TAG, "showErrorMessage: " + error);
    }


    // METODOS DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                // Add item to list
                presenter.addFruit();
                fruitAdapter.notifyDataSetChanged();
                return true;

            case R.id.change_view:
                if(gridView.getVisibility() == View.VISIBLE){
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menu.setHeaderTitle("Actions");
        menuInflater.inflate(R.menu.context_menu_fruit_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_item:
                // Delete item
                presenter.deleteFruit(menuInfo.position);
                fruitAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    // METODOS ON CLICK ITEM LISTENER DE LISTS VIEWS
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this,
                "" + fruits.get(position).getName() + " from " + fruits.get(position).getOrigin(),
                Toast.LENGTH_LONG)
                .show();
    }

}
