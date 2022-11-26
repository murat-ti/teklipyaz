package com.android.teklipyaz.models.repositories.local;

import com.android.teklipyaz.models.entities.City;
import java.util.List;
import io.reactivex.Observable;


public interface CitiesLocalRepo {
    Observable<List<City>> getAllLocal();
    void add(List<City> cities);
    void removeAll();

}
