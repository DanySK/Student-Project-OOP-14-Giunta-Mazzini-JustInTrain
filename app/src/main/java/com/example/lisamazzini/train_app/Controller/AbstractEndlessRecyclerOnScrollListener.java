package com.example.lisamazzini.train_app.controller;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Classe che implementa la logica per gestire l' "endless scrolling" del journeyListFragment.
 * I crediti vanno a: <a href="https://gist.github.com/ssinss">ssinss</a> .
 *
 * @author ssinss
 */
public abstract class AbstractEndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int N_SOL_LOAD = 5;
    private final LinearLayoutManager mLinearLayoutManager;
    private int previousTotal;
    private int currentPage = 1;
    private boolean loading = true;


    /**
     * Costruttore.
     * @param linearLayoutManager layoutmanager
     */
    public AbstractEndlessRecyclerOnScrollListener(final LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public final void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int visibleThreshold = N_SOL_LOAD;
        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = mLinearLayoutManager.getItemCount();
        final int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    /**
     * Metodo che definisce il comportamento dell'applicazione nel momento in cui l'utente scrolla la recycler view
     * fino in fondo.
     * @param pCurrentPage pagina corrente
     */
    public abstract void onLoadMore(int pCurrentPage);
}