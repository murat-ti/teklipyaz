package com.android.teklipyaz.views.presenters;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.OrganizationCategoriesRepo;
import java.util.List;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class OrganizationCategoriesPresenterImpl extends OrganizationCategoriesPresenter {

    private OrganizationCategoriesRepo itemsRep;

    private Scheduler scheduler;

    private Disposable disposable;

    public OrganizationCategoriesPresenterImpl(OrganizationCategoriesRepo itemsRep, Scheduler scheduler) {
        this.itemsRep = itemsRep;
        this.scheduler = scheduler;
    }


    @Override
    public void getOrganizationCategories() {
        if (!isViewAttached())
            return;

        getView().showLoading();


        disposable = itemsRep.getAllOrganizationCategories().observeOn(scheduler).subscribeWith(new DisposableObserver<List<OrganizationCategory>>() {
            @Override
            public void onNext(List<OrganizationCategory> organizationCategories) {
                if (!isViewAttached())
                    return;

                getView().showOrganizationCategories(organizationCategories);
                getView().hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached())
                    return;
                getView().showError(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
    }
}
