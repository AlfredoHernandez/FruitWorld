package alfredohdz.com.fruitworld.presenter;

import java.util.ArrayList;

import alfredohdz.com.fruitworld.data.Fruit;
import alfredohdz.com.fruitworld.interactor.FruitInteractor;

/**
 * Created by alfredohdz on 5/14/17.
 */

public class FruitPresenter implements FruitInteractor.OnFruitListener {

    private FruitInteractor interactor;
    private View view;

    public FruitPresenter(FruitInteractor interactor) {
        this.interactor = interactor;
    }
    public void setView(View view) {
        this.view = view;
    }

    public void loadFruits(){
        if(view!=null){
            view.showLoader();
        }
        interactor.getFruits(this);
    }

    public void addFruit(){
        if(view!=null){
            view.showLoader();
        }
        interactor.addFruit(this);
    }

    public void deleteFruit(int position){
        if(view!=null){
            view.showLoader();
        }
        interactor.deleteFruit(position,this);
    }

    public interface View{
        void showFruitList(ArrayList<Fruit> fruits);
        void showLoader();
        void hideLoader();
        void showErrorMessage(String error);
    }

    @Override
    public void onFruitsSuccess(ArrayList<Fruit> fruits) {
        if(view!=null){
            view.hideLoader();
            view.showFruitList(fruits);
        }
    }

    @Override
    public void onFruitsFailed(String error) {
        if(view!=null){
            view.hideLoader();
            view.showErrorMessage(error);
        }
    }
}
