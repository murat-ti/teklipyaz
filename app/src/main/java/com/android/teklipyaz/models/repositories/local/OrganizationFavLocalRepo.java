package com.android.teklipyaz.models.repositories.local;

import com.android.teklipyaz.models.entities.OrganizationFav;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;


public interface OrganizationFavLocalRepo {
    Observable<List<OrganizationFav>> getAllLocal();
    Completable add(OrganizationFav item);
    Maybe<OrganizationFav> get(String id);
    Completable remove(OrganizationFav item);
    //void removeAll();

}
