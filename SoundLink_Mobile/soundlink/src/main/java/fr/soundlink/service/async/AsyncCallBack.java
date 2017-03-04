package fr.soundlink.service.async;

/**
 * Created by Ixoh on 20/04/2016.
 */
public interface AsyncCallBack<R> {

    void onSuccess(R result);

    void onFailure(Exception cause);

}
