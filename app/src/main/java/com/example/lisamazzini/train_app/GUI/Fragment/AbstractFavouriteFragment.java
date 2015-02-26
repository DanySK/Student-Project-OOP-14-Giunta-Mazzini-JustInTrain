package com.example.lisamazzini.train_app.gui.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.controller.favourites.FavouriteControllerStrategy;
import com.example.lisamazzini.train_app.controller.favourites.IFavouriteController;

/**
 * Fragment astratto che devono estendere tutti i fragment che hanno bisogno di gestire un certo tipo di preferiti.
 * Estende AbstractRobospiceFragment quindi dà anche la possibilità di utilizzare robospice.
 *
 * @author albertogiunta
 */
public abstract class AbstractFavouriteFragment extends AbstractRobospiceFragment implements IFavouriteFragment {

    private MenuItem favItem;
    private MenuItem notFavItem;
    private IFavouriteController favouriteController;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public final void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        setMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        } else if (id == R.id.action_prefere) {
            Toast.makeText(getActivity().getApplicationContext(), "Aggiunto ai preferiti", Toast.LENGTH_SHORT).show();
            favouriteController.addFavourite(getFavouriteForAdding());
            setAsFavouriteIcon(true);
        } else if (id == R.id.action_deprefere) {
            Toast.makeText(getActivity().getApplicationContext(), "Rimosso dai preferiti", Toast.LENGTH_SHORT).show();
            favouriteController.removeFavourite(getFavouriteForRemoving());
            setAsFavouriteIcon(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public final void setMenu(final Menu menu) {
        this.favItem = menu.findItem(R.id.action_deprefere);
        this.notFavItem = menu.findItem(R.id.action_prefere);
    }

    @Override
    public final void toggleFavouriteIcon(final String reqData1, final String reqData2) {
        if (favouriteController.isFavourite(reqData1, reqData2)) {
            setAsFavouriteIcon(true);
        } else {
            setAsFavouriteIcon(false);
        }
    }

    @Override
    public final void setAsFavouriteIcon(final boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(!b);
    }

    @Override
    public final void setAllEnabled(final boolean b) {
        this.favItem.setVisible(b);
        this.notFavItem.setVisible(b);
    }

    @Override
    public final void setFavouriteController(final FavouriteControllerStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Lo strategy non è stato istanziato correttamente. "
                    + "Chiamare il metodo setFavouriteController nella classe che vuole usufruire di un favouriteController");
        }
        favouriteController = strategy.getController();
        favouriteController.setContext(getActivity());
    }

    /**
     * Metodo che restituisce un array di stringhe contenente i dati dell'elemento da aggiungere ai preferiti.
     * @return i dati dell'elemento
     */
    public abstract String[] getFavouriteForAdding();

    /**
     * Metodo che restituisce un array di stringhe contenente i dati dell'elemento da rimuovere dai preferiti.
     * @return i dati dell'elemento
     */
    public abstract String[] getFavouriteForRemoving();
}