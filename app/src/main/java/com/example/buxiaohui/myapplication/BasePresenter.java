package com.example.buxiaohui.myapplication;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by buxiaohui on 12/10/2016.
 */

public class BasePresenter {
    protected CompositeSubscription compositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void unSubscription() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }
}
